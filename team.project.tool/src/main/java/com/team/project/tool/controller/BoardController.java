package com.team.project.tool.controller;

import com.team.project.tool.model.dto.ReadBoardDto;
import com.team.project.tool.model.dto.WriteBoardDto;
import com.team.project.tool.service.BoardService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService service;

    @PostMapping
    @Transactional
    public ResponseEntity<Void> create(HttpServletRequest request, @RequestBody WriteBoardDto dto) {
        Long id = service.create(dto);
        return ResponseEntity.created(URI.create(request.getRequestURI() + "/" + id)).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReadBoardDto> read(@PathVariable Long id) {
        return ResponseEntity.ok(service.read(id));
    }

    @GetMapping("s")
    public ResponseEntity<List<ReadBoardDto>> readAll(@RequestParam Long userId) {
        return ResponseEntity.ok(service.readAll(userId));
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> update(@PathVariable("id") Long id, @RequestBody WriteBoardDto dto) {
        service.update(id, dto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }
}
