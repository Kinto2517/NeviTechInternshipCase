package com.kinto2517.nevitechinternshipcase.service.impl;

import com.kinto2517.nevitechinternshipcase.dto.UserDTO;
import com.kinto2517.nevitechinternshipcase.dto.UserSaveRequest;
import com.kinto2517.nevitechinternshipcase.entity.User;
import com.kinto2517.nevitechinternshipcase.mapper.UserMapper;
import com.kinto2517.nevitechinternshipcase.repository.UserRepository;
import com.kinto2517.nevitechinternshipcase.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        if (users.isEmpty()) {
            logger.error("No users found");
            throw new RuntimeException("No users found");
        }
        logger.info("Users found");
        return UserMapper.INSTANCE.usersToUserDTOs(users);
    }

    @Override
    public UserDTO saveUser(UserSaveRequest userSaveRequest) {
        User user = UserMapper.INSTANCE.userSaveRequestToUser(userSaveRequest);
        userRepository.save(user);

        logger.info("User saved");
        return UserMapper.INSTANCE.userToUserDTO(user);
    }
}
