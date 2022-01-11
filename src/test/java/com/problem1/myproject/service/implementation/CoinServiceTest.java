package com.problem1.myproject.service.implementation;

import com.problem1.myproject.exceptions.ObjectNotFoundException;
import com.problem1.myproject.model.Coin;
import com.problem1.myproject.repository.CoinRepository;
import com.problem1.myproject.service.ICoinService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.internal.matchers.Null;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.OngoingStubbing;

import java.util.List;
import java.util.Optional;

import static java.util.Arrays.asList;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CoinServiceTest {

    @Mock private CoinRepository coinRepo;
    @InjectMocks
    private CoinService coinService;

    @Test
    void canFindAll() {
        Coin firstCoin = createCoin(2,"xrp",12.1,3213.22);
        Coin secondCoin = createCoin(3,"egld",123.1,3211233.22);

        given(coinRepo.findAll()).willReturn(asList(firstCoin, secondCoin));

        //when
        List<Coin> receivedCoins = coinService.findAll();
        //then
        assertThat(receivedCoins).asList().containsExactly(firstCoin,secondCoin);
        verify(coinRepo,times(1)).findAll();

    }
    @Test
    void  FindById() {
        Coin coin = createCoin(1,"ada",12.2,100.2);

        given(coinRepo.findById(anyLong())).willReturn(Optional.of(coin));
        Coin receivedCoin = coinService.findById(1L);

        assertThat(coin).isEqualTo(receivedCoin);
    }
    @Test
    void cannotFindById() {

        given(coinRepo.findById(1L)).willReturn(Optional.empty());

        assertThatThrownBy(() -> coinService.findById(1L))
                .isInstanceOf(ObjectNotFoundException.class)
                .hasMessageContaining("Did not find Coin id - 1");
    }

    @Test
    void canAddCoin() {
        Coin coin = createCoin(1,"ada",12.2,100.2);

        //when
        Coin savedCoin = coinService.save(coin);

        //then
        assertThat(savedCoin).isEqualTo(coin);
        verify(coinRepo,times(1)).save(any(Coin.class));

    }

    @Test
    void deleteById() {
        Coin coin = createCoin(3,"ada",12.2,100.2);
        given(coinRepo.findById(3L)).willReturn(Optional.of(coin));

        Coin deletedCoin = coinService.deleteById(coin.getId());

        assertThat(deletedCoin).isEqualTo(coin);
        verify(coinRepo,times(1)).deleteById(anyLong());

    }
    private Coin createCoin(long id, String name,double price, double supply){
        Coin coin = new Coin();
        coin.setPrice(price);
        coin.setCoinName(name);
        coin.setSupply(supply);
        coin.setHolders(null);
        coin.setId(id);
        return coin;
    }
}