package com.team.project.tool.services;

import com.team.project.tool.models.dtos.ReadTaskDTO;
import com.team.project.tool.models.dtos.WriteTaskDTO;

import java.util.List;

public interface TaskService {

    Long createTask(Long boardId, WriteTaskDTO dto);

    ReadTaskDTO readTask(Long id);

    List<ReadTaskDTO> readAllTasks(Long boardId, Long userId, String titlePart, String descriptionPart, Long statusId, Long createdById);

    void updateTask(Long id, WriteTaskDTO dto);

    void delete(Long id);
}
