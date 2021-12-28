package com.problem1.myproject.model.dto;

import lombok.Data;

import javax.persistence.Column;

@Data
public class CoinDTO {
    private String coinName;
    private Double supply;
    private Double price;
}
