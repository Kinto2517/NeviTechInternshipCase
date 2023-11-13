package com.kinto2517.nevitechinternshipcase.mapper;

import com.kinto2517.nevitechinternshipcase.dto.UserDTO;
import com.kinto2517.nevitechinternshipcase.dto.UserSaveRequest;
import com.kinto2517.nevitechinternshipcase.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
    UserDTO userToUserDTO(User user);
    User userSaveRequestToUser(UserSaveRequest userSaveRequest);
    List<UserDTO> usersToUserDTOs(List<User> users);

}
