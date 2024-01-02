package com.team.project.tool.controller;

import com.team.project.tool.model.dto.ReadTaskDto;
import com.team.project.tool.model.dto.WriteTaskDto;
import com.team.project.tool.service.TaskService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class TaskController {

    private final TaskService service;

    @PostMapping("/board/{boardId}/task")
    @Transactional
    public ResponseEntity<Void> create(@PathVariable Long boardId, @RequestBody WriteTaskDto dto, HttpServletRequest request) {
        Long id = service.create(boardId, dto);
        return ResponseEntity.created(URI.create(request.getRequestURI() + "/" + id)).build();
    }

    @GetMapping("/board/task/{id}")
    public ResponseEntity<ReadTaskDto> read(@PathVariable Long id) {
        return ResponseEntity.ok(service.read(id));
    }

    @GetMapping("/board/tasks")
    public ResponseEntity<List<ReadTaskDto>> readAll(@RequestParam(required = false) Long boardId,
                                                     @RequestParam(required = false) Long userId,
                                                     @RequestParam(required = false) String titlePart,
                                                     @RequestParam(required = false) String descriptionPart,
                                                     @RequestParam(required = false) Long statusId,
                                                     @RequestParam(required = false) Long createdById) {
        return ResponseEntity.ok(service.readAll(boardId, userId, titlePart, descriptionPart, statusId, createdById));
    }

    @PutMapping("/board/task/{id}")
    @Transactional
    public ResponseEntity<Void> update(@PathVariable("id") Long id, @RequestBody WriteTaskDto dto) {
        service.update(id, dto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/board/task/{id}")
    @Transactional
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }
}
