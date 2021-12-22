package com.problem1.myproject.service;

import com.problem1.myproject.exceptions.ObjectNotFoundException;
import com.problem1.myproject.model.Coin;
import com.problem1.myproject.model.User;
import com.problem1.myproject.repository.UserRepositoryJPA;
import com.problem1.myproject.service.implementation.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements IUserService {

    private UserRepositoryJPA userRepo;

    @Autowired
    public UserService(UserRepositoryJPA userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    @Transactional

    public List<User> findAll() {
        return this.userRepo.findAll();
    }

    @Override
    @Transactional

    public User findById(long theId)
    {
        Optional<User> result = this.userRepo.findById(theId);
        User theUser = new User();
        if(result.isPresent()) {
            theUser = result.get();

        }else{
            throw new ObjectNotFoundException("The User with id " + theId + " was not found.\n");
        }

        return theUser;
    }

    @Override
    @Transactional
    public void save(User theUser) {
        this.userRepo.save(theUser);
    }

    @Override
    @Transactional

    public void deleteById(long theId) {

        this.userRepo.deleteById(theId);
    }

    @Override
    public List<Coin> getPortofolio(long userId){
        User theUser = this.findById(userId);
        return theUser.getPortofolio();
    }

    @Override
    @Transactional
    public List<Coin> buyCoin(long userId,Coin theCoin){
        User theUser = this.findById(userId);                   ///geting the user with id userId
        List<Coin> portofolio = theUser.getPortofolio();        ///adding to the portofolio list the new coin
        portofolio.add(theCoin);
        return portofolio;// sau theUser.getPortofolio();       /// returning the portofolio
    }
}
