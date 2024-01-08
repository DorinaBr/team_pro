package com.team.project.tool.services;

import com.team.project.tool.models.dtos.ReadBoardDTO;
import com.team.project.tool.models.dtos.WriteBoardDTO;

import java.util.List;

public interface BoardService {

    ReadBoardDTO createBoard(WriteBoardDTO writeBoardDTO);

    ReadBoardDTO getBoard(Long boardId);

    List<ReadBoardDTO> getAllBoards(Long userId);

    ReadBoardDTO updateBoard(Long taskId, WriteBoardDTO writeBoardDTO);

    void deleteBoard(Long boardId);
}
