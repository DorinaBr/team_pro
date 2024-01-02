package com.team.project.tool.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@Builder
@Table(name = "board")
@NoArgsConstructor
@AllArgsConstructor
public class BoardEntity {
    @Id
    @GeneratedValue
    private Long id;
    @Column(nullable = false)
    private String name;
    @ManyToOne(optional = false)
    @JoinColumn(name = "owner_id")
    private UserEntity owner;
    @ManyToMany(mappedBy = "boards")
    private List<UserEntity> users;
    @OneToMany(mappedBy = "board")
    private List<StatusEntity> statuses;
    @OneToMany(mappedBy = "board")
    private List<TaskEntity> tasks;
}
