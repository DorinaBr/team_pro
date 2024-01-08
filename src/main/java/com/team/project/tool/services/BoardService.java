package com.team.project.tool.services;

import com.team.project.tool.models.dtos.ReadBoardDTO;
import com.team.project.tool.models.dtos.WriteBoardDTO;

import java.util.List;

public interface BoardService {

    Long createBoard(WriteBoardDTO dto);

    ReadBoardDTO readBoard(Long id);

    List<ReadBoardDTO> readAllBoards(Long userId);

    void updateBoard(Long id, WriteBoardDTO dto);

    void deleteBoard(Long id);
}
