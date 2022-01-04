package com.problem1.myproject.repository;

import com.problem1.myproject.model.RolesEnum;
import com.problem1.myproject.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository underTest;

    @Test
    void checkFindByEmail() {
         User user = new User();
         user.setId(0);
         user.setEmail("test.com");
         user.setName("incercare1");
         user.setPassword("das");
         user.setRole(RolesEnum.USER);
         user.setBalance(1.0);
        user.setDateOfBirth(null);
        user.setPortofolio(null);

        //underTest.save(user);
        User exists = underTest.findByEmail("test.com");

        assertThat(exists).isEqualTo(user);

    }
}