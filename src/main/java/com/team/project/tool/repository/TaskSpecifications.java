package com.team.project.tool.repository;

import com.team.project.tool.model.entity.BoardEntity;
import com.team.project.tool.model.entity.StatusEntity;
import com.team.project.tool.model.entity.TaskEntity;
import com.team.project.tool.model.entity.UserEntity;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class TaskSpecifications {

    public Specification<TaskEntity> hasStatus(StatusEntity status) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("status"), status);
    }

    public Specification<TaskEntity> isOnBoard(BoardEntity board) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("board"), board);
    }

    public Specification<TaskEntity> hasUser(UserEntity user) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.isMember(user, root.get("users"));
    }

    public Specification<TaskEntity> titleContains(String titlePart) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("title"), "%" + titlePart + "%");
    }

    public Specification<TaskEntity> descriptionContains(String descriptionPart) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("description"), "%" + descriptionPart + "%");
    }

    public Specification<TaskEntity> isCreatedBy(UserEntity createdBy) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("createdBy"), createdBy);
    }
}
