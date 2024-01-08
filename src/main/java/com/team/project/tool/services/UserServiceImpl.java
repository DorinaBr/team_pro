package com.team.project.tool.services;

import com.team.project.tool.exceptions.UserNotFoundException;
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
    public ReadUserDTO createUser(WriteUserDTO writeUserDTO) {
        return modelMapper.userEntityToReadDto(userRepository.save(modelMapper.writeUserDtoToEntity(writeUserDTO)));
    }

    @Override
    public ReadUserDTO getUser(Long userId) {
        return modelMapper.userEntityToReadDto(userRepository.findById(userId).orElseThrow(UserNotFoundException::new));
    }

    @Transactional
    @Override
    public ReadUserDTO updateUser(Long userId, WriteUserDTO writeUserDTO) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);

        user.setFirstName(writeUserDTO.getFirstName());
        user.setLastName(writeUserDTO.getLastName());
        user.setEmail(writeUserDTO.getEmail());

        return modelMapper.userEntityToReadDto(userRepository.save(user));
    }

    @Transactional
    @Override
    public void deleteUser(Long userId) {
        userRepository.findById(userId).orElseThrow(UserNotFoundException::new);

        userRepository.deleteById(userId);
    }
}
