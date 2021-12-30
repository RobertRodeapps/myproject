package com.problem1.myproject.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
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

    @Column(name = "password")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @Column(name = "date_of_birth")
    @JsonProperty("date_of_birth")
    private Date dateOfBirth;
    @Column(name =  "balance")
    private Double balance;
    @ManyToMany(
            fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.DETACH,CascadeType.REFRESH}
    )
    @JoinTable(
            name=  "user_coin",
            joinColumns=@JoinColumn(name = "id_user"),
            inverseJoinColumns = @JoinColumn(name = "id_coin")
    )
    private List<Coin> portofolio;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private RolesEnum role;

}
