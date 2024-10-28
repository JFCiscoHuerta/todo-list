package com.gklyphon.ToDo;

import com.gklyphon.ToDo.model.entity.Task;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class Data {

    public final static Task TASK = new Task(1L, "Buy Milk", false, LocalDate.of(2024, 10, 31), LocalDateTime.now(), LocalDateTime.now());
    public final static Task TASK2 = new Task(2L, "Buy Milk", true, LocalDate.of(2024, 10, 31), LocalDateTime.now(), LocalDateTime.now());
    public final static List<Task> TASKS = List.of(
            new Task(1L, "Buy Milk", false, LocalDate.of(2024, 10, 31), LocalDateTime.now(), LocalDateTime.now()),
            new Task(2L, "Buy Beans", false, LocalDate.of(2024, 8, 12), LocalDateTime.now(), LocalDateTime.now())
    );

}
