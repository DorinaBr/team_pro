package com.team.project.tool.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@Table(name = "status", uniqueConstraints = @UniqueConstraint(columnNames = {"name", "board_id"}))
@NoArgsConstructor
@AllArgsConstructor
public class StatusEntity {
    @Id
    @GeneratedValue
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private Integer position;
    @ManyToOne(optional = false)
    @JoinColumn(name = "board_id")
    private BoardEntity board;
}
