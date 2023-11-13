package com.kinto2517.nevitechinternshipcase.unit.service;

import com.kinto2517.nevitechinternshipcase.dto.UserDTO;
import com.kinto2517.nevitechinternshipcase.dto.UserSaveRequest;
import com.kinto2517.nevitechinternshipcase.entity.User;
import com.kinto2517.nevitechinternshipcase.mapper.UserMapper;
import com.kinto2517.nevitechinternshipcase.repository.UserRepository;
import com.kinto2517.nevitechinternshipcase.service.UserService;
import com.kinto2517.nevitechinternshipcase.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllUsers_shouldReturnUserDTOList() {
        List<User> users = Arrays.asList(
                new User(1L, "John Doe", "john.doe", "password123", null),
                new User(2L, "Jane Doe", "jane.doe", "pass456", null)
        );
        when(userRepository.findAll()).thenReturn(users);

        List<UserDTO> userDTOs = userService.getAllUsers();

        assertEquals(2, userDTOs.size());
        assertEquals("John Doe", userDTOs.get(0).name());
        assertEquals("Jane Doe", userDTOs.get(1).name());
    }

    @Test
    void saveUser_shouldReturnSavedUserDTO() {
        UserSaveRequest userSaveRequest = new UserSaveRequest("John Doe", "john.doe", "password123");
        User userToSave = UserMapper.INSTANCE.userSaveRequestToUser(userSaveRequest);
        when(userRepository.save(any(User.class))).thenReturn(userToSave);

        UserDTO savedUserDTO = userService.saveUser(userSaveRequest);

        assertEquals("John Doe", savedUserDTO.name());
        assertEquals("john.doe", savedUserDTO.username());
    }
}
