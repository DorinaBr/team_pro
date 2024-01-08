package com.team.project.tool.services;

import com.team.project.tool.exceptions.BoardNotFoundException;
import com.team.project.tool.exceptions.DuplicateStatusNameException;
import com.team.project.tool.exceptions.InvalidPositionException;
import com.team.project.tool.exceptions.StatusNotFoundException;
import com.team.project.tool.models.ModelMapper;
import com.team.project.tool.models.dtos.ReadStatusDTO;
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
public class StatusServiceImpl implements StatusService {

    private final StatusRepository statusRepository;
    private final ModelMapper modelMapper;
    private final BoardRepository boardRepository;

    @Transactional
    @Override
    public ReadStatusDTO createStatus(Long boardId, WriteStatusDTO writeStatusDTO) {
        Status status = modelMapper.writeStatusDtoToEntity(writeStatusDTO);
        status.setPosition(statusRepository.findMaxPosition(boardId).orElse(0) + 1);
        status.setBoard(boardRepository.findById(boardId).orElseThrow(BoardNotFoundException::new));

        try {
            return modelMapper.statusEntityToReadDto(statusRepository.save(status));
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
    public ReadStatusDTO updateStatus(Long statusId, WriteStatusDTO writeStatusDTO) {
        Status status = statusRepository.findById(statusId).orElseThrow(StatusNotFoundException::new);
        status.setName(writeStatusDTO.getName());

        if (writeStatusDTO.getPosition() != null && !writeStatusDTO.getPosition().equals(status.getPosition())) {
            Integer currentPosition = status.getPosition();
            status.setPosition(writeStatusDTO.getPosition());

            Status statusToSwitchPositionWith = status.getBoard().getStatuses().stream()
                    .filter(e -> e.getPosition().equals(writeStatusDTO.getPosition())).findFirst().orElseThrow(InvalidPositionException::new);
            statusToSwitchPositionWith.setPosition(currentPosition);
            statusRepository.save(statusToSwitchPositionWith);
        }
        return modelMapper.statusEntityToReadDto(statusRepository.save(status));
    }

    @Transactional
    @Override
    public void deleteStatus(Long statusId) {
        statusRepository.findById(statusId).orElseThrow(StatusNotFoundException::new);

        statusRepository.deleteById(statusId);
    }
}
