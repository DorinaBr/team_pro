package com.team.project.tool.unit;

import com.team.project.tool.model.ModelMapper;
import com.team.project.tool.model.dto.WriteUserDto;
import com.team.project.tool.model.entity.UserEntity;
import com.team.project.tool.repository.UserRepository;
import com.team.project.tool.service.UserService;
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
class UserServiceTest {

    @Mock
    private UserRepository repository;
    @Spy
    private ModelMapper modelMapper;
    @InjectMocks
    private UserService service;

    @Test
    void testCreate_ShouldPass_WithSetOrganizer() {
        String email = "test.user@email.com";
        String firstName = "Test";
        String lastName = "User";
        Long id = 1L;

        WriteUserDto dto = WriteUserDto.builder()
                .email(email)
                .firstName(firstName)
                .lastName(lastName)
                .build();

        UserEntity userEntity = UserEntity.builder()
                .email(email)
                .firstName(firstName)
                .lastName(lastName)
                .build();

        UserEntity savedUserEntity = UserEntity.builder()
                .id(id)
                .email(email)
                .firstName(firstName)
                .lastName(lastName)
                .build();

        when(repository.save(userEntity)).thenReturn(savedUserEntity);

        Long createdId = service.create(dto);

        verify(repository, times(1)).save(userEntity);
        assertEquals(id, createdId);
    }

    @Test
    void testRead_ShouldFail_WhenUserDoesNotExist() {
        Long id = 1L;

        when(repository.findById(id)).thenThrow(new EntityNotFoundException());

        assertThrows(EntityNotFoundException.class, () -> service.read(id));
    }

}
