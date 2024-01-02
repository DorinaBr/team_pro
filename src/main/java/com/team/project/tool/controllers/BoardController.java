package com.team.project.tool.controllers;

import com.team.project.tool.models.dtos.ReadBoardDTO;
import com.team.project.tool.models.dtos.WriteBoardDTO;
import com.team.project.tool.services.BoardService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @PostMapping
    public ResponseEntity<Void> createBoard(@RequestBody WriteBoardDTO writeBoardDto, HttpServletRequest request) {
        Long id = boardService.createBoard(writeBoardDto);
        return ResponseEntity.created(URI.create(request.getRequestURI() + "/" + id)).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReadBoardDTO> readBoard(@PathVariable Long id) {
        return ResponseEntity.ok(boardService.readBoard(id));
    }

    @GetMapping("s")
    public ResponseEntity<List<ReadBoardDTO>> readAllBoards(@RequestParam Long userId) {
        return ResponseEntity.ok(boardService.readAllBoards(userId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateBoard(@PathVariable("id") Long id, @RequestBody WriteBoardDTO writeBoardDto) {
        boardService.updateBoard(id, writeBoardDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBoard(@PathVariable("id") Long id) {
        boardService.deleteBoard(id);
        return ResponseEntity.ok().build();
    }

}
