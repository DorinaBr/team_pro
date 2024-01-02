package com.team.project.tool.service;

import com.team.project.tool.model.ModelMapper;
import com.team.project.tool.model.dto.ReadBoardDto;
import com.team.project.tool.model.dto.WriteBoardDto;
import com.team.project.tool.model.entity.BoardEntity;
import com.team.project.tool.model.entity.UserEntity;
import com.team.project.tool.repository.BoardRepository;
import com.team.project.tool.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository repository;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;

    public Long create(WriteBoardDto dto) {
        BoardEntity entity = modelMapper.writeBoardDtoToEntity(dto);
        entity.setOwner(userRepository.findById(dto.getOwnerId()).orElseThrow(EntityNotFoundException::new));

        return modelMapper.boardEntityToReadDto(repository.save(entity)).getId();
    }

    public ReadBoardDto read(Long id) {
        return modelMapper.boardEntityToReadDto(repository.findById(id).orElseThrow(EntityNotFoundException::new));
    }

    public List<ReadBoardDto> readAll(Long userId) {
        Set<BoardEntity> boardEntities = new HashSet<>();

        UserEntity userEntity = userRepository.findById(userId).orElseThrow(EntityNotFoundException::new);
        boardEntities.addAll(userEntity.getBoards());
        boardEntities.addAll(userEntity.getOwnedBoards());

        return modelMapper.boardEntitiesToReadDtos(boardEntities);
    }

    public void update(Long id, WriteBoardDto dto) {
        BoardEntity entity = repository.findById(id).orElseThrow(EntityNotFoundException::new);
        entity.setName(dto.getName());
        entity.setOwner(userRepository.findById(dto.getOwnerId()).orElseThrow(EntityNotFoundException::new));

        repository.save(entity);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
