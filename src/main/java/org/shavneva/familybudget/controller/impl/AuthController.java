package org.shavneva.familybudget.controller.impl;

import jakarta.validation.Valid;
import org.shavneva.familybudget.entity.User;
import org.shavneva.familybudget.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping ("/auth")
public class AuthController {

    private final UserRepository userRep;

    @Autowired
    public AuthController(UserRepository userRep) {
        this.userRep = userRep;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody User user){

        if(user.getNickname() != null && user.getPassword() != null &&
                userRep.existsUserByNicknameAndPassword(user.getNickname(), user.getPassword())){
            return ResponseEntity.ok("Login successful");
        }
        return ResponseEntity.status(401).body("Unauthorized");
    }
}
