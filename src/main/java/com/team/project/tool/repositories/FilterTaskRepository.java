package com.team.project.tool.repositories;

import com.team.project.tool.models.entities.Task;

import java.util.List;

public interface FilterTaskRepository {

    List<Task> findFilteredTasks(Long boardId, Long userId, String titlePart, String descriptionPart, Long statusId, Long createdById);

}
