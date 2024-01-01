package com.team.project.tool.service;

import com.team.project.tool.model.ModelMapper;
import com.team.project.tool.model.dto.ReadTaskDto;
import com.team.project.tool.model.dto.WriteTaskDto;
import com.team.project.tool.model.entity.TaskEntity;
import com.team.project.tool.model.entity.UserEntity;
import com.team.project.tool.repository.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.emptyList;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository repository;
    private final StatusRepository statusRepository;
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;
    private final ModelMapper modelMapper;
    private final TaskSpecifications specifications;

    public Long create(Long boardId, WriteTaskDto dto) {
        if (dto.getUserIds() == null) dto.setUserIds(emptyList());

        TaskEntity entity = modelMapper.writeTaskDtoToEntity(dto);

        entity.setCreatedAt(LocalDateTime.now());
        entity.setCreatedBy(userRepository.findById(dto.getCreatedById()).orElseThrow(EntityNotFoundException::new));
        entity.setBoard(boardRepository.findById(boardId).orElseThrow(EntityNotFoundException::new));
        entity.setStatus(statusRepository.findById(dto.getStatusId()).orElseThrow(EntityNotFoundException::new));
        entity.setUsers(dto.getUserIds().stream().map(userRepository::findById).map(optional -> optional.orElseThrow(EntityNotFoundException::new)).toList());

        return modelMapper.taskEntityToReadDto(repository.save(entity)).getId();
    }

    public ReadTaskDto read(Long id) {
        return modelMapper.taskEntityToReadDto(repository.findById(id).orElseThrow(EntityNotFoundException::new));
    }

    public List<ReadTaskDto> readAll(Long boardId, Long userId, String titlePart, String descriptionPart, Long statusId, Long createdById) {
        Specification<TaskEntity> specification = Specification.where(null);

        if (boardId != null) {
            specifications.isOnBoard(boardRepository.findById(boardId).orElseThrow(EntityNotFoundException::new));
        }

        if (userId != null) {
            specifications.hasUser(userRepository.findById(userId).orElseThrow(EntityNotFoundException::new));
        }

        if (titlePart != null) {
            specifications.titleContains(titlePart);
        }

        if (descriptionPart != null) {
            specifications.descriptionContains(descriptionPart);
        }

        if (statusId != null) {
            specifications.hasStatus(statusRepository.findById(statusId).orElseThrow(EntityNotFoundException::new));
        }

        if (createdById != null) {
            specifications.isCreatedBy(userRepository.findById(createdById).orElseThrow(EntityNotFoundException::new));
        }

        return modelMapper.taskEntitiesToReadDtos(repository.findAll(specification, Sort.by(Sort.Direction.DESC, "createdAt")));
    }

    public void update(Long id, WriteTaskDto dto) {
        if (dto.getUserIds() == null) dto.setUserIds(emptyList());

        TaskEntity entity = repository.findById(id).orElseThrow(EntityNotFoundException::new);

        entity.setTitle(dto.getTitle());
        entity.setDescription(dto.getDescription());

        if (!entity.getStatus().getId().equals(dto.getStatusId())) {
            entity.setStatus(statusRepository.findById(dto.getStatusId()).orElseThrow(EntityNotFoundException::new));
        }
        if (!entity.getUsers().stream().map(UserEntity::getId).sorted().toList().equals(dto.getUserIds().stream().sorted().toList())) {
            entity.setUsers(new ArrayList<>(dto.getUserIds().stream().map(userRepository::findById).map(optional -> optional.orElseThrow(EntityNotFoundException::new)).toList()));
        }

        repository.save(entity);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
