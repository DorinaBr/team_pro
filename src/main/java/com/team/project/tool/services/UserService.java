package com.team.project.tool.services;

import com.team.project.tool.models.dtos.ReadUserDTO;
import com.team.project.tool.models.dtos.WriteUserDTO;

public interface UserService {

    Long createUser(WriteUserDTO dto);

    ReadUserDTO readUser(Long id);

    void updateUser(Long id, WriteUserDTO dto);

    void deleteUser(Long id);
}
