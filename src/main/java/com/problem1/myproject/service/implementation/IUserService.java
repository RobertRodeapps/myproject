package com.problem1.myproject.service.implementation;

import com.problem1.myproject.model.User;

import java.util.List;

public interface IUserService {

        public List<User> findAll();

        public User findById(long theId);

        public void save(User theUser);

        public void deleteById(long theId);
}
