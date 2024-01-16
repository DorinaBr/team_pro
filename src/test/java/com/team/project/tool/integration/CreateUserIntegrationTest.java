package com.team.project.tool.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.team.project.tool.models.dtos.ErrorDTO;
import com.team.project.tool.models.dtos.ReadUserDTO;
import com.team.project.tool.models.dtos.WriteUserDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

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

        WriteUserDTO writeUserDto = WriteUserDTO.builder()
                .email(email)
                .firstName(firstName)
                .lastName(lastName)
                .build();

        ReadUserDTO readUserDTO = ReadUserDTO.builder()
                .id(id)
                .email(email)
                .firstName(firstName)
                .lastName(lastName)
                .build();

        mockMvc.perform(post("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(writeUserDto)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(readUserDTO)))
                .andReturn();
    }

    @Test
    void testReadUserShouldFailWhenUserDoesNotExist() throws Exception {
        ErrorDTO errorDTO = ErrorDTO.builder()
                .message("User not found.")
                .build();

        mockMvc.perform(get("/user/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().json(objectMapper.writeValueAsString(errorDTO)))
                .andReturn();
    }

}
