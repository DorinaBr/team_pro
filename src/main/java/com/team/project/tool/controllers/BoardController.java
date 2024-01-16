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

    @GetMapping("/{boardId}")
    public ResponseEntity<ReadBoardDTO> getBoard(@PathVariable Long boardId) {
        return ResponseEntity.ok(boardService.getBoard(boardId));
    }

    @GetMapping("s")
    public ResponseEntity<List<ReadBoardDTO>> getAllBoards(@RequestParam Long userId) {
        return ResponseEntity.ok(boardService.getAllBoards(userId));
    }

    @PutMapping("/{boardId}")
    public ResponseEntity<ReadBoardDTO> updateBoard(@PathVariable Long boardId, @RequestBody WriteBoardDTO writeBoardDto) {
        return ResponseEntity.ok(boardService.updateBoard(boardId, writeBoardDto));
    }

    @DeleteMapping("/{boardId}")
    public ResponseEntity<Void> deleteBoard(@PathVariable Long boardId) {
        boardService.deleteBoard(boardId);
        return ResponseEntity.ok().build();
    }
}
