package com.problem1.myproject.service;

import com.problem1.myproject.exceptions.ObjectNotFoundException;
import com.problem1.myproject.model.Coin;
import com.problem1.myproject.model.MyUser;
import com.problem1.myproject.repository.UserRepository;
import com.problem1.myproject.service.implementation.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements IUserService {

    private UserRepository userRepo;

    @Autowired
    private  BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    @Transactional

    public List<MyUser> findAll() {
        return this.userRepo.findAll();
    }

    @Override
    @Transactional

    public MyUser findById(long theId)
    {
        Optional<MyUser> result = this.userRepo.findById(theId);
        MyUser theMyUser = new MyUser();
        if(result.isPresent()) {
            theMyUser = result.get();

        }else{
            throw new ObjectNotFoundException("The User with id " + theId + " was not found.\n");
        }

        return theMyUser;
    }

    @Override
    @Transactional
    public void save(MyUser theMyUser) {
        theMyUser.setPassword(passwordEncoder.encode(theMyUser.getPassword()));
        this.userRepo.save(theMyUser);
    }

    @Override
    @Transactional
    public void deleteById(long theId) {
       //findById(theId);
        this.userRepo.deleteById(theId);
    }

    @Override
    public List<Coin> getPortofolio(long userId){
        MyUser theMyUser = this.findById(userId);
        return theMyUser.getPortofolio();
    }

    @Override
    @Transactional
    public List<Coin> buyCoin(long userId,Coin theCoin){
        MyUser theMyUser = this.findById(userId);                   ///geting the user with id userId
        List<Coin> portofolio = theMyUser.getPortofolio();        ///adding to the portofolio list the new coin
        portofolio.add(theCoin);
        return portofolio;// sau theUser.getPortofolio();       /// returning the portofolio
    }
}
