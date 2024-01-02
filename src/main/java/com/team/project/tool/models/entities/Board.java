package com.team.project.tool.models.entities;

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
public class Board {
    @Id
    @GeneratedValue
    private Long id;
    @Column(nullable = false)
    private String name;
    @ManyToOne(optional = false)
    @JoinColumn(name = "owner_id")
    private User owner;
    @ManyToMany(mappedBy = "boards")
    private List<User> users;
    @OneToMany(mappedBy = "board")
    private List<Status> statuses;
    @OneToMany(mappedBy = "board")
    private List<Task> tasks;
}
