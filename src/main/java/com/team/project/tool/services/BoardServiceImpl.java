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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;

    @Transactional
    @Override
    public ReadBoardDTO createBoard(WriteBoardDTO writeBoardDTO) {
        Board board = modelMapper.writeBoardDtoToEntity(writeBoardDTO);

        board.setOwner(userRepository.findById(writeBoardDTO.getOwnerId()).orElseThrow(UserNotFoundException::new));

        return modelMapper.boardEntityToReadDto(boardRepository.save(board));
    }

    @Override
    public ReadBoardDTO getBoard(Long boardId) {
        return modelMapper.boardEntityToReadDto(boardRepository.findById(boardId).orElseThrow(BoardNotFoundException::new));
    }

    @Override
    public List<ReadBoardDTO> getAllBoards(Long userId) {
        Set<Board> boardEntities = new HashSet<>();
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);

        boardEntities.addAll(user.getBoards());
        boardEntities.addAll(user.getOwnedBoards());

        return modelMapper.boardEntitiesToReadDtos(boardEntities);
    }

    @Transactional
    @Override
    public ReadBoardDTO updateBoard(Long taskId, WriteBoardDTO writeBoardDTO) {
        Board board = boardRepository.findById(taskId).orElseThrow(BoardNotFoundException::new);

        board.setName(writeBoardDTO.getName());
        board.setOwner(userRepository.findById(writeBoardDTO.getOwnerId()).orElseThrow(UserNotFoundException::new));

        return modelMapper.boardEntityToReadDto(boardRepository.save(board));
    }

    @Transactional
    @Override
    public void deleteBoard(Long boardId) {
        boardRepository.findById(boardId).orElseThrow(BoardNotFoundException::new);

        boardRepository.deleteById(boardId);
    }
}
