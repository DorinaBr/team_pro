package com.team.project.tool.services;

import com.team.project.tool.models.dtos.ReadStatusDTO;
import com.team.project.tool.models.dtos.WriteStatusDTO;

public interface StatusService {

    ReadStatusDTO createStatus(Long boardId, WriteStatusDTO writeStatusDTO);

    ReadStatusDTO updateStatus(Long statusId, WriteStatusDTO writeStatusDTO);

    void deleteStatus(Long statusId);
}
