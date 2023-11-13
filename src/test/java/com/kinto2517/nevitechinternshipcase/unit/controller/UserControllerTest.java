package com.kinto2517.nevitechinternshipcase.unit.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kinto2517.nevitechinternshipcase.dto.UserDTO;
import com.kinto2517.nevitechinternshipcase.dto.UserSaveRequest;
import com.kinto2517.nevitechinternshipcase.service.UserService;
import com.kinto2517.nevitechinternshipcase.controller.UserController;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private final MockMvc mockMvc;

    public UserControllerTest() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    void getAllUsers_shouldReturnAllUsers() throws Exception {
        when(userService.getAllUsers()).thenReturn(Arrays.asList(
                new UserDTO(1L, "John Doe", "john.doe", "password123"),
                new UserDTO(2L, "Jane Doe", "jane.doe", "pass456")
        ));

        mockMvc.perform(get("/api/v1/user/all"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].name").value("John Doe"))
                .andExpect(jsonPath("$[1].name").value("Jane Doe"));

        verify(userService, times(1)).getAllUsers();
    }

    @Test
    void saveUser_shouldReturnSavedUser() throws Exception {
        UserSaveRequest userSaveRequest = new UserSaveRequest("John Doe", "john.doe", "password123");
        when(userService.saveUser(any(UserSaveRequest.class))).thenReturn(new UserDTO(1L, "John Doe", "john.doe", "password123"));

        mockMvc.perform(post("/api/v1/user/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userSaveRequest)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("John Doe"))
                .andExpect(jsonPath("$.username").value("john.doe"));

        verify(userService, times(1)).saveUser(any(UserSaveRequest.class));
    }
}

