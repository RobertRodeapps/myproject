package com.problem1.myproject.controller;

import com.problem1.myproject.exceptions.ObjectNotFoundException;
import com.problem1.myproject.model.Coin;
import com.problem1.myproject.model.User;
import com.problem1.myproject.model.dto.CoinDTO;
import com.problem1.myproject.model.dto.UserDTO;
import com.problem1.myproject.model.dto.UserUpdateDTO;
import com.problem1.myproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;

@RestController
@RequestMapping("/menu")
public class UserRestController {

    private UserService userService;

    @Autowired
    public UserRestController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping("/users")
    @Transactional
    public List<User> getAll(){
        return this.userService.findAll();
    }

    @GetMapping("/users/{userId}")
    @Transactional
    public User getUser(@PathVariable long userId){
        User theUser = this.userService.findById(userId);
        if (theUser == null){
            throw new ObjectNotFoundException("The User with id " + userId + " was not found.\n");
        }
        return theUser;
    }

    @PostMapping("/users")
    @Transactional
    public User addUser(@RequestBody UserDTO theUserDTO){

        User newUser = new User();

        ///Transforming a User dto into a Coin Object
        newUser.setId(0);
        newUser.setBalance(theUserDTO.getBalance());
        newUser.setEmail(theUserDTO.getEmail());
        newUser.setName(theUserDTO.getName());
        newUser.setRole(theUserDTO.getRole());
        newUser.setPassword(theUserDTO.getPassword());
        newUser.setDateOfBirth(theUserDTO.getDateOfBirth());
        newUser.setPortofolio(theUserDTO.getPortofolio());
        this.userService.save(newUser);
        return newUser;
    }

    @PutMapping("/users/{userId}")
    @Transactional
    public User updateCoin(@RequestBody UserUpdateDTO theUserDTO, @PathVariable long userId){
        User oldUser = this.userService.findById(userId);
        if (oldUser == null){
            throw new ObjectNotFoundException("The User with id " + userId + " was not found.\n");
        }
        oldUser.setBalance(theUserDTO.getBalance());
        oldUser.setEmail(theUserDTO.getEmail());
        oldUser.setName(theUserDTO.getName());
        oldUser.setRole(theUserDTO.getRole());
        oldUser.setDateOfBirth(theUserDTO.getDateOfBirth());
        oldUser.setPortofolio(theUserDTO.getPortofolio());

        return oldUser;
    }

    @DeleteMapping("/users/{userId}")
    @Transactional
    public User deleteCoin(@PathVariable long userId){      ///TODO ASK IF ITS A PROBLEM IF I CANT FIND PORTOFOLIO adn i get an error
        User deletedUser = this.userService.findById(userId);
        if (deletedUser == null){
            throw new ObjectNotFoundException("The User with id " + userId + " was not found.\n");
        }

        this.userService.deleteById(userId);
        return deletedUser;
    }

    @GetMapping("/portofolio/{userId}")
    @Transactional
    public List<Coin> getPortofolio(@PathVariable long userId){
        return this.userService.getPortofolio(userId);
    }


    ///requires a json (Coin) and  user id in the path and will append to the portofolio of that user the required coin
    ///return the portofolio of that user
    @PutMapping("/buy/{userId}")  ///TODO MAYBE ADD QUANTITY LATER
    @Transactional
    public List<Coin> buyCoin(@PathVariable long userId, @RequestBody Coin theCoin){


        return  this.userService.buyCoin(userId,theCoin);


    }   ///TODO it doesnt work, i think its harder than i thought
}
