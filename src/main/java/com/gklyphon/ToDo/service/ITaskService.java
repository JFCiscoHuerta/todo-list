package com.gklyphon.ToDo.service;

import com.gklyphon.ToDo.model.entity.Task;

import java.util.List;

/**
 * Service interface for managing {@link Task} entities.
 * <p>
 * This interface defines the core operations for working with tasks,
 * including creating, retrieving, updating, and deleting tasks.
 * </p>
 *
 * @author JFCiscoHuerta
 * @version 1.0
 * @since 28-Oct-2024
 */
public interface ITaskService {

    /**
     * Service interface for managing {@link Task} entities.
     * <p>
     * This interface defines the core operations for working with tasks,
     * including creating, retrieving, updating, and deleting tasks.
     * </p>
     */
    public List<Task> getAllTasks();

    /**
     * Retrieves a specific task by its ID.
     *
     * @param id the unique identifier of the task
     * @return the {@link Task} entity if found
     * @throws com.gklyphon.ToDo.exception.custom.ElementNotFoundException if no task is found with the given ID
     */
    public Task getTaskById(Long id);

    /**
     * Saves a new task to the database.
     *
     * @param task the {@link Task} entity to be saved
     * @return the saved {@link Task} entity
     */
    public Task saveTask(Task task);

    /**
     * Deletes a task by its ID.
     *
     * @param id the unique identifier of the task to be deleted
     * @return {@code true} if the task was successfully deleted, {@code false} otherwise
     */
    public boolean deleteTaskById(Long id);

    /**
     * Updates an existing task with new information.
     *
     * @param id the unique identifier of the task to be updated
     * @param task the new task data
     * @return the updated {@link Task} entity
     * @throws com.gklyphon.ToDo.exception.custom.ElementNotFoundException if no task is found with the given ID
     */
    public Task updateTask(Long id, Task task);

    /**
     * Updates the completion status of a specific task.
     *
     * @param id the unique identifier of the task
     * @param complete the new completion status (true for completed, false for incomplete)
     * @return the updated {@link Task} entity
     * @throws com.gklyphon.ToDo.exception.custom.ElementNotFoundException if no task is found with the given ID
     */
    public Task updateTaskComplete(Long id, boolean complete);
}
