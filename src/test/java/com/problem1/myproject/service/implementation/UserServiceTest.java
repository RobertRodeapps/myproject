package com.problem1.myproject.service.implementation;

 import com.problem1.myproject.model.Coin;
 import com.problem1.myproject.model.RolesEnum;
import com.problem1.myproject.model.User;
 import com.problem1.myproject.repository.UserRepository;
 import org.junit.jupiter.api.Disabled;
 import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
 import org.springframework.boot.test.mock.mockito.MockBean;
 import org.springframework.security.core.GrantedAuthority;
 import org.springframework.security.core.authority.SimpleGrantedAuthority;
 import org.springframework.security.core.userdetails.UserDetails;
 import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
 import org.springframework.security.crypto.password.PasswordEncoder;

 import java.util.ArrayList;
 import java.util.Collection;
 import java.util.List;
 import java.util.Optional;

 import static java.util.Arrays.asList;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
 import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class UserServiceTest {

        @Mock
        private UserRepository userRepo;
        @Mock
        private PasswordEncoder passwordEncoder;
        @InjectMocks
        private UserService userService;

    @Test
    void findAll() {
        User firstUser = new User(1,"ben","ben.com","1234",null,12.3,null, RolesEnum.ADMIN);
        User secondUser = new User(2,"tiri","tiri.com","1234",null,1211.3,null, RolesEnum.ADMIN);

        given(userRepo.findAll()).willReturn(asList(firstUser, secondUser));

        //when
        List<User> receivedUsers = userService.findAll();
        //then
        assertThat(receivedUsers).asList().containsExactly(firstUser,secondUser);
        verify(userRepo,times(1)).findAll();
    }

    @Test
    void findById() {
        User newUser = new User(1,"ben","ben.com","1234",null,12.3,null, RolesEnum.ADMIN);
        when(userRepo.findById(1L)).thenReturn(java.util.Optional.of(newUser));

        User receivedUser = userService.findById(1L);

        verify(userRepo,times(1)).findById(1L);
        assertThat(receivedUser).isEqualTo(newUser);

    }

    @Test
    void canSave() {
        User newUser = new User(1,"ben","ben.com","1234",null,12.3,null, RolesEnum.ADMIN);

        //when
        when(passwordEncoder.encode("1234")).thenReturn("encodedMock");

        User savedUser = userService.save(newUser);

        //then
        verify(userRepo,times(1)).save(any(User.class));
        assertThat(newUser).isEqualTo(savedUser);

    }

    @Test
    void deleteById() {
         User newUser = new User(1,"ben","ben.com","1234",null,12.3,null, RolesEnum.ADMIN);

         when(userRepo.findById(1L)).thenReturn(java.util.Optional.of(newUser));
        User deletedUser = userService.deleteById(newUser.getId());

        verify(userRepo,times(1)).deleteById((long)1);
        assertThat(newUser).isEqualTo(deletedUser);
     }

    @Test
    void getPortofolio() {
        List<Coin> coins = new ArrayList<>();
        coins.add(new Coin());
        coins.add(new Coin());

        User newUser = new User(1,"ben","ben.com","1234",null,12.3,coins, RolesEnum.ADMIN);
        when(userRepo.findById(1L)).thenReturn(java.util.Optional.of(newUser));

        List<Coin> receivedCoins = userService.getPortofolio(newUser.getId());

        assertThat(receivedCoins.size()).isEqualTo(2);

        verify(userRepo,times(1)).findById((long)1);

    }

    @Test
    void getUserByEmail() {
        User newUser = new User(1,"ben","ben.com","1234",null,12.3,null, RolesEnum.ADMIN);
        when(userRepo.findByEmail(newUser.getEmail())).thenReturn(newUser);

        User receivedUser = userService.getUserByEmail(newUser.getEmail());

        assertThat(receivedUser).isEqualTo(newUser);
    }

    @Test
    void loadUserByUsername() {
        User newUser = new User(1,"ben","ben.com","1234",null,12.3,null, RolesEnum.ADMIN);
        when(userRepo.findByEmail(newUser.getEmail())).thenReturn(newUser);

        UserDetails received = userService.loadUserByUsername(newUser.getEmail());
        assertThat(received.getUsername()).isEqualTo("ben.com");
        assertThat(received.getPassword()).isEqualTo("1234");

    }
}