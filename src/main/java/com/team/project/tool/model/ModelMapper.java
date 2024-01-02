package com.team.project.tool.model;

import com.team.project.tool.model.dto.*;
import com.team.project.tool.model.entity.BoardEntity;
import com.team.project.tool.model.entity.StatusEntity;
import com.team.project.tool.model.entity.TaskEntity;
import com.team.project.tool.model.entity.UserEntity;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Component
public class ModelMapper {

    public ReadUserDto userEntityToReadDto(UserEntity entity) {
        return ReadUserDto.builder()
                .id(entity.getId())
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .email(entity.getEmail())
                .build();
    }

    public UserEntity writeUserDtoToEntity(WriteUserDto dto) {
        return UserEntity.builder()
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .email(dto.getEmail())
                .build();
    }

    public BoardEntity writeBoardDtoToEntity(WriteBoardDto dto) {
        return BoardEntity.builder()
                .name(dto.getName())
                .build();
    }

    public List<ReadBoardDto> boardEntitiesToReadDtos(Collection<BoardEntity> entities) {
        if (entities == null)
            return Collections.emptyList();
        return entities.stream()
                .map(this::boardEntityToReadDto)
                .toList();
    }

    public ReadBoardDto boardEntityToReadDto(BoardEntity entity) {
        return ReadBoardDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .owner(userEntityToReadDto(entity.getOwner()))
                .statuses(statusEntitiesToReadDtos(entity.getStatuses()))
                .tasks(taskEntitiesToReadDtos(entity.getTasks()))
                .build();
    }

    public List<ReadTaskDto> taskEntitiesToReadDtos(List<TaskEntity> entities) {
        if (entities == null)
            return Collections.emptyList();
        return entities.stream()
                .map(this::taskEntityToReadDto)
                .sorted((o1, o2) -> o2.getCreatedAt().compareTo(o1.getCreatedAt()))
                .toList();
    }

    public ReadTaskDto taskEntityToReadDto(TaskEntity entity) {
        if (entity.getUsers() == null) entity.setUsers(Collections.emptyList());

        return ReadTaskDto.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .statusId(entity.getStatus().getId())
                .description(entity.getDescription())
                .userIds(entity.getUsers().stream().map(UserEntity::getId).toList())
                .createdAt(entity.getCreatedAt())
                .createdById(entity.getCreatedBy().getId())
                .build();
    }

    public List<ReadStatusDto> statusEntitiesToReadDtos(List<StatusEntity> entities) {
        if (entities == null)
            return Collections.emptyList();
        return entities.stream()
                .map(this::statusEntityToReadDto)
                .toList();
    }

    public ReadStatusDto statusEntityToReadDto(StatusEntity entity) {
        return ReadStatusDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .position(entity.getPosition())
                .build();
    }

    public StatusEntity writeStatusDtoToEntity(WriteStatusDto dto) {
        return StatusEntity.builder()
                .name(dto.getName())
                .build();
    }

    public List<ReadUserDto> userEntitiesToReadDtos(List<UserEntity> entities) {
        if (entities == null)
            return Collections.emptyList();
        return entities.stream()
                .map(this::userEntityToReadDto)
                .toList();
    }

    public TaskEntity writeTaskDtoToEntity(WriteTaskDto dto) {
        return TaskEntity.builder().description(dto.getDescription()).title(dto.getTitle()).build();
    }
}
