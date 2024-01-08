package com.team.project.tool.controllers;

import com.team.project.tool.models.dtos.ReadTaskDTO;
import com.team.project.tool.models.dtos.WriteTaskDTO;
import com.team.project.tool.services.TaskService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @PostMapping("/board/{boardId}/task")
    public ResponseEntity<Void> createTask(@PathVariable Long boardId, @RequestBody WriteTaskDTO writeTaskDto, HttpServletRequest request) {
        Long id = taskService.createTask(boardId, writeTaskDto);
        return ResponseEntity.created(URI.create(request.getRequestURI() + "/" + id)).build();
    }

    @GetMapping("/board/task/{id}")
    public ResponseEntity<ReadTaskDTO> readTask(@PathVariable Long id) {
        return ResponseEntity.ok(taskService.readTask(id));
    }

    @GetMapping("/board/tasks")
    public ResponseEntity<List<ReadTaskDTO>> readAllTasks(@RequestParam(required = false) Long boardId,
                                                          @RequestParam(required = false) Long userId,
                                                          @RequestParam(required = false) String titlePart,
                                                          @RequestParam(required = false) String descriptionPart,
                                                          @RequestParam(required = false) Long statusId,
                                                          @RequestParam(required = false) Long createdById) {
        return ResponseEntity.ok(taskService.readAllTasks(boardId, userId, titlePart, descriptionPart, statusId, createdById));
    }

    @PutMapping("/board/task/{id}")
    public ResponseEntity<Void> updateTask(@PathVariable("id") Long id, @RequestBody WriteTaskDTO writeTaskDto) {
        taskService.updateTask(id, writeTaskDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/board/task/{id}")
    @Transactional
    public ResponseEntity<Void> deleteTask(@PathVariable("id") Long id) {
        taskService.delete(id);
        return ResponseEntity.ok().build();
    }
}
