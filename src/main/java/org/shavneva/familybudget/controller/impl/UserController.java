package org.shavneva.familybudget.controller.impl;

import jakarta.validation.Valid;
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
    public UserController(UserService userService, UserMapper userMapper, UserService userService1) {
        super(userService, userMapper);
        this.userService = userService1;
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

    @GetMapping("/get/{id}")
    @PreAuthorize("authentication.principal.iduser == #id or hasRole('ROLE_ADMIN')")
    public UserDTO getById(@Valid @PathVariable int id) {
        return userService.getById(id);
    }

    @PutMapping("/update/{oldNickname}")
    @PreAuthorize("authentication.principal.username == #oldNickname or hasRole('ROLE_ADMIN')")
    public ResponseEntity<UserDTO> update(@PathVariable String oldNickname, @RequestBody UserDTO updatedUserDTO) {
        UserMapper userMapper = new UserMapper();
        User updatedUser = userService.updateByNickname(oldNickname, userMapper.mapToEntity(updatedUserDTO));
        return ResponseEntity.ok(userMapper.mapToDTO(updatedUser));
    }

    @Override
    @PreAuthorize("authentication.principal.iduser == #id or hasRole('ROLE_ADMIN')")
    public void delete(int id) {
        super.delete(id);
    }


    @GetMapping("/nickname/{nickname}")
    @PreAuthorize("authentication.principal.username == #nickname or hasRole('ROLE_ADMIN')")
    public ResponseEntity<UserDTO> getByNickname(@PathVariable String nickname) {
        UserDTO userDTO = userService.getByNickname(nickname);
        return  ResponseEntity.ok(userDTO);
    }
}