package com.team.project.tool.services;

import com.team.project.tool.exceptions.BoardNotFoundException;
import com.team.project.tool.exceptions.UserNotFoundException;
import com.team.project.tool.models.ModelMapper;
import com.team.project.tool.models.dtos.ReadBoardDTO;
import com.team.project.tool.models.dtos.WriteBoardDTO;
import com.team.project.tool.models.entities.Board;
import com.team.project.tool.models.entities.User;
import com.team.project.tool.repositories.BoardRepository;
import com.team.project.tool.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@RequiredArgsConstructor
@Service
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;

    @Transactional
    @Override
    public ReadBoardDTO createBoard(WriteBoardDTO writeBoardDTO) {
        Board board = modelMapper.writeBoardDtoToEntity(writeBoardDTO);

        board.setOwner(userRepository.findById(writeBoardDTO.getOwnerId()).orElseThrow(UserNotFoundException::new));

        Board savedBoard = boardRepository.save(board);
        log.info("Saved Board with id {}, in the database.", savedBoard.getId());

        return modelMapper.boardEntityToReadDto(savedBoard);
    }

    @Override
    public ReadBoardDTO getBoard(Long boardId) {
        Board board = boardRepository.findById(boardId).orElseThrow(BoardNotFoundException::new);
        log.info("Found Board with id {}, in the database.", boardId);

        return modelMapper.boardEntityToReadDto(board);
    }

    @Override
    public List<ReadBoardDTO> getAllBoards(Long userId) {
        Set<Board> boards = new HashSet<>();
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);

        boards.addAll(user.getBoards());
        boards.addAll(user.getOwnedBoards());
        log.info("Found {} Boards in the database.", boards.size());

        return modelMapper.boardEntitiesToReadDtos(boards);
    }

    @Transactional
    @Override
    public ReadBoardDTO updateBoard(Long boardId, WriteBoardDTO writeBoardDTO) {
        Board board = boardRepository.findById(boardId).orElseThrow(BoardNotFoundException::new);

        board.setName(writeBoardDTO.getName());
        board.setOwner(userRepository.findById(writeBoardDTO.getOwnerId()).orElseThrow(UserNotFoundException::new));

        Board updatedBoard = boardRepository.save(board);
        log.info("Updated Board with id {}, in the database.", boardId);

        return modelMapper.boardEntityToReadDto(updatedBoard);
    }

    @Transactional
    @Override
    public void deleteBoard(Long boardId) {
        boardRepository.findById(boardId).orElseThrow(BoardNotFoundException::new);

        boardRepository.deleteById(boardId);

        log.info("Deleted Board with id {}, from the database.", boardId);
    }
}
