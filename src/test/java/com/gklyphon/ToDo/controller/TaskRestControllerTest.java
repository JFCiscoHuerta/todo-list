package com.gklyphon.ToDo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.gklyphon.ToDo.Data;
import com.gklyphon.ToDo.exception.custom.ElementNotFoundException;
import com.gklyphon.ToDo.model.entity.Task;
import com.gklyphon.ToDo.service.TaskServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Integration tests for the {@link TaskRestController} class.
 * <p>
 * This class tests the RESTful API endpoints for task management, including retrieval,
 * creation, updating, and deletion of tasks. It uses MockMvc to perform HTTP requests
 * and Mockito to mock the service layer.
 * </p>
 *
 * @author JFCiscoHuerta
 * @version 1.0
 * @since 28-Oct-2024
 */
@SpringBootTest
@AutoConfigureMockMvc
class TaskRestControllerTest {

    @MockBean
    TaskServiceImpl taskService;

    @Autowired
    MockMvc mockMvc;

    String API_URL = "/v1/tasks";
    ObjectMapper objectMapper;

    /**
     * Sets up the ObjectMapper before each test.
     * <p>
     * This method initializes the ObjectMapper and registers the JavaTimeModule for
     * handling Java 8 date and time types.
     * </p>
     */
    @BeforeEach
    void setUp() {
        this.objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    /**
     * Sets up the ObjectMapper before each test.
     * <p>
     * This method initializes the ObjectMapper and registers the JavaTimeModule for
     * handling Java 8 date and time types.
     * </p>
     */
    @Test
    void shouldReturnAllTasks() throws Exception {
        when(taskService.getAllTasks()).thenReturn(Data.TASKS);
        mockMvc.perform(
                MockMvcRequestBuilders.get(API_URL)
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1));
    }

    /**
     * Test for retrieving tasks when no tasks exist.
     * <p>
     * This test checks that the API returns a No Content (204) status when there are
     * no tasks available.
     * </p>
     */
    @Test
    void shouldReturnNoContentWhenAllTasksCalled() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get(API_URL)
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isNoContent());
    }

    /**
     * Test for retrieving a task by its ID.
     * <p>
     * This test verifies that the API returns the correct task when a valid ID is provided
     * with status OK (200).
     * </p>
     */
    @Test
    void shouldReturnTaskById() throws Exception {
        when(taskService.getTaskById(anyLong())).thenReturn(Data.TASK);
        mockMvc.perform(
                        MockMvcRequestBuilders.get(API_URL + "/1")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    /**
     * Test for retrieving a task by a non-existent ID.
     * <p>
     * This test checks that the API returns Not Found (404) status when the task ID does not exist.
     * </p>
     */
    @Test
    void shouldReturnNoContentWhenTaskByIdCalled() throws Exception {
        doThrow(ElementNotFoundException.class).when(taskService).getTaskById(1000L);
        mockMvc.perform(
                        MockMvcRequestBuilders.get(API_URL + "/1000")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isNotFound());
    }

    /**
     * Test for retrieving a task by a non-existent ID.
     * <p>
     * This test checks that the API returns Not Found (404) status when the task ID does not exist.
     * </p>
     */
    @Test
    void shouldReturnTaskWhenCreateTaskCalled() throws Exception {
        when(taskService.saveTask(any(Task.class))).thenReturn(Data.TASK);
        mockMvc.perform(
                MockMvcRequestBuilders.post(API_URL + "/create-task")
                        .content(objectMapper.writeValueAsString(Data.TASK))
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
        )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Buy Milk"))
                .andExpect(jsonPath("$.complete").value(false));
    }

    /**
     * Test for deleting a task.
     * <p>
     * This test verifies that the API returns No Content (204) status when a task is successfully deleted.
     * </p>
     */
    @Test
    void shouldDeleteTaskWhenDeleteTaskCalled() throws Exception {
        doReturn(true).when(taskService).deleteTaskById(anyLong());
        mockMvc.perform(
                MockMvcRequestBuilders.delete(API_URL + "/delete-task/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
        )
                .andExpect(status().isNoContent());
    }

    /**
     * Test for deleting a task that does not exist.
     * <p>
     * This test verifies that the API returns Not Found (404) status when trying to delete a non-existent task.
     * </p>
     */
    @Test
    void shouldReturnNotFoundWhenDeleteTaskCalled() throws Exception {
        doReturn(false).when(taskService).deleteTaskById(anyLong());
        mockMvc.perform(
                        MockMvcRequestBuilders.delete(API_URL + "/delete-task/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .with(csrf())
                )
                .andExpect(status().isNotFound());
    }

    /**
     * Test for updating a task.
     * <p>
     * This test verifies that the API returns the updated task with status OK (200)
     * and checks the properties of the updated task.
     * </p>
     */
    @Test
    void shouldReturnTaskWhenUpdateTaskCalled() throws Exception {
        when(taskService.updateTask(anyLong(), any(Task.class))).thenReturn(Data.TASK);
        mockMvc.perform(
                        MockMvcRequestBuilders.put(API_URL + "/update-task/1")
                                .content(objectMapper.writeValueAsString(Data.TASK))
                                .contentType(MediaType.APPLICATION_JSON)
                                .with(csrf())
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Buy Milk"))
                .andExpect(jsonPath("$.complete").value(false));
    }

    /**
     * Test for updating the completion status of a task.
     * <p>
     * This test verifies that the API returns the task with updated completion status and status OK (200).
     * </p>
     */
    @Test
    void shouldReturnTaskWhenUpdateCompleteTaskCalled() throws Exception {
        when(taskService.updateTaskComplete(anyLong(), anyBoolean())).thenReturn(Data.TASK2);
        mockMvc.perform(
                        MockMvcRequestBuilders.put(API_URL + "/update-complete-task/1")
                                .param("complete","true")
                                .contentType(MediaType.APPLICATION_JSON)
                                .with(csrf())
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(2))
                .andExpect(jsonPath("$.name").value("Buy Milk"))
                .andExpect(jsonPath("$.complete").value(true));
    }

}