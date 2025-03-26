package org.shavneva.familybudget.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.shavneva.familybudget.dto.UserDTO;
import org.shavneva.familybudget.entity.User;
import org.shavneva.familybudget.exception.ResourceNotFoundException;
import org.shavneva.familybudget.mapper.impl.UserMapper;
import org.shavneva.familybudget.repository.UserRepository;
import org.shavneva.familybudget.service.ICrudService;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

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

    public User getById(int id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
    }

    @Override
    public User update(User updatedUser) {
        User existingUser = getById(updatedUser.getIduser());

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
        userRepository.deleteById(id);
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

}
