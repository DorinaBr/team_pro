package com.team.project.tool.repositories;

import com.team.project.tool.models.entities.Board;
import com.team.project.tool.models.entities.Status;
import com.team.project.tool.models.entities.Task;
import com.team.project.tool.models.entities.User;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class TaskSpecifications {

    public Specification<Task> hasStatus(Status status) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("status"), status);
    }

    public Specification<Task> isOnBoard(Board board) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("board"), board);
    }

    public Specification<Task> hasUser(User user) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.isMember(user, root.get("users"));
    }

    public Specification<Task> titleContains(String titlePart) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("title"), "%" + titlePart + "%");
    }

    public Specification<Task> descriptionContains(String descriptionPart) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("description"), "%" + descriptionPart + "%");
    }

    public Specification<Task> isCreatedBy(User createdBy) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("createdBy"), createdBy);
    }
}
