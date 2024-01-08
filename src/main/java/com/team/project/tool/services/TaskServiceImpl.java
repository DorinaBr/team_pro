package com.team.project.tool.services;

import com.team.project.tool.exceptions.BoardNotFoundException;
import com.team.project.tool.exceptions.StatusNotFoundException;
import com.team.project.tool.exceptions.TaskNotFoundException;
import com.team.project.tool.exceptions.UserNotFoundException;
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
    public ReadTaskDTO createTask(Long boardId, WriteTaskDTO writeTaskDTO) {
        if (writeTaskDTO.getUserIds() == null) writeTaskDTO.setUserIds(emptyList());

        Task task = modelMapper.writeTaskDtoToEntity(writeTaskDTO);

        task.setCreatedAt(LocalDateTime.now());
        task.setCreatedBy(userRepository.findById(writeTaskDTO.getCreatedById()).orElseThrow(UserNotFoundException::new));
        task.setBoard(boardRepository.findById(boardId).orElseThrow(BoardNotFoundException::new));
        task.setStatus(statusRepository.findById(writeTaskDTO.getStatusId()).orElseThrow(StatusNotFoundException::new));
        task.setUsers(writeTaskDTO.getUserIds().stream().map(userRepository::findById).map(optional -> optional.orElseThrow(UserNotFoundException::new)).toList());

        return modelMapper.taskEntityToReadDto(taskRepository.save(task));
    }

    @Override
    public ReadTaskDTO getTask(Long taskId) {
        return modelMapper.taskEntityToReadDto(taskRepository.findById(taskId).orElseThrow(TaskNotFoundException::new));
    }

    @Override
    public List<ReadTaskDTO> getAllTasks(Long boardId, Long userId, String titlePart, String descriptionPart, Long statusId, Long createdById) {
        Specification<Task> specification = Specification.where(null);

        if (boardId != null) {
            taskSpecifications.isOnBoard(boardRepository.findById(boardId).orElseThrow(BoardNotFoundException::new));
        }
        if (userId != null) {
            taskSpecifications.hasUser(userRepository.findById(userId).orElseThrow(UserNotFoundException::new));
        }
        if (titlePart != null) {
            taskSpecifications.titleContains(titlePart);
        }
        if (descriptionPart != null) {
            taskSpecifications.descriptionContains(descriptionPart);
        }
        if (statusId != null) {
            taskSpecifications.hasStatus(statusRepository.findById(statusId).orElseThrow(StatusNotFoundException::new));
        }
        if (createdById != null) {
            taskSpecifications.isCreatedBy(userRepository.findById(createdById).orElseThrow(UserNotFoundException::new));
        }

        return modelMapper.taskEntitiesToReadDtos(taskRepository.findAll(specification, Sort.by(Sort.Direction.DESC, "createdAt")));
    }

    @Transactional
    @Override
    public ReadTaskDTO updateTask(Long taskId, WriteTaskDTO writeTaskDTO) {
        if (writeTaskDTO.getUserIds() == null) writeTaskDTO.setUserIds(emptyList());

        Task task = taskRepository.findById(taskId).orElseThrow(TaskNotFoundException::new);

        task.setTitle(writeTaskDTO.getTitle());
        task.setDescription(writeTaskDTO.getDescription());

        if (!task.getStatus().getId().equals(writeTaskDTO.getStatusId())) {
            task.setStatus(statusRepository.findById(writeTaskDTO.getStatusId()).orElseThrow(StatusNotFoundException::new));
        }
        if (!task.getUsers().stream().map(User::getId).sorted().toList().equals(writeTaskDTO.getUserIds().stream().sorted().toList())) {
            task.setUsers(new ArrayList<>(writeTaskDTO.getUserIds().stream().map(userRepository::findById).map(optional -> optional.orElseThrow(UserNotFoundException::new)).toList()));
        }

        return modelMapper.taskEntityToReadDto(taskRepository.save(task));
    }

    @Transactional
    @Override
    public void delete(Long taskId) {
        taskRepository.findById(taskId).orElseThrow(TaskNotFoundException::new);

        taskRepository.deleteById(taskId);
    }
}
