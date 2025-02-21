package org.shavneva.familybudget.repository;

import org.shavneva.familybudget.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByNickname(String nickname);

    boolean  existsUserByNicknameAndPassword(String nickname, String password);


}