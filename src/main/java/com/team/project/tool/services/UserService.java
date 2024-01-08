package com.team.project.tool.services;

import com.team.project.tool.models.dtos.ReadUserDTO;
import com.team.project.tool.models.dtos.WriteUserDTO;

public interface UserService {

    ReadUserDTO createUser(WriteUserDTO writeUserDTO);

    ReadUserDTO getUser(Long userId);

    ReadUserDTO updateUser(Long userId, WriteUserDTO writeUserDTO);

    void deleteUser(Long userId);
}
