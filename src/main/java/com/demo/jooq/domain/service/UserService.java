package com.demo.jooq.domain.service;

import com.demo.jooq.domain.models.UserReq;
import com.demo.jooq.domain.models.UserRes;
import com.demo.jooq.domain.repositories.UserDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserDAO userDAO;

    public List<UserRes> getAll() {
        return userDAO.findAllUsers();
    }

    public UserRes getUserById(Long id) {
        return userDAO.findById(id);
    }

    public UserRes createUser(UserReq user) {
        return userDAO.save(user);
    }

    public Optional<UserRes> updateUser(Long id, UserReq user) {
        if (!userDAO.existsById(id)) {
            return Optional.empty();
        }
        user.setId(id);
        return Optional.of(userDAO.save(user));
    }

    public boolean deleteUser(Long id) {
        if (!userDAO.existsById(id)) {
            return false;
        }
        userDAO.deleteById(id);
        return true;
    }

}
