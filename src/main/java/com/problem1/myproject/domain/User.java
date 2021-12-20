package com.problem1.myproject.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

//@Entity
@Data
@Entity
@Table(name = "user")
public class User {
    enum Roles {
        ADMIN,
        USER,
        GUEST
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    @Column(name = "name")
    private String name;
    @Column(name = "email")
    private String email;
    @Column(name = "date_of_birth")
    private Date dateOfBirth;
    @Column(name =  "balance")
    private Double balance;
    @ManyToMany
    private List<Coin> portofolio;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private RolesEnum role;

}
