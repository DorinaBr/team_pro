package com.team.project.tool.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.team.project.tool.model.dto.WriteUserDto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
class CreateUserIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @PersistenceContext
    private EntityManager entityManager;

    @Test
    void testCreateUserShouldPass() throws Exception {
        String email = "test.user@email.com";
        String firstName = "Test";
        String lastName = "User";
        Long id = 1L;

        WriteUserDto writeUserDto = WriteUserDto.builder()
                .email(email)
                .firstName(firstName)
                .lastName(lastName)
                .build();

        mockMvc.perform(post("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(writeUserDto)))
                .andExpect(status().isCreated())
                .andExpect(header().string("location", "/user/" + id))
                .andReturn();

        assertEquals(1, entityManager.createNativeQuery(String.format("""
                SELECT id FROM user_
                WHERE id = '%s'
                AND email = '%s'
                AND first_name = '%s'
                AND last_name = '%s'""", id, email, firstName, lastName)).getResultList().size());
    }

    @Test
    void testCreateUserShouldFailWhenUserDoesNotExist() throws Exception {
        mockMvc.perform(get("/user/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().json("{\"message\":\"Resource not found.\"}"))
                .andReturn();
    }

}
