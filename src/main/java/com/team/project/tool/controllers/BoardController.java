package com.team.project.tool.controllers;

import com.team.project.tool.models.dtos.ReadBoardDTO;
import com.team.project.tool.models.dtos.WriteBoardDTO;
import com.team.project.tool.services.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @PostMapping
    public ResponseEntity<ReadBoardDTO> createBoard(@RequestBody WriteBoardDTO writeBoardDto) {
        return ResponseEntity.ok(boardService.createBoard(writeBoardDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReadBoardDTO> getBoard(@PathVariable Long id) {
        return ResponseEntity.ok(boardService.getBoard(id));
    }

    @GetMapping("s")
    public ResponseEntity<List<ReadBoardDTO>> getAllBoards(@RequestParam Long userId) {
        return ResponseEntity.ok(boardService.getAllBoards(userId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReadBoardDTO> updateBoard(@PathVariable("id") Long id, @RequestBody WriteBoardDTO writeBoardDto) {
        return ResponseEntity.ok(boardService.updateBoard(id, writeBoardDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBoard(@PathVariable("id") Long id) {
        boardService.deleteBoard(id);
        return ResponseEntity.ok().build();
    }
}
