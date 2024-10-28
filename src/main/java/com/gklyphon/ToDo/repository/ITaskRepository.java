package com.gklyphon.ToDo.repository;

import com.gklyphon.ToDo.model.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for managing {@link Task} entities.
 * <p>
 * This interface extends {@link JpaRepository}, providing CRUD operations
 * and other database interactions for the {@code Task} entity.
 * </p>
 * <p>
 * No implementation is required as Spring Data JPA automatically provides
 * the implementation at runtime.
 * </p>
 *
 * @author JFCiscoHuerta
 * @version 1.0
 * @since 28-Oct-2024
 */
public interface ITaskRepository extends JpaRepository<Task, Long> {
}
