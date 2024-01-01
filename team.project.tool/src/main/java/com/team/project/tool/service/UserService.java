package com.team.project.tool.service;

import com.team.project.tool.model.ModelMapper;
import com.team.project.tool.model.dto.ReadUserDto;
import com.team.project.tool.model.dto.WriteUserDto;
import com.team.project.tool.model.entity.UserEntity;
import com.team.project.tool.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;
    private final ModelMapper modelMapper;

    public Long create(WriteUserDto dto) {
        return modelMapper.userEntityToReadDto(repository.save(modelMapper.writeUserDtoToEntity(dto))).getId();
    }

    public ReadUserDto read(Long id) {
        return modelMapper.userEntityToReadDto(repository.findById(id).orElseThrow(EntityNotFoundException::new));
    }

    public void update(Long id, WriteUserDto dto) {
        UserEntity entity = repository.findById(id).orElseThrow(EntityNotFoundException::new);

        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setEmail(dto.getEmail());

        repository.save(entity);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
