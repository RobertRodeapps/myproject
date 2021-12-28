package com.problem1.myproject.service.implementation;

import com.problem1.myproject.model.Coin;

import java.util.List;

public interface ICoinService {

        public List<Coin> findAll();

        public Coin findById(long theId);

        public void save(Coin theCoin);

        public void deleteById(long theId);
}
