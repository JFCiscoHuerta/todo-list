package com.gklyphon.ToDo.service;

import com.gklyphon.ToDo.model.entity.Task;

import java.util.List;

public interface ITaskService {
    public List<Task> getAllTasks();
    public Task getTaskById(Long id);
    public Task saveTask(Task task);
    public boolean deleteTaskById(Long id);
    public Task updateTask(Long id, Task task);
    public Task updateTaskComplete(Long id, boolean complete);
}
