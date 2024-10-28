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

@RestController
@RequestMapping("/v1/tasks")
@RequiredArgsConstructor
public class TaskRestController {

    private final ITaskService taskService;

    @GetMapping
    public ResponseEntity<List<Task>> getAllTasks() {
        List<Task> tasks = taskService.getAllTasks();
        if (tasks.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> getById(
            @PathVariable Long id
    ) {
        Task task = taskService.getTaskById(id);
        return new ResponseEntity<>(task, HttpStatus.OK);
    }

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

    @PutMapping("/update-complete-task/{id}")
    public ResponseEntity<?> updateCompleteTask(
            @PathVariable Long id,
            @RequestParam boolean complete
    ) {
        Task task = taskService.updateTaskComplete(id, complete);
        return new ResponseEntity<>(task, HttpStatus.OK);
    }

}
