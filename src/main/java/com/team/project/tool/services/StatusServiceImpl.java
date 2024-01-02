package com.team.project.tool.services;

import com.team.project.tool.exceptions.DuplicateStatusNameException;
import com.team.project.tool.exceptions.InvalidPositionException;
import com.team.project.tool.models.ModelMapper;
import com.team.project.tool.models.dtos.WriteStatusDTO;
import com.team.project.tool.models.entities.Status;
import com.team.project.tool.repositories.BoardRepository;
import com.team.project.tool.repositories.StatusRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class StatusServiceImpl implements StatusService{

    private final StatusRepository statusRepository;
    private final ModelMapper modelMapper;
    private final BoardRepository boardRepository;

    @Transactional
    @Override
    public Long createStatus(Long boardId, WriteStatusDTO writeStatusDto) {
        Status status = modelMapper.writeStatusDtoToEntity(writeStatusDto);
        status.setPosition(statusRepository.findMaxPosition(boardId).orElse(0) + 1);
        status.setBoard(boardRepository.findById(boardId).orElseThrow(EntityNotFoundException::new));

        try {
            return statusRepository.save(status).getId();
        } catch (RuntimeException e) {
            if (e.getMessage().contains("duplicate key value violates unique constraint")) {
                throw new DuplicateStatusNameException(e);
            } else {
                throw e;
            }
        }
    }

    @Transactional
    @Override
    public void updateStatus(Long id, WriteStatusDTO writeStatusDto) {
        Status status = statusRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        status.setName(writeStatusDto.getName());

        if (writeStatusDto.getPosition() != null && !writeStatusDto.getPosition().equals(status.getPosition())) {
            Integer currentPosition = status.getPosition();
            status.setPosition(writeStatusDto.getPosition());

            Status statusToSwitchPositionWith = status.getBoard().getStatuses().stream()
                    .filter(e -> e.getPosition().equals(writeStatusDto.getPosition())).findFirst().orElseThrow(InvalidPositionException::new);
            statusToSwitchPositionWith.setPosition(currentPosition);
            statusRepository.save(statusToSwitchPositionWith);
        }
        statusRepository.save(status);
    }

    @Transactional
    @Override
    public void deleteStatus(Long id) {
        statusRepository.deleteById(id);
    }
}
