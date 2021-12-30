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
    @ManyToMany(mappedBy = "portofolio")
    private List<User> holders;
}
