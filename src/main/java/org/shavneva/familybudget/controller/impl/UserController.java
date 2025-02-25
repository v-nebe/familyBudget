package org.shavneva.familybudget.controller.impl;

import org.shavneva.familybudget.mapper.impl.*;
import org.shavneva.familybudget.dto.*;
import org.shavneva.familybudget.entity.*;
import org.shavneva.familybudget.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "http://localhost:5173")
public class UserController extends BaseController<User, UserDTO> {

    @Autowired
    public UserController(UserService userService, UserMapper userMapper) {
        super(userService, userMapper);
    }

    @Override
    public UserDTO create(UserDTO userDto) {
        if (userDto.getRole() == null || userDto.getRole().isEmpty()) {
            userDto.setRole("ROLE_USER");
        }
        return super.create(userDto);
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    @PostFilter("hasRole('ROLE_ADMIN') or filterObject.nickname == authentication.principal.username")
    public List<UserDTO> read() {
        return super.read();
    }

    @Override
    @PreAuthorize("authentication.principal.iduser == #id or hasRole('ROLE_ADMIN')")
    public UserDTO getById(int id) {
        return super.getById(id);
    }

    @Override
    @PreAuthorize("authentication.principal.username == #newDTO.nickname or hasRole('ROLE_ADMIN')")
    public UserDTO update(UserDTO newDTO) {
        return super.update(newDTO);
    }

    @Override
    @PreAuthorize("authentication.principal.iduser == #id or hasRole('ROLE_ADMIN')")
    public void delete(int id) {
        super.delete(id);
    }
}