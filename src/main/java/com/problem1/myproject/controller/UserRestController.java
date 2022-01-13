package com.problem1.myproject.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.problem1.myproject.exceptions.ObjectNotFoundException;
import com.problem1.myproject.model.Coin;
import com.problem1.myproject.model.RolesEnum;
import com.problem1.myproject.model.User;
import com.problem1.myproject.model.dto.UserDTO;
import com.problem1.myproject.model.dto.UserUpdateDTO;
import com.problem1.myproject.service.implementation.UserService;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

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
    public UserUpdateDTO updateUser(@RequestBody UserUpdateDTO theUserDTO, @PathVariable long userId){
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

        return theUserDTO;
    }

    @DeleteMapping("/users/{userId}")
    @Transactional
    public UserUpdateDTO deleteUser(@PathVariable long userId){      ///TODO ASK IF ITS A PROBLEM IF I CANT FIND PORTOFOLIO adn i get an error
        User deletedUser = this.userService.findById(userId);

        if (deletedUser == null){
            throw new ObjectNotFoundException("The User with id " + userId + " was not found.\n");
        }

        this.userService.deleteById(userId);
        deletedUser.getPortofolio();
        UserUpdateDTO oldUser = new UserUpdateDTO();
        oldUser.setBalance(deletedUser.getBalance());
        oldUser.setEmail(deletedUser.getEmail());
        oldUser.setName(deletedUser.getName());
        oldUser.setRole(deletedUser.getRole());
        oldUser.setDateOfBirth(deletedUser.getDateOfBirth());
        oldUser.setPortofolio(deletedUser.getPortofolio());
        Hibernate.initialize(oldUser.getPortofolio());
        return oldUser;
    }

    @GetMapping("/portofolio/{userId}")
    @Transactional
    public List<Coin> getPortofolio(@PathVariable long userId){
        return this.userService.getPortofolio(userId);
    }


    ///requires a json (Coin) and  user id in the path and will append to the portofolio of that user the required coin
    ///return the portofolio of that user
/*    @PutMapping("/buy/{userId}")  ///TODO MAYBE ADD QUANTITY LATER
    @Transactional
    public List<Coin> buyCoin(@PathVariable long userId, @RequestBody Coin theCoin){


        return  this.userService.buyCoin(userId,theCoin);


    }   ///TODO it doesnt work, i think its harder than i thought*/

    @GetMapping("/refreshtoken")
    public void refreshToken (HttpServletRequest request, HttpServletResponse response) throws IOException {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){
            try {
                String refresh_token = authorizationHeader.substring("Bearer ".length());
                Algorithm algorithm = Algorithm.HMAC256("${jwt.secret}".getBytes());
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(refresh_token);
                String username = decodedJWT.getSubject();
                User user = userService.getUserByEmail(username);
                 Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
                authorities.add(new SimpleGrantedAuthority(user.getRole().toString()));
                List<RolesEnum> roles  = new ArrayList<>();
                roles.add(user.getRole());

                String access_token = String.valueOf(JWT.create()
                        .withSubject(user.getEmail())
                        .withExpiresAt(new Date(System.currentTimeMillis() + 11 * 60 * 1000))
                        .withIssuer(request.getRequestURL().toString())
                        .withClaim("roles",roles.stream().map(RolesEnum::toString).collect(Collectors.toList()))
                        .sign(algorithm));


                Map<String,String> tokens = new HashMap<>();
                tokens.put("access_token",access_token);
                tokens.put("refresh_token",refresh_token);
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(),tokens);


            }catch (Exception exception){
                response.setHeader("Error",exception.getMessage());
                response.setStatus(FORBIDDEN.value());
                //response.sendError(FORBIDDEN.value());
                Map<String,String> error = new HashMap<>();

                error.put("error_message",exception.getMessage());
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(),error);

            }
        }else {
            throw new  RuntimeException("REFresh token is missing");
        }

     }

}
