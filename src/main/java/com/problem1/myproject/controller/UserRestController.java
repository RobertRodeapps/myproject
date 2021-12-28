package com.problem1.myproject.controller;

import com.problem1.myproject.exceptions.ObjectNotFoundException;
import com.problem1.myproject.model.Coin;
import com.problem1.myproject.model.MyUser;
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
    public List<MyUser> getAll(){
        return this.userService.findAll();
    }

    @GetMapping("/users/{userId}")
    @Transactional
    public MyUser getUser(@PathVariable long userId){
        MyUser theMyUser = this.userService.findById(userId);
        if (theMyUser == null){
            throw new ObjectNotFoundException("The User with id " + userId + " was not found.\n");
        }
        return theMyUser;
    }

    @PostMapping("/users")
    @Transactional
    public MyUser addUser(@RequestBody UserDTO theUserDTO){

        MyUser newMyUser = new MyUser();

        ///Transforming a User dto into a Coin Object
        newMyUser.setId(0);
        newMyUser.setBalance(theUserDTO.getBalance());
        newMyUser.setEmail(theUserDTO.getEmail());
        newMyUser.setName(theUserDTO.getName());
        newMyUser.setRole(theUserDTO.getRole());
        newMyUser.setPassword(theUserDTO.getPassword());
        newMyUser.setDateOfBirth(theUserDTO.getDateOfBirth());
        newMyUser.setPortofolio(theUserDTO.getPortofolio());
        this.userService.save(newMyUser);
        return newMyUser;
    }

    @PutMapping("/users/{userId}")
    @Transactional
    public MyUser updateCoin(@RequestBody UserUpdateDTO theUserDTO, @PathVariable long userId){
        MyUser oldMyUser = this.userService.findById(userId);
        if (oldMyUser == null){
            throw new ObjectNotFoundException("The User with id " + userId + " was not found.\n");
        }
        oldMyUser.setBalance(theUserDTO.getBalance());
        oldMyUser.setEmail(theUserDTO.getEmail());
        oldMyUser.setName(theUserDTO.getName());
        oldMyUser.setRole(theUserDTO.getRole());
        oldMyUser.setDateOfBirth(theUserDTO.getDateOfBirth());
        oldMyUser.setPortofolio(theUserDTO.getPortofolio());

        return oldMyUser;
    }

    @DeleteMapping("/users/{userId}")
    @Transactional
    public MyUser deleteCoin(@PathVariable long userId){      ///TODO ASK IF ITS A PROBLEM IF I CANT FIND PORTOFOLIO adn i get an error
        MyUser deletedMyUser = this.userService.findById(userId);
        if (deletedMyUser == null){
            throw new ObjectNotFoundException("The User with id " + userId + " was not found.\n");
        }

        this.userService.deleteById(userId);
        return deletedMyUser;
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
