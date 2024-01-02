package com.team.project.tool.services;

import com.team.project.tool.models.ModelMapper;
import com.team.project.tool.models.dtos.ReadUserDTO;
import com.team.project.tool.models.dtos.WriteUserDTO;
import com.team.project.tool.models.entities.User;
import com.team.project.tool.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Transactional
    @Override
    public Long createUser(WriteUserDTO writeUserDto) {
        return modelMapper.userEntityToReadDto(userRepository.save(modelMapper.writeUserDtoToEntity(writeUserDto))).getId();
    }

    @Override
    public ReadUserDTO readUser(Long id) {
        return modelMapper.userEntityToReadDto(userRepository.findById(id).orElseThrow(EntityNotFoundException::new));
    }

    @Transactional
    @Override
    public void updateUser(Long id, WriteUserDTO writeUserDto) {
        User user = userRepository.findById(id).orElseThrow(EntityNotFoundException::new);

        user.setFirstName(writeUserDto.getFirstName());
        user.setLastName(writeUserDto.getLastName());
        user.setEmail(writeUserDto.getEmail());

        userRepository.save(user);
    }

    @Transactional
    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

}
