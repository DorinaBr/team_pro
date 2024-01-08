package com.team.project.tool.services;

import com.team.project.tool.models.dtos.ReadTaskDTO;
import com.team.project.tool.models.dtos.WriteTaskDTO;

import java.util.List;

public interface TaskService {

    ReadTaskDTO createTask(Long boardId, WriteTaskDTO writeTaskDTO);

    ReadTaskDTO getTask(Long taskId);

    List<ReadTaskDTO> getAllTasks(Long boardId, Long userId, String titlePart, String descriptionPart, Long statusId, Long createdById);

    ReadTaskDTO updateTask(Long taskId, WriteTaskDTO writeTaskDTO);

    void delete(Long taskId);
}
