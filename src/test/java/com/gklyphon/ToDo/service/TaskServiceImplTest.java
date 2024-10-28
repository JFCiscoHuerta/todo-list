package com.gklyphon.ToDo.service;

import com.gklyphon.ToDo.Data;
import com.gklyphon.ToDo.exception.custom.ElementNotFoundException;
import com.gklyphon.ToDo.model.entity.Task;
import com.gklyphon.ToDo.repository.ITaskRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

/**
 * Unit tests for the {@link TaskServiceImpl} class.
 * <p>
 * This class tests the various functionalities of the Task service, including retrieving, saving, updating, and deleting tasks.
 * It uses Mockito for mocking dependencies and assertions to verify expected behavior.
 * </p>
 *
 * @author JFCiscoHuerta
 * @version 1.0
 * @since 28-Oct-2024
 */
@ExtendWith(MockitoExtension.class)
class TaskServiceImplTest {

    @Mock
    ITaskRepository taskRepository;

    @InjectMocks
    TaskServiceImpl taskService;

    /**
     * Test for retrieving all tasks.
     * <p>
     * This test verifies that the method returns a non-empty list of tasks and checks the properties of the first task.
     * </p>
     */
    @Test
    void shouldRetrieveAllTasks() {
        when(taskRepository.findAll()).thenReturn(Data.TASKS);
        List<Task> tasks = taskService.getAllTasks();
        assertAll( ()-> {
                assertFalse(tasks.isEmpty());
                assertEquals(2, tasks.size());
                assertEquals("Buy Milk", tasks.getFirst().getName());
                assertFalse(tasks.getFirst().isComplete());
                }
        );
        verify(taskRepository).findAll();
    }

    /**
     * Test for retrieving a task by its ID.
     * <p>
     * This test checks that the correct task is returned when a valid ID is provided.
     * </p>
     */
    @Test
    void shouldRetrieveTaskById() {
        when(taskRepository.findById(anyLong())).thenReturn(Optional.of(Data.TASK));
        Task task = taskService.getTaskById(1L);
        assertAll(()->{
            assertNotNull(task);
            assertEquals(1L, task.getId());
            assertFalse(task.isComplete());
        });
        verify(taskRepository).findById(anyLong());
    }

    /**
     * Test for retrieving a task by its ID.
     * <p>
     * This test checks that the correct task is returned when a valid ID is provided.
     * </p>
     */
    @Test
    void shouldThrowElementNotFoundExceptionWhenIdDoesNotExists() {
        doThrow(ElementNotFoundException.class).when(taskRepository).findById(1000L);
        assertThrows(ElementNotFoundException.class, ()->{
            taskService.getTaskById(1000L);
        });
        verify(taskRepository).findById(anyLong());
    }

    /**
     * Test for saving a task.
     * <p>
     * This test checks that the saved task is returned correctly.
     * </p>
     */
    @Test
    void shouldRetrieveTaskWhenSave() {
        when(taskRepository.save(any(Task.class))).thenReturn(Data.TASK);
        Task task = taskService.saveTask(Data.TASK);
        assertAll(()->{
            assertNotNull(task);
            assertEquals(1L, task.getId());
            assertFalse(task.isComplete());
        });
        verify(taskRepository).save(any(Task.class));
    }

    /**
     * Test for deleting a task.
     * <p>
     * This test verifies that the deletion method returns true when the task exists.
     * </p>
     */
    @Test
    void shouldRetrieveTrueWhenDeleteTask() {
        when(taskRepository.existsById(anyLong())).thenReturn(true);

        boolean result = taskService.deleteTaskById(1L);
        assertTrue(result);
        verify(taskRepository).existsById(anyLong());
    }

    /**
     * Test for updating a task.
     * <p>
     * This test checks that the updated task is returned correctly after modification.
     * </p>
     */
    @Test
    void shouldRetrieveTaskWhenUpdateTask() {
        when(taskRepository.save(any(Task.class))).thenReturn(Data.TASK);
        when(taskRepository.findById(anyLong())).thenReturn(Optional.of(Data.TASK));
        Task task = taskService.updateTask(1L, Data.TASK);
        assertAll(()->{
            assertNotNull(task);
            assertEquals(1L, task.getId());
            assertFalse(task.isComplete());
        });
        verify(taskRepository).save(any(Task.class));
        verify(taskRepository).findById(anyLong());
    }

    /**
     * Test for updating the completion status of a task.
     * <p>
     * This test checks that the task's completion status is updated correctly.
     * </p>
     */
    @Test
    void shouldRetrieveTaskWhenUpdateCompleteTask() {
        when(taskRepository.save(any(Task.class))).thenReturn(Data.TASK);
        when(taskRepository.findById(anyLong())).thenReturn(Optional.of(Data.TASK));
        Task task = taskService.updateTaskComplete(1L, true);
        assertAll(()->{
            assertNotNull(task);
            assertEquals(1L, task.getId());
            assertTrue(task.isComplete());
        });
        verify(taskRepository).save(any(Task.class));
        verify(taskRepository).findById(anyLong());
    }
}