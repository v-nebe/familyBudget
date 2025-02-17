package org.shavneva.familybudget.service.impl;

import org.shavneva.familybudget.entity.User;
import org.shavneva.familybudget.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService extends BaseService<User, Integer> {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        super(userRepository);
        this.userRepository = userRepository;
    }

    @Override
    public User create(User user) {
        User savedUser = userRepository.save(user);
        return savedUser;
    }

    @Override
    public User update(User updatedEntity) {
        User existingEntity = userRepository.findById(updatedEntity.getIduser()).orElseThrow(
                () -> new IllegalArgumentException("User not found with id: " + updatedEntity.getIduser())
        );

        if (updatedEntity.getNickname() != null) {
            existingEntity.setNickname(updatedEntity.getNickname());
        }
        if (updatedEntity.getPassword() != null) {
            existingEntity.setPassword(updatedEntity.getPassword());
        }
        if (updatedEntity.getRole() != null && !updatedEntity.getRole().isEmpty()) {
            existingEntity.setRole(updatedEntity.getRole());
        }

        return userRepository.save(existingEntity);
    }

}
