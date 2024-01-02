package com.team.project.tool.services;

import com.team.project.tool.models.ModelMapper;
import com.team.project.tool.models.dtos.ReadTaskDTO;
import com.team.project.tool.models.dtos.WriteTaskDTO;
import com.team.project.tool.models.entities.Task;
import com.team.project.tool.models.entities.User;
import com.team.project.tool.repositories.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.emptyList;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final StatusRepository statusRepository;
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;
    private final ModelMapper modelMapper;
    private final TaskSpecifications taskSpecifications;

    @Transactional
    @Override
    public Long createTask(Long boardId, WriteTaskDTO writeTaskDto) {
        if (writeTaskDto.getUserIds() == null) writeTaskDto.setUserIds(emptyList());

        Task task = modelMapper.writeTaskDtoToEntity(writeTaskDto);

        task.setCreatedAt(LocalDateTime.now());
        task.setCreatedBy(userRepository.findById(writeTaskDto.getCreatedById()).orElseThrow(EntityNotFoundException::new));
        task.setBoard(boardRepository.findById(boardId).orElseThrow(EntityNotFoundException::new));
        task.setStatus(statusRepository.findById(writeTaskDto.getStatusId()).orElseThrow(EntityNotFoundException::new));
        task.setUsers(writeTaskDto.getUserIds().stream().map(userRepository::findById).map(optional -> optional.orElseThrow(EntityNotFoundException::new)).toList());

        return modelMapper.taskEntityToReadDto(taskRepository.save(task)).getId();
    }

    @Override
    public ReadTaskDTO readTask(Long id) {
        return modelMapper.taskEntityToReadDto(taskRepository.findById(id).orElseThrow(EntityNotFoundException::new));
    }

    @Override
    public List<ReadTaskDTO> readAllTasks(Long boardId, Long userId, String titlePart, String descriptionPart, Long statusId, Long createdById) {
        Specification<Task> specification = Specification.where(null);

        if (boardId != null) {
            taskSpecifications.isOnBoard(boardRepository.findById(boardId).orElseThrow(EntityNotFoundException::new));
        }
        if (userId != null) {
            taskSpecifications.hasUser(userRepository.findById(userId).orElseThrow(EntityNotFoundException::new));
        }
        if (titlePart != null) {
            taskSpecifications.titleContains(titlePart);
        }
        if (descriptionPart != null) {
            taskSpecifications.descriptionContains(descriptionPart);
        }
        if (statusId != null) {
            taskSpecifications.hasStatus(statusRepository.findById(statusId).orElseThrow(EntityNotFoundException::new));
        }
        if (createdById != null) {
            taskSpecifications.isCreatedBy(userRepository.findById(createdById).orElseThrow(EntityNotFoundException::new));
        }

        return modelMapper.taskEntitiesToReadDtos(taskRepository.findAll(specification, Sort.by(Sort.Direction.DESC, "createdAt")));
    }

    @Transactional
    @Override
    public void updateTask(Long id, WriteTaskDTO writeTaskDto) {
        if (writeTaskDto.getUserIds() == null) writeTaskDto.setUserIds(emptyList());

        Task task = taskRepository.findById(id).orElseThrow(EntityNotFoundException::new);

        task.setTitle(writeTaskDto.getTitle());
        task.setDescription(writeTaskDto.getDescription());

        if (!task.getStatus().getId().equals(writeTaskDto.getStatusId())) {
            task.setStatus(statusRepository.findById(writeTaskDto.getStatusId()).orElseThrow(EntityNotFoundException::new));
        }
        if (!task.getUsers().stream().map(User::getId).sorted().toList().equals(writeTaskDto.getUserIds().stream().sorted().toList())) {
            task.setUsers(new ArrayList<>(writeTaskDto.getUserIds().stream().map(userRepository::findById).map(optional -> optional.orElseThrow(EntityNotFoundException::new)).toList()));
        }

        taskRepository.save(task);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        taskRepository.deleteById(id);
    }
}
