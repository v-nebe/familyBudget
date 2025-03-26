package org.shavneva.familybudget.controller.impl;

import jakarta.validation.Valid;
import org.shavneva.familybudget.mapper.IMapper;
import org.shavneva.familybudget.mapper.impl.*;
import org.shavneva.familybudget.dto.*;
import org.shavneva.familybudget.entity.*;
import org.shavneva.familybudget.service.ICrudService;
import org.shavneva.familybudget.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController extends BaseController<User, UserDTO> {

    private final UserService userService;
    private final UserMapper userMapper;

    public UserController(ICrudService<User> service, IMapper<User, UserDTO> mapper, UserService userService, UserMapper userMapper) {
        super(service, mapper);
        this.userService = userService;
        this.userMapper = userMapper;
    }


    @Override
    public UserDTO create(UserDTO userDto) {
        if (userDto.getRole() == null || userDto.getRole().isEmpty()) {
            userDto.setRole("ROLE_USER");
        }
        return super.create(userDto);
    }

    @Override
    @PreAuthorize("isAuthenticated()")
    @PostFilter("hasRole('ROLE_ADMIN') or filterObject.nickname == authentication.principal.username")
    public List<UserDTO> read() {
        return super.read();
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

    @GetMapping("/get/user")
    @PreAuthorize("authentication.principal.iduser == #id or hasRole('ROLE_ADMIN')")
    public ResponseEntity<UserDTO> getUser(@RequestParam Map<String, String> filters) {
        User user = userService.getUser(filters);
        return ResponseEntity.ok(userMapper.mapToDTO(user));
    }
    
}