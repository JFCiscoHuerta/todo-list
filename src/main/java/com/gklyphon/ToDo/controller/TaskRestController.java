package com.gklyphon.ToDo.controller;

import com.gklyphon.ToDo.model.entity.Task;
import com.gklyphon.ToDo.repository.ITaskRepository;
import com.gklyphon.ToDo.service.ITaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing tasks.
 * <p>
 * This controller provides endpoints to create, retrieve, update, and delete tasks.
 * </p>
 *
 * @author JFCiscoHuerta
 * @version 1.0
 * @since 28-Oct-2024
 */
@RestController
@RequestMapping("/v1/tasks")
@RequiredArgsConstructor
public class TaskRestController {

    private final ITaskService taskService;

    /**
     * Retrieves all tasks from the service.
     *
     * @return a {@link ResponseEntity} containing the list of tasks or an HTTP status
     *         indicating that no tasks are available.
     */
    @GetMapping
    public ResponseEntity<List<Task>> getAllTasks() {
        List<Task> tasks = taskService.getAllTasks();
        if (tasks.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    /**
     * Retrieves a task by its ID.
     *
     * @param id the ID of the task to retrieve
     * @return a {@link ResponseEntity} containing the task or an HTTP status
     *         indicating that the task was not found.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Task> getById(
            @PathVariable Long id
    ) {
        Task task = taskService.getTaskById(id);
        return new ResponseEntity<>(task, HttpStatus.OK);
    }

    /**
     * Creates a new task.
     *
     * @param task the task to create
     * @param result the binding result containing validation errors, if any
     * @return a {@link ResponseEntity} with the created task or a list of error messages
     *         if validation fails.
     */
    @PostMapping("/create-task")
    public ResponseEntity<?> save(
            @Valid @RequestBody Task task,
            BindingResult result
    ) {
        if (result.hasErrors()) {
            List<String> errorMessages = result.getFieldErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .toList();
            return new ResponseEntity<>(errorMessages, HttpStatus.BAD_REQUEST);
        }
        Task taskCreated = taskService.saveTask(task);
        return new ResponseEntity<>(taskCreated, HttpStatus.CREATED);
    }

    /**
     * Deletes a task by its ID.
     *
     * @param id the ID of the task to delete
     * @return a {@link ResponseEntity} indicating the result of the deletion.
     */
    @DeleteMapping("/delete-task/{id}")
    public ResponseEntity<?> deleteTask(
            @PathVariable Long id
    ) {
        if (id<=0) {
            return new ResponseEntity<>("Id must be greater than 0.", HttpStatus.BAD_REQUEST);
        }

        boolean isDeleted = taskService.deleteTaskById(id);
        if (!isDeleted) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Updates an existing task by its ID.
     *
     * @param id the ID of the task to update
     * @param task the updated task details
     * @param result the binding result containing validation errors, if any
     * @return a {@link ResponseEntity} with the updated task or a list of error messages
     *         if validation fails.
     */
    @PutMapping("/update-task/{id}")
    public ResponseEntity<?> updateTask(
            @PathVariable Long id,
            @Valid @RequestBody Task task, BindingResult result
    ) {
        if (result.hasErrors()) {
            List<String> errorMessages = result.getFieldErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .toList();
            return new ResponseEntity<>(errorMessages, HttpStatus.BAD_REQUEST);
        }

        Task taskUpdated = taskService.updateTask(id, task);
        return new ResponseEntity<>(taskUpdated, HttpStatus.OK);
    }

    /**
     * Updates the completion status of a task.
     *
     * @param id the ID of the task to update
     * @param complete the new completion status
     * @return a {@link ResponseEntity} containing the updated task.
     */
    @PutMapping("/update-complete-task/{id}")
    public ResponseEntity<?> updateCompleteTask(
            @PathVariable Long id,
            @RequestParam boolean complete
    ) {
        Task task = taskService.updateTaskComplete(id, complete);
        return new ResponseEntity<>(task, HttpStatus.OK);
    }

}
