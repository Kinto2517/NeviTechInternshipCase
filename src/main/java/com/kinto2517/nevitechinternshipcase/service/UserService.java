package com.kinto2517.nevitechinternshipcase.service;

import com.kinto2517.nevitechinternshipcase.dto.UserDTO;
import com.kinto2517.nevitechinternshipcase.dto.UserSaveRequest;
import org.springframework.stereotype.Service;

import java.util.List;

public interface UserService {

    List<UserDTO> getAllUsers();

    UserDTO saveUser(UserSaveRequest userSaveRequest);
}
