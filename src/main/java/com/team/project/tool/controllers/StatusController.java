package com.team.project.tool.controllers;

import com.team.project.tool.exceptions.DuplicateStatusNameException;
import com.team.project.tool.models.dtos.ReadStatusDTO;
import com.team.project.tool.models.dtos.WriteStatusDTO;
import com.team.project.tool.services.StatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/board")
@RequiredArgsConstructor
public class StatusController {

    private final StatusService statusService;

    @PostMapping("/{boardId}/status")
    public ResponseEntity<ReadStatusDTO> createStatus(@PathVariable("boardId") Long boardId, @RequestBody WriteStatusDTO writeStatusDto) {
        try {
            return ResponseEntity.ok(statusService.createStatus(boardId, writeStatusDto));
        } catch (DataIntegrityViolationException e) {
            if (e.getMessage().contains("duplicate key value violates unique constraint")) {
                throw new DuplicateStatusNameException(e);
            } else {
                throw e;
            }
        }
    }

    @PutMapping("/status/{statusId}")
    public ResponseEntity<ReadStatusDTO> updateStatus(@PathVariable Long statusId, @RequestBody WriteStatusDTO writeStatusDto) {
        return ResponseEntity.ok(statusService.updateStatus(statusId, writeStatusDto));
    }

    @DeleteMapping("/status/{statusId}")
    public ResponseEntity<Void> deleteStatus(@PathVariable Long statusId) {
        statusService.deleteStatus(statusId);
        return ResponseEntity.ok().build();
    }
}
