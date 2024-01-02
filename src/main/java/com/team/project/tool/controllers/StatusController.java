package com.team.project.tool.controllers;

import com.team.project.tool.models.dtos.WriteStatusDTO;
import com.team.project.tool.services.StatusService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequiredArgsConstructor
public class StatusController {

    private final StatusService statusService;

    @PostMapping("/board/{boardId}/status")
    public ResponseEntity<Void> createStatus(@PathVariable("boardId") Long boardId, @RequestBody WriteStatusDTO writeStatusDto, HttpServletRequest request) {
        Long id = statusService.createStatus(boardId, writeStatusDto);
        return ResponseEntity.created(URI.create(request.getRequestURI() + "/" + id)).build();
    }

    @PutMapping("/board/status/{id}")
    public ResponseEntity<Void> updateStatus(@PathVariable("id") Long id, @RequestBody WriteStatusDTO writeStatusDto){
        statusService.updateStatus(id, writeStatusDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/board/status/{id}")
    public ResponseEntity<Void> deleteStatus(@PathVariable("id") Long id) {
        statusService.deleteStatus(id);
        return ResponseEntity.ok().build();
    }

}
