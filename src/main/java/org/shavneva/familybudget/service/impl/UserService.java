package org.shavneva.familybudget.service.impl;

import lombok.RequiredArgsConstructor;
import org.shavneva.familybudget.entity.User;
import org.shavneva.familybudget.exception.ResourceNotFoundException;
import org.shavneva.familybudget.repository.UserRepository;
import org.shavneva.familybudget.service.ICrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService implements ICrudService<User> {

    private final UserRepository userRepository;

    @Override
    public User create(User user) {
        return userRepository.save(user);
    }

    @Override
    public List<User> read() {
        return userRepository.findAll();
    }

    @Override
    public User getById(int id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
    }

    @Override
    public User update(User updatedUser) {
        User existingUser = getById(updatedUser.getIduser());

        if (updatedUser.getNickname() != null) {
            existingUser.setNickname(updatedUser.getNickname());
        }
        if (updatedUser.getPassword() != null) {
            existingUser.setPassword(updatedUser.getPassword());
        }
        if (updatedUser.getRole() != null && !updatedUser.getRole().isEmpty()) {
            existingUser.setRole(updatedUser.getRole());
        }

        return userRepository.save(existingUser);
    }

    @Override
    public void delete(int id) {
        userRepository.deleteById(id);
    }
}
