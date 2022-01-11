package com.problem1.myproject.service.implementation;

import com.problem1.myproject.exceptions.ObjectNotFoundException;
import com.problem1.myproject.model.Coin;
import com.problem1.myproject.model.User;
import com.problem1.myproject.repository.UserRepository;
import com.problem1.myproject.service.IUserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service @Slf4j
@AllArgsConstructor
public class UserService implements IUserService, UserDetailsService {

    private UserRepository userRepo;
    private PasswordEncoder passwordEncoder;


    @Override
    public List<User> findAll() {
        return this.userRepo.findAll();
    }

    @Override
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
    public User save(User theUser) {
        theUser.setPassword(passwordEncoder.encode(theUser.getPassword()));
        this.userRepo.save(theUser);
        return theUser;
    }

    @Override
    public User deleteById(long theId) {
        User user = findById(theId);

        this.userRepo.deleteById(theId);
        return user;
    }

    @Override
    public List<Coin> getPortofolio(long userId){
        User theUser = this.findById(userId);
        return theUser.getPortofolio();
    }

    /*@Override
    public List<Coin> buyCoin(long userId,Coin theCoin){
        User theUser = this.findById(userId);                   ///geting the user with id userId
        List<Coin> portofolio = theUser.getPortofolio();        ///adding to the portofolio list the new coin
        portofolio.add(theCoin);
        return portofolio;// sau theUser.getPortofolio();       /// returning the portofolio
    }*/
    @Override
    public User getUserByEmail(String username){
        return userRepo.findByEmail(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByEmail(username);

        if (user == null) {
            log.error("User not found in database");
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(user.getRole().toString()));

        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
                authorities);
    }


}
