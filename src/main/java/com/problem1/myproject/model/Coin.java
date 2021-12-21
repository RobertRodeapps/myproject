package com.problem1.myproject.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "coin")
@JsonIgnoreProperties(value= {"holders"})
public class Coin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    @Column(name = "coin_name")
    private String coinName;
    @Column(name = "supply")
    private Double supply;
    @Column(name = "price")
    private Double price;
     @ManyToMany(        ///TODO ASK IF I HAVE TO DO THIS HOLDERS LIST
            fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.DETACH,CascadeType.REFRESH}
    )
    @JoinTable(
            name=  "user_coin",
            joinColumns=@JoinColumn(name = "id_coin"),
            inverseJoinColumns = @JoinColumn(name = "id_user")
    )
    private List<User> holders;
}
