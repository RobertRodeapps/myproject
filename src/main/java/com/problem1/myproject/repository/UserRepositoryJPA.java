package com.problem1.myproject.repository;

import com.problem1.myproject.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepositoryJPA extends JpaRepository<User,Long> {
}
