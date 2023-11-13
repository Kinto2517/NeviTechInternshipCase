package com.kinto2517.nevitechinternshipcase.controller;

import com.kinto2517.nevitechinternshipcase.dto.UserDTO;
import com.kinto2517.nevitechinternshipcase.dto.UserSaveRequest;
import com.kinto2517.nevitechinternshipcase.entity.User;
import com.kinto2517.nevitechinternshipcase.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        logger.info("getAllUsers() called");
        return ResponseEntity.ok().body(userService.getAllUsers());
    }

    @PostMapping("/save")
    public ResponseEntity<UserDTO> saveUser(UserSaveRequest userSaveRequest) {
        logger.info("saveUser() called");
        return ResponseEntity.ok().body(userService.saveUser(userSaveRequest));
    }


}
