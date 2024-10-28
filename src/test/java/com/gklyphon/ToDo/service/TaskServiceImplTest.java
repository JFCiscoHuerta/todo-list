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

@ExtendWith(MockitoExtension.class)
class TaskServiceImplTest {

    @Mock
    ITaskRepository taskRepository;

    @InjectMocks
    TaskServiceImpl taskService;

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

    @Test
    void shouldThrowElementNotFoundExceptionWhenIdDoesNotExists() {
        doThrow(ElementNotFoundException.class).when(taskRepository).findById(1000L);
        assertThrows(ElementNotFoundException.class, ()->{
            taskService.getTaskById(1000L);
        });
        verify(taskRepository).findById(anyLong());
    }

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

    @Test
    void shouldRetrieveTrueWhenDeleteTask() {
        when(taskRepository.existsById(anyLong())).thenReturn(true);

        boolean result = taskService.deleteTaskById(1L);
        assertTrue(result);
        verify(taskRepository).existsById(anyLong());
    }

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