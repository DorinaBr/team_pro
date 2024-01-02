package com.team.project.tool.models;

import com.team.project.tool.models.dtos.*;
import com.team.project.tool.models.entities.Board;
import com.team.project.tool.models.entities.Status;
import com.team.project.tool.models.entities.Task;
import com.team.project.tool.models.entities.User;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Component
public class ModelMapper {

    public ReadUserDTO userEntityToReadDto(User entity) {
        return ReadUserDTO.builder()
                .id(entity.getId())
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .email(entity.getEmail())
                .build();
    }

    public User writeUserDtoToEntity(WriteUserDTO dto) {
        return User.builder()
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .email(dto.getEmail())
                .build();
    }

    public Board writeBoardDtoToEntity(WriteBoardDTO dto) {
        return Board.builder()
                .name(dto.getName())
                .build();
    }

    public List<ReadBoardDTO> boardEntitiesToReadDtos(Collection<Board> entities) {
        if (entities == null)
            return Collections.emptyList();
        return entities.stream()
                .map(this::boardEntityToReadDto)
                .toList();
    }

    public ReadBoardDTO boardEntityToReadDto(Board entity) {
        return ReadBoardDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .owner(userEntityToReadDto(entity.getOwner()))
                .statuses(statusEntitiesToReadDtos(entity.getStatuses()))
                .tasks(taskEntitiesToReadDtos(entity.getTasks()))
                .build();
    }

    public List<ReadTaskDTO> taskEntitiesToReadDtos(List<Task> entities) {
        if (entities == null)
            return Collections.emptyList();
        return entities.stream()
                .map(this::taskEntityToReadDto)
                .sorted((o1, o2) -> o2.getCreatedAt().compareTo(o1.getCreatedAt()))
                .toList();
    }

    public ReadTaskDTO taskEntityToReadDto(Task entity) {
        if (entity.getUsers() == null) entity.setUsers(Collections.emptyList());

        return ReadTaskDTO.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .statusId(entity.getStatus().getId())
                .description(entity.getDescription())
                .userIds(entity.getUsers().stream().map(User::getId).toList())
                .createdAt(entity.getCreatedAt())
                .createdById(entity.getCreatedBy().getId())
                .build();
    }

    public List<ReadStatusDTO> statusEntitiesToReadDtos(List<Status> entities) {
        if (entities == null)
            return Collections.emptyList();
        return entities.stream()
                .map(this::statusEntityToReadDto)
                .toList();
    }

    public ReadStatusDTO statusEntityToReadDto(Status entity) {
        return ReadStatusDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .position(entity.getPosition())
                .build();
    }

    public Status writeStatusDtoToEntity(WriteStatusDTO dto) {
        return Status.builder()
                .name(dto.getName())
                .build();
    }

    public List<ReadUserDTO> userEntitiesToReadDtos(List<User> entities) {
        if (entities == null)
            return Collections.emptyList();
        return entities.stream()
                .map(this::userEntityToReadDto)
                .toList();
    }

    public Task writeTaskDtoToEntity(WriteTaskDTO dto) {
        return Task.builder().description(dto.getDescription()).title(dto.getTitle()).build();
    }
}
