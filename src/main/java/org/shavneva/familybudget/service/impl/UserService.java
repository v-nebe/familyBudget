package org.shavneva.familybudget.service.impl;

import jakarta.persistence.EntityNotFoundException;
import org.shavneva.familybudget.entity.User;
import org.shavneva.familybudget.exception.ResourceNotFoundException;
import org.shavneva.familybudget.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

@Service
public class UserService extends BaseService<User, Integer> {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        super(userRepository, "User");
        this.userRepository = userRepository;
    }

    @Override
    public User create(User user) {
        return super.create(user);
    }

    @Override
    public User update(User updatedUser) {
        User existingUser = userRepository.findById(updatedUser.getIduser())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + updatedUser.getId()));

        if (updatedUser.getNickname() != null && !updatedUser.getNickname().isEmpty()) {
            existingUser.setNickname(updatedUser.getNickname());
        }
        if (updatedUser.getPassword() != null && !updatedUser.getPassword().isEmpty()) {
            existingUser.setPassword(updatedUser.getPassword());
        }
        if (updatedUser.getRole() != null && !updatedUser.getRole().isEmpty()) {
            existingUser.setRole(updatedUser.getRole());
        }

        return userRepository.save(existingUser);
    }

    public User updateByNickname(String oldNickname, User updatedUser) {
        User existingUser = userRepository.findByNickname(oldNickname)
                .orElseThrow(() -> new EntityNotFoundException("User not found with nickname: " +
                        updatedUser.getNickname()));

        if (updatedUser.getNickname() != null && !updatedUser.getNickname().isEmpty()) {
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
        super.delete(id);
    }


   public User getUser(Map<String, String> filters){
        if(filters.isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Не указан ни один фильтр");
        }
        if(filters.containsKey("id")){
            return userRepository.findById(Integer.parseInt(filters.get("id")))
                    .orElseThrow(() -> new ResourceNotFoundException("Пользователь не найден"));
        }
        if (filters.containsKey("nickname")){
            return userRepository.findByNickname(String.valueOf(filters.get("nickname")))
                    .orElseThrow(() -> new ResourceNotFoundException("Пользователь не найден"));
        }
        throw  new ResponseStatusException(HttpStatus.BAD_REQUEST, "Некорректные фильтры");
   }

   public boolean authenticated(String nickname, String password){
       return nickname != null && password != null &&
               userRepository.existsUserByNicknameAndPassword(nickname, password);
   }

}
