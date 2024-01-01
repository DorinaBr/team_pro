package com.team.project.tool.controller;

import com.team.project.tool.model.dto.WriteStatusDto;
import com.team.project.tool.service.StatusService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequiredArgsConstructor
public class StatusController {

    private final StatusService service;

    @PostMapping("/board/{boardId}/status")
    @Transactional
    public ResponseEntity<Void> create(@PathVariable("boardId") Long boardId, @RequestBody WriteStatusDto dto, HttpServletRequest request) {
        Long id = service.create(boardId, dto);
        return ResponseEntity.created(URI.create(request.getRequestURI() + "/" + id)).build();
    }

    @PutMapping("/board/status/{id}")
    @Transactional
    public ResponseEntity<Void> update(@PathVariable("id") Long id,  @RequestBody WriteStatusDto dto){
        service.update(id, dto);
        return ResponseEntity.ok().build();
    }


    @DeleteMapping("/board/status/{id}")
    @Transactional
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }
}
