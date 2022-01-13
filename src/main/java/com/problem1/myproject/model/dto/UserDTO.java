package com.problem1.myproject.model.dto;

import com.problem1.myproject.model.Coin;
import com.problem1.myproject.model.RolesEnum;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class UserDTO {

    private String name;
    private String email;
    private Date dateOfBirth;
    private Double balance;
    private String password;
    private RolesEnum role;
    private List<Coin> portofolio;

}