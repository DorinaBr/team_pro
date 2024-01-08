package com.team.project.tool.controllers;

import com.team.project.tool.models.dtos.ReadStatusDTO;
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
    public ResponseEntity<ReadStatusDTO> createStatus(@PathVariable("boardId") Long boardId, @RequestBody WriteStatusDTO writeStatusDto) {
        return ResponseEntity.ok(statusService.createStatus(boardId, writeStatusDto));
    }

    @PutMapping("/board/status/{id}")
    public ResponseEntity<ReadStatusDTO> updateStatus(@PathVariable("id") Long id, @RequestBody WriteStatusDTO writeStatusDto){
        return ResponseEntity.ok(statusService.updateStatus(id, writeStatusDto));
    }

    @DeleteMapping("/board/status/{id}")
    public ResponseEntity<Void> deleteStatus(@PathVariable("id") Long id) {
        statusService.deleteStatus(id);
        return ResponseEntity.ok().build();
    }
}
