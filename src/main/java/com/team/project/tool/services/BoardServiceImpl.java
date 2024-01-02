package com.team.project.tool.services;

import com.team.project.tool.models.ModelMapper;
import com.team.project.tool.models.dtos.ReadBoardDTO;
import com.team.project.tool.models.dtos.WriteBoardDTO;
import com.team.project.tool.models.entities.Board;
import com.team.project.tool.models.entities.User;
import com.team.project.tool.repositories.BoardRepository;
import com.team.project.tool.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
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
    public Long createBoard(WriteBoardDTO writeBoardDto) {
        Board board = modelMapper.writeBoardDtoToEntity(writeBoardDto);

        board.setOwner(userRepository.findById(writeBoardDto.getOwnerId()).orElseThrow(EntityNotFoundException::new));

        return modelMapper.boardEntityToReadDto(boardRepository.save(board)).getId();
    }

    @Override
    public ReadBoardDTO readBoard(Long id) {
        return modelMapper.boardEntityToReadDto(boardRepository.findById(id).orElseThrow(EntityNotFoundException::new));
    }

    @Override
    public List<ReadBoardDTO> readAllBoards(Long userId) {
        Set<Board> boardEntities = new HashSet<>();
        User user = userRepository.findById(userId).orElseThrow(EntityNotFoundException::new);

        boardEntities.addAll(user.getBoards());
        boardEntities.addAll(user.getOwnedBoards());

        return modelMapper.boardEntitiesToReadDtos(boardEntities);
    }

    @Transactional
    @Override
    public void updateBoard(Long id, WriteBoardDTO writeBoardDto) {
        Board board = boardRepository.findById(id).orElseThrow(EntityNotFoundException::new);

        board.setName(writeBoardDto.getName());
        board.setOwner(userRepository.findById(writeBoardDto.getOwnerId()).orElseThrow(EntityNotFoundException::new));

        boardRepository.save(board);
    }

    @Transactional
    @Override
    public void deleteBoard(Long id) {
        boardRepository.deleteById(id);
    }

}
