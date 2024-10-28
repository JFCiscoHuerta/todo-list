package com.gklyphon.ToDo.service;

import com.gklyphon.ToDo.exception.custom.ElementNotFoundException;
import com.gklyphon.ToDo.model.entity.Task;
import com.gklyphon.ToDo.repository.ITaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Implementation of the {@link ITaskService} interface.
 * <p>
 * This service provides transactional operations for managing {@link Task} entities,
 * including creating, updating, retrieving, and deleting tasks from the repository.
 * </p>
 *
 * @author JFCiscoHuerta
 * @version 1.0
 * @since 28-Oct-2024
 */
@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements ITaskService {

    private final ITaskRepository taskRepository;

    /**
     * Retrieves all tasks from the database.
     *
     * @return a list of all {@link Task} entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    /**
     * Retrieves a task by its ID.
     *
     * @param id the unique identifier of the task
     * @return the {@link Task} entity
     * @throws ElementNotFoundException if no task is found with the given ID
     */
    @Override
    @Transactional(readOnly = true)
    public Task getTaskById(Long id) {
        return taskRepository.findById(id).orElseThrow(
                () -> new ElementNotFoundException("Task with id: " + id + " not found."));
    }

    /**
     * Saves a new task to the database with the current timestamp.
     *
     * @param task the {@link Task} entity to be saved
     * @return the saved {@link Task} entity
     */
    @Override
    @Transactional
    public Task saveTask(Task task) {
        task.setCreateAt(LocalDateTime.now());
        return taskRepository.save(task);
    }

    /**
     * Deletes a task by its ID if it exists.
     *
     * @param id the unique identifier of the task to be deleted
     * @return {@code true} if the task was deleted, {@code false} if not found
     */
    @Override
    @Transactional
    public boolean deleteTaskById(Long id) {
        if (!taskRepository.existsById(id)) {
            return false;
        }
        taskRepository.deleteById(id);
        return true;
    }

    /**
     * Updates an existing task's data.
     *
     * @param id the unique identifier of the task to be updated
     * @param task the updated task data
     * @return the updated {@link Task} entity
     * @throws ElementNotFoundException if no task is found with the given ID
     */
    @Override
    @Transactional
    public Task updateTask(Long id, Task task) {
        Task originalTask = getTaskById(id);
        originalTask.setName(task.getName());
        originalTask.setComplete(task.isComplete());
        originalTask.setDueDate(task.getDueDate());
        originalTask.setUpdateAt(LocalDateTime.now());
        return taskRepository.save(originalTask);
    }

    /**
     * Updates the completion status of a task.
     *
     * @param id the unique identifier of the task
     * @param complete the new completion status
     * @return the updated {@link Task} entity
     * @throws ElementNotFoundException if no task is found with the given ID
     */
    @Override
    @Transactional
    public Task updateTaskComplete(Long id, boolean complete) {
        Task originalTask = getTaskById(id);
        originalTask.setComplete(complete);
        originalTask.setUpdateAt(LocalDateTime.now());
        return taskRepository.save(originalTask);
    }
}
