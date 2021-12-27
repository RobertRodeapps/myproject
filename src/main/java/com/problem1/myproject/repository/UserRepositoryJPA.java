package com.problem1.myproject.repository;

import com.problem1.myproject.model.MyUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;


public interface UserRepositoryJPA extends JpaRepository<MyUser,Long> {
    MyUser findByEmail(String email);

}
