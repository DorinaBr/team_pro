package com.team.project.tool.models.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Builder
@Table(name = "task")
@NoArgsConstructor
@AllArgsConstructor
public class Task {
    @Id
    @GeneratedValue
    private Long id;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String description;
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;
    @ManyToOne(optional = false)
    @JoinColumn(name = "status_id")
    private Status status;
    @ManyToOne(optional = false)
    @JoinColumn(name = "board_id", updatable = false)
    private Board board;
    @ManyToMany
    @JoinTable(
            name = "task_user",
            joinColumns = @JoinColumn(name = "task_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> users;
    @ManyToOne(optional = false)
    @JoinColumn(name = "created_by_id", updatable = false)
    private User createdBy;
}
