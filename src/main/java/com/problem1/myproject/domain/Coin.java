package com.problem1.myproject.domain;

import lombok.*;

import javax.persistence.*;

@Data
@Entity
@Table(name = "coin")
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

}
