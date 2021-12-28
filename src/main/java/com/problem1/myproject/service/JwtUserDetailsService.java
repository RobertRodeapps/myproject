package com.problem1.myproject.service;


import java.util.ArrayList;

import com.problem1.myproject.model.dto.UserDTO;
import com.problem1.myproject.model.MyUser;
import com.problem1.myproject.repository.UserRepositoryJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsService implements UserDetailsService {


    @Autowired
    private UserRepositoryJPA userDao;




    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        MyUser user = userDao.findByEmail(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
                new ArrayList<>());

    }

    public PasswordEncoder passwordEncoder() {          ///TODO ASK IF THIS IS ALRIGHT HERE
        return new BCryptPasswordEncoder();
    }


    public UserRepositoryJPA save(UserDTO theUserDTO) {
        MyUser newMyUser = new MyUser();
        newMyUser.setId(0);
        newMyUser.setBalance(theUserDTO.getBalance());
        newMyUser.setEmail(theUserDTO.getEmail());
        newMyUser.setName(theUserDTO.getName());
        newMyUser.setRole(theUserDTO.getRole());
        newMyUser.setPassword(this.passwordEncoder().encode(theUserDTO.getPassword()));
        newMyUser.setDateOfBirth(theUserDTO.getDateOfBirth());
        newMyUser.setPortofolio(theUserDTO.getPortofolio());
        userDao.save(newMyUser);
        return userDao;
    }

}