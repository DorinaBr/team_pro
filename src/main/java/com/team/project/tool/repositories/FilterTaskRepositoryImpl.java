package com.team.project.tool.repositories;

import com.team.project.tool.exceptions.BoardNotFoundException;
import com.team.project.tool.exceptions.StatusNotFoundException;
import com.team.project.tool.exceptions.UserNotFoundException;
import com.team.project.tool.models.entities.Board;
import com.team.project.tool.models.entities.Status;
import com.team.project.tool.models.entities.Task;
import com.team.project.tool.models.entities.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Repository
public class FilterTaskRepositoryImpl implements FilterTaskRepository {

    private final TaskRepository taskRepository;
    private final StatusRepository statusRepository;
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;

    @Override
    public List<Task> findFilteredTasks(Long boardId, Long userId, String titlePart, String descriptionPart, Long statusId, Long createdById) {
        Specification<Task> specification = Specification.where(null);

        if (boardId != null) {
            specification = specification.and(isOnBoard(boardRepository.findById(boardId).orElseThrow(BoardNotFoundException::new)));
        }
        if (userId != null) {
            specification = specification.and(hasUser(userRepository.findById(userId).orElseThrow(UserNotFoundException::new)));
        }
        if (titlePart != null) {
            specification = specification.and(titleContains(titlePart));
        }
        if (descriptionPart != null) {
            specification = specification.and(descriptionContains(descriptionPart));
        }
        if (statusId != null) {
            specification = specification.and(hasStatus(statusRepository.findById(statusId).orElseThrow(StatusNotFoundException::new)));
        }
        if (createdById != null) {
            specification = specification.and(isCreatedBy(userRepository.findById(createdById).orElseThrow(UserNotFoundException::new)));
        }

        return taskRepository.findAll(specification, Sort.by(Sort.Direction.DESC, "createdAt"));
    }

    private Specification<Task> hasStatus(Status status) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("status"), status);
    }

    private Specification<Task> isOnBoard(Board board) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("board"), board);
    }

    private Specification<Task> hasUser(User user) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.isMember(user, root.get("users"));
    }

    private Specification<Task> titleContains(String titlePart) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("title"), "%" + titlePart + "%");
    }

    private Specification<Task> descriptionContains(String descriptionPart) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("description"), "%" + descriptionPart + "%");
    }

    private Specification<Task> isCreatedBy(User createdBy) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("createdBy"), createdBy);
    }

}
