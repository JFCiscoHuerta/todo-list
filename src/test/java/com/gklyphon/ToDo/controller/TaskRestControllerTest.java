package com.gklyphon.ToDo.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.gklyphon.ToDo.Data;
import com.gklyphon.ToDo.exception.custom.ElementNotFoundException;
import com.gklyphon.ToDo.model.entity.Task;
import com.gklyphon.ToDo.service.TaskServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class TaskRestControllerTest {

    @MockBean
    TaskServiceImpl taskService;

    @Autowired
    MockMvc mockMvc;

    String API_URL = "/v1/tasks";
    ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        this.objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

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

    @Test
    void shouldReturnNoContentWhenAllTasksCalled() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get(API_URL)
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isNoContent());
    }

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

    @Test
    void shouldReturnNoContentWhenTaskByIdCalled() throws Exception {
        doThrow(ElementNotFoundException.class).when(taskService).getTaskById(1000L);
        mockMvc.perform(
                        MockMvcRequestBuilders.get(API_URL + "/1000")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isNotFound());
    }

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