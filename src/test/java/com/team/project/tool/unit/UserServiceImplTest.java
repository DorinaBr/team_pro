package com.team.project.tool.unit;

import com.team.project.tool.models.ModelMapper;
import com.team.project.tool.models.dtos.ReadUserDTO;
import com.team.project.tool.models.dtos.WriteUserDTO;
import com.team.project.tool.models.entities.User;
import com.team.project.tool.repositories.UserRepository;
import com.team.project.tool.services.UserServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository repository;
    @Spy
    private ModelMapper modelMapper;
    @InjectMocks
    private UserServiceImpl service;

    @Test
    void testCreate_ShouldPass_WithSetOrganizer() {
        String email = "test.user@email.com";
        String firstName = "Test";
        String lastName = "User";
        Long id = 1L;

        WriteUserDTO writeUserDTO = WriteUserDTO.builder()
                .email(email)
                .firstName(firstName)
                .lastName(lastName)
                .build();

        User user = User.builder()
                .email(email)
                .firstName(firstName)
                .lastName(lastName)
                .build();

        User savedUser = User.builder()
                .id(id)
                .email(email)
                .firstName(firstName)
                .lastName(lastName)
                .build();

        when(repository.save(user)).thenReturn(savedUser);

        ReadUserDTO createdUser = service.createUser(writeUserDTO);

        verify(repository, times(1)).save(user);
        assertEquals(writeUserDTO.getEmail(), createdUser.getEmail());
        assertEquals(writeUserDTO.getLastName(), createdUser.getLastName());
        assertEquals(writeUserDTO.getFirstName(), createdUser.getFirstName());
    }

    @Test
    void testRead_ShouldFail_WhenUserDoesNotExist() {
        Long id = 1L;

        when(repository.findById(id)).thenThrow(new EntityNotFoundException());

        assertThrows(EntityNotFoundException.class, () -> service.getUser(id));
    }

}
