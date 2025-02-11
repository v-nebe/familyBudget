package org.shavneva.familybudget.mapper.impl;

import org.shavneva.familybudget.dto.UserDTO;
import org.shavneva.familybudget.entity.User;
import org.shavneva.familybudget.mapper.IMapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class UserMapper implements IMapper<User, UserDTO> {

    @Override
    public UserDTO mapToDTO(User entity) {
        if(entity == null){
            return null;
        }
        UserDTO userDTO = new UserDTO();
        userDTO.setIdUser(entity.getIdUser());
        userDTO.setNickname(entity.getNickname());
        userDTO.setPassword(entity.getPassword());
        userDTO.setRole(entity.getRole());

        return  userDTO;
    }

    @Override
    public User mapToEntity(UserDTO userDTO) {
        if(userDTO == null){
            return null;
        }
        User user = new User();
        user.setIdUser(userDTO.getIdUser());
        user.setNickname(userDTO.getNickname());
        user.setPassword(userDTO.getPassword());
        user.setRole(userDTO.getRole());

        return user;

    }
}
