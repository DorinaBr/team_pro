package com.team.project.tool.services;

import com.team.project.tool.exceptions.UserNotFoundException;
import com.team.project.tool.models.ModelMapper;
import com.team.project.tool.models.dtos.ReadUserDTO;
import com.team.project.tool.models.dtos.WriteUserDTO;
import com.team.project.tool.models.entities.User;
import com.team.project.tool.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Transactional
    @Override
    public ReadUserDTO createUser(WriteUserDTO writeUserDTO) {
        User savedUser = userRepository.save(modelMapper.writeUserDtoToEntity(writeUserDTO));
        log.info("Saved User with id {}, in the database.", savedUser.getId());

        return modelMapper.userEntityToReadDto(savedUser);
    }

    @Override
    public ReadUserDTO getUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        log.info("Found User with id {}, in the database.", userId);

        return modelMapper.userEntityToReadDto(user);
    }

    @Transactional
    @Override
    public ReadUserDTO updateUser(Long userId, WriteUserDTO writeUserDTO) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);

        user.setFirstName(writeUserDTO.getFirstName());
        user.setLastName(writeUserDTO.getLastName());
        user.setEmail(writeUserDTO.getEmail());

        User updatedUser = userRepository.save(user);
        log.info("Updated User with id {}, in the database.", userId);

        return modelMapper.userEntityToReadDto(updatedUser);
    }

    @Transactional
    @Override
    public void deleteUser(Long userId) {
        userRepository.findById(userId).orElseThrow(UserNotFoundException::new);

        userRepository.deleteById(userId);

        log.info("Deleted User with id {}, from the database.", userId);
    }
}
