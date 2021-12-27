package com.problem1.myproject.service.implementation;

import com.problem1.myproject.model.Coin;
import com.problem1.myproject.model.MyUser;

import java.util.List;

public interface IUserService {

        public List<MyUser> findAll();

        public MyUser findById(long theId);

        public void save(MyUser theMyUser);

        public void deleteById(long theId);

        ///it will return the coins bought by a user
        public List<Coin> getPortofolio(long userId);


        ///requires a json (Coin) and  user id in the path and will append to the portofolio of that user the required coin
        ///return the portofolio of that user
        public List<Coin> buyCoin(long userId,Coin theCoin);

        }