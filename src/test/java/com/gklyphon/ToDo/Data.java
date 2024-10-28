package com.gklyphon.ToDo;

import com.gklyphon.ToDo.model.entity.Task;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Utility class that contains sample data for {@link Task} entities.
 * <p>
 * This class provides static instances of tasks and a list of tasks to be used for testing or initialization purposes.
 * </p>
 *
 * @author JFCiscoHuerta
 * @version 1.0
 * @since 28-Oct-2024
 */
public class Data {

    public final static Task TASK = new Task(1L, "Buy Milk", false, LocalDate.of(2024, 10, 31), LocalDateTime.now(), LocalDateTime.now());
    public final static Task TASK2 = new Task(2L, "Buy Milk", true, LocalDate.of(2024, 10, 31), LocalDateTime.now(), LocalDateTime.now());
    public final static List<Task> TASKS = List.of(
            new Task(1L, "Buy Milk", false, LocalDate.of(2024, 10, 31), LocalDateTime.now(), LocalDateTime.now()),
            new Task(2L, "Buy Beans", false, LocalDate.of(2024, 8, 12), LocalDateTime.now(), LocalDateTime.now())
    );

}
