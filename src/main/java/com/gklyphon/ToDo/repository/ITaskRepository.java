package com.gklyphon.ToDo.repository;

import com.gklyphon.ToDo.model.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ITaskRepository extends JpaRepository<Task, Long> {
}
