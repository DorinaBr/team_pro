package com.team.project.tool.service;

import com.team.project.tool.exception.DuplicateStatusNameException;
import com.team.project.tool.exception.InvalidPositionException;
import com.team.project.tool.model.ModelMapper;
import com.team.project.tool.model.dto.WriteStatusDto;
import com.team.project.tool.model.entity.StatusEntity;
import com.team.project.tool.repository.BoardRepository;
import com.team.project.tool.repository.StatusRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StatusService {

    private final StatusRepository repository;
    private final ModelMapper modelMapper;
    private final BoardRepository boardRepository;

    public Long create(Long boardId, WriteStatusDto dto) {
        StatusEntity entity = modelMapper.writeStatusDtoToEntity(dto);
        entity.setPosition(repository.findMaxPosition(boardId).orElse(0) + 1);
        entity.setBoard(boardRepository.findById(boardId).orElseThrow(EntityNotFoundException::new));

        try {
            return repository.save(entity).getId();
        } catch (RuntimeException e) {
            if (e.getMessage().contains("duplicate key value violates unique constraint")) {
                throw new DuplicateStatusNameException(e);
            } else {
                throw e;
            }
        }
    }

    public void update(Long id, WriteStatusDto dto) {
        StatusEntity entity = repository.findById(id).orElseThrow(EntityNotFoundException::new);
        entity.setName(dto.getName());

        if (dto.getPosition() != null && !dto.getPosition().equals(entity.getPosition())) {
            Integer currentPosition = entity.getPosition();
            entity.setPosition(dto.getPosition());

            StatusEntity entityToSwitchPositionWith = entity.getBoard().getStatuses().stream()
                    .filter(e -> e.getPosition().equals(dto.getPosition())).findFirst().orElseThrow(InvalidPositionException::new);
            entityToSwitchPositionWith.setPosition(currentPosition);
            repository.save(entityToSwitchPositionWith);
        }
        repository.save(entity);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
