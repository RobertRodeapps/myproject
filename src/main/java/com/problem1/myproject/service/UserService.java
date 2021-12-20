package com.problem1.myproject.service;

import com.problem1.myproject.model.User;
import com.problem1.myproject.repository.UserRepositoryJPA;
import com.problem1.myproject.service.implementation.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements IUserService {

    private UserRepositoryJPA userRepo;

    @Autowired
    public UserService(UserRepositoryJPA userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public List<User> findAll() {
        return null;
    }

    @Override
    public User findById(long theId) {
        return null;
    }

    @Override
    public void save(User theUser) {

    }

    @Override
    public void deleteById(long theId) {

    }
}
