package com.team.project.tool.services;

import com.team.project.tool.exceptions.BoardNotFoundException;
import com.team.project.tool.exceptions.InvalidPositionException;
import com.team.project.tool.exceptions.StatusNotFoundException;
import com.team.project.tool.models.ModelMapper;
import com.team.project.tool.models.dtos.ReadStatusDTO;
import com.team.project.tool.models.dtos.WriteStatusDTO;
import com.team.project.tool.models.entities.Status;
import com.team.project.tool.repositories.BoardRepository;
import com.team.project.tool.repositories.StatusRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
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

        Status savedStatus = statusRepository.save(status);
        log.info("Saved Status with id {}, in the database.", savedStatus.getId());

        return modelMapper.statusEntityToReadDto(savedStatus);
    }

    @Transactional
    @Override
    public ReadStatusDTO updateStatus(Long statusId, WriteStatusDTO writeStatusDTO) {
        Status status = statusRepository.findById(statusId).orElseThrow(StatusNotFoundException::new);
        status.setName(writeStatusDTO.getName());

        if (writeStatusDTO.getPosition() != null && !writeStatusDTO.getPosition().equals(status.getPosition())) {
            status.setPosition(writeStatusDTO.getPosition());

            Status statusToSwitchPositionWith = status.getBoard().getStatuses().stream()
                    .filter(e -> e.getPosition().equals(writeStatusDTO.getPosition())).findFirst().orElseThrow(InvalidPositionException::new);
            statusToSwitchPositionWith.setPosition(status.getPosition());
            statusRepository.save(statusToSwitchPositionWith);
            log.info("Updated Status with id {}, in the database.", statusToSwitchPositionWith.getId());
        }

        Status savedStatus = statusRepository.save(status);
        log.info("Updated Status with id {}, in the database.", statusId);

        return modelMapper.statusEntityToReadDto(savedStatus);
    }

    @Transactional
    @Override
    public void deleteStatus(Long statusId) {
        statusRepository.findById(statusId).orElseThrow(StatusNotFoundException::new);

        statusRepository.deleteById(statusId);

        log.info("Deleted Status with id {}, from the database.", statusId);
    }
}
