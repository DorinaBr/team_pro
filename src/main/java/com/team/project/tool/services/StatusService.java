package com.team.project.tool.services;

import com.team.project.tool.models.dtos.WriteStatusDTO;

public interface StatusService {

    Long createStatus(Long boardId, WriteStatusDTO dto);

    void updateStatus(Long id, WriteStatusDTO dto);

    void deleteStatus(Long id);
}
