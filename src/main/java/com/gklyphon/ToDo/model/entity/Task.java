package com.gklyphon.ToDo.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Represents a task entity in the to-do application.
 * <p>
 * This class maps to the {@code tasks} table in the database and
 * contains the relevant fields for tracking tasks, such as name, status,
 * due date, and timestamps for creation and updates.
 * </p>
 *
 * @author JFCiscoHuerta
 * @version 1.0
 * @since 28-Oct-2024
 */
@Entity
@Table(name = "tasks")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Task {
    /** The unique identifier of the task. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /** The name or title of the task. */
    private String name;
    /** Indicates whether the task is completed or not. */
    private boolean complete;
    /** The due date by which the task should be completed. */
    private LocalDate dueDate;
    /** The timestamp when the task was created. */
    private LocalDateTime createAt;
    /** The timestamp when the task was last updated. */
    private LocalDateTime updateAt;
}
