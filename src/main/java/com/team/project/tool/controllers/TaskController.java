package com.team.project.tool.controllers;

import com.team.project.tool.models.dtos.ReadTaskDTO;
import com.team.project.tool.models.dtos.WriteTaskDTO;
import com.team.project.tool.services.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @PostMapping("/board/{boardId}/task")
    public ResponseEntity<ReadTaskDTO> createTask(@PathVariable Long boardId, @RequestBody WriteTaskDTO writeTaskDto) {
        return ResponseEntity.ok(taskService.createTask(boardId, writeTaskDto));
    }

    @GetMapping("/board/task/{id}")
    public ResponseEntity<ReadTaskDTO> getTask(@PathVariable Long id) {
        return ResponseEntity.ok(taskService.getTask(id));
    }

    @GetMapping("/board/tasks")
    public ResponseEntity<List<ReadTaskDTO>> getAllTasks(@RequestParam(required = false) Long boardId,
                                                         @RequestParam(required = false) Long userId,
                                                         @RequestParam(required = false) String titlePart,
                                                         @RequestParam(required = false) String descriptionPart,
                                                         @RequestParam(required = false) Long statusId,
                                                         @RequestParam(required = false) Long createdById) {
        return ResponseEntity.ok(taskService.getAllTasks(boardId, userId, titlePart, descriptionPart, statusId, createdById));
    }

    @PutMapping("/board/task/{id}")
    public ResponseEntity<ReadTaskDTO> updateTask(@PathVariable("id") Long taskId, @RequestBody WriteTaskDTO writeTaskDto) {
        return ResponseEntity.ok(taskService.updateTask(taskId, writeTaskDto));
    }

    @DeleteMapping("/board/task/{id}")
    @Transactional
    public ResponseEntity<Void> deleteTask(@PathVariable("id") Long id) {
        taskService.delete(id);
        return ResponseEntity.ok().build();
    }
}
