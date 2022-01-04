package com.problem1.myproject.service.implementation;

import com.problem1.myproject.exceptions.ObjectNotFoundException;
import com.problem1.myproject.model.Coin;
import com.problem1.myproject.repository.CoinRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;

import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CoinServiceTest {

    @Mock private CoinRepository coinRepo;
    private CoinService underTest;


    @BeforeEach
    void setUp(){
        underTest = new CoinService(coinRepo);
    }



    @Test
    void canFindAll() {
        //when
        underTest.findAll();
        //then
        verify(coinRepo).findAll();
    }

    @Test
    void canFindById() {

          assertThatThrownBy(() -> underTest.findById(1))
                .isInstanceOf(ObjectNotFoundException.class)
                .hasMessageContaining("Did not find employee id - 1");
    }

    @Test
    void canAddCoin() {
        Coin coin = new Coin();
        coin.setPrice(12.3);
        coin.setCoinName("ara");
        coin.setSupply(123.0);
        coin.setHolders(null);
        coin.setId(0);
        //when
        underTest.save(coin);

        //then
        ArgumentCaptor<Coin> coinArgumentCaptor=
                ArgumentCaptor.forClass(Coin.class);

        verify(coinRepo).save(coinArgumentCaptor.capture());

        Coin capturedCoin = coinArgumentCaptor.getValue();
        assertThat(capturedCoin).isEqualTo(coin);

    }

    @Test
    void deleteById() {
        Coin coin = new Coin();
        coin.setPrice(12.3);
        coin.setCoinName("ara");
        coin.setSupply(123.0);
        coin.setHolders(null);
        coin.setId(1);


        underTest.deleteById(coin.getId());
        verify(coinRepo).deleteById((long)1);
    }
}