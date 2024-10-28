package com.gklyphon.ToDo.service;

import com.gklyphon.ToDo.exception.custom.ElementNotFoundException;
import com.gklyphon.ToDo.model.entity.Task;
import com.gklyphon.ToDo.repository.ITaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements ITaskService {

    private final ITaskRepository taskRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Task getTaskById(Long id) {
        return taskRepository.findById(id).orElseThrow(
                () -> new ElementNotFoundException("Task with id: " + id + " not found."));
    }

    @Override
    @Transactional
    public Task saveTask(Task task) {
        task.setCreateAt(LocalDateTime.now());
        return taskRepository.save(task);
    }

    @Override
    @Transactional
    public boolean deleteTaskById(Long id) {
        if (!taskRepository.existsById(id)) {
            return false;
        }
        taskRepository.deleteById(id);
        return true;
    }

    @Override
    @Transactional
    public Task updateTask(Long id, Task task) {
        Task originalTask = getTaskById(id);
        originalTask.setName(task.getName());
        originalTask.setComplete(task.isComplete());
        originalTask.setDueTime(task.getDueTime());
        originalTask.setUpdateAt(LocalDateTime.now());
        return taskRepository.save(originalTask);
    }

    @Override
    @Transactional
    public Task updateTaskComplete(Long id, boolean complete) {
        Task originalTask = getTaskById(id);
        originalTask.setComplete(complete);
        originalTask.setUpdateAt(LocalDateTime.now());
        return taskRepository.save(originalTask);
    }
}
