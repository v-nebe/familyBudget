package org.shavneva.familybudget.controller.impl;

import org.shavneva.familybudget.mapper.impl.*;
import org.shavneva.familybudget.dto.*;
import org.shavneva.familybudget.entity.*;
import org.shavneva.familybudget.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController extends BaseController<User, UserDTO> {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService, UserMapper userMapper, UserService userService1){
        super(userService, userMapper);
        this.userService = userService1;
    }

    @Override
    public UserDTO create(UserDTO userDto) {
        return super.create(userDto);
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    @PostFilter("hasRole('ROLE_ADMIN') or filterObject.nickname == authentication.name")
    public List<UserDTO> read() {
        return super.read();
    }

    @Override
    @PreAuthorize("principal.userId == #id or hasRole('ROLE_ADMIN')")
    public UserDTO getById(int id) {
        return super.getById(id);
    }

    @Override
    @PreAuthorize("#newDTO.nickname == authentication.principal.nickname or hasRole('ROLE_ADMIN')")
    public UserDTO update(UserDTO newDTO) {
        return super.update(newDTO);
    }

    @Override
    @PreAuthorize("principal.userId == #id or hasRole('ROLE_ADMIN')")
    public void delete(int id) {
        super.delete(id);
    }
}
