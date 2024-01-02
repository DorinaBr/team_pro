package com.team.project.tool.repositories;

import com.team.project.tool.models.entities.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StatusRepository extends JpaRepository<Status, Long> {
    @Query(value = "SELECT MAX(s.position) FROM status s WHERE s.board_id = :board_id", nativeQuery = true)
    Optional<Integer> findMaxPosition(@Param("board_id") Long boardId);
}
