package com.problem1.myproject.service;

import com.problem1.myproject.model.Coin;

import java.util.List;

public interface ICoinService {

        public List<Coin> findAll();

        public Coin findById(long theId);

        public Coin save(Coin theCoin);

        public Coin deleteById(long theId);
}
