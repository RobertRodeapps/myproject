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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
        verify(coinRepo).findAll();

    }

    @Test
    void cannotFindById() {

        given(coinRepo.findById(1L)).willReturn(Optional.empty());
/*
        when(coinService.findById(1L)).thenThrow(new ObjectNotFoundException("Did not find Coin id - 1"));
        assertThat(coinService.findById(1L)).withFailMessage("Did not find Coin id - 1");
*/

        assertThatThrownBy(() -> coinService.findById(1L))
                .isInstanceOf(ObjectNotFoundException.class)
                .hasMessageContaining("Did not find Coin id - 1");
       /// then(caught).isInstanceOf(ObjectNotFoundException.class);

        /*given(otherServiceMock.bar()).willThrow(new MyException());

        when(() -> myService.foo());

        then(caughtException()).isInstanceOf(MyException.class);*/

    }

    @Test
    void canAddCoin() {
        Coin coin = createCoin(1,"ada",12.2,100.2);

        //when
        coinService.save(coin);

        //then
        ArgumentCaptor<Coin> coinArgumentCaptor =
                ArgumentCaptor.forClass(Coin.class);

        verify(coinRepo).save(coinArgumentCaptor.capture());

        Coin capturedCoin = coinArgumentCaptor.getValue();
        assertThat(capturedCoin).isEqualTo(coin);

    }

    @Test
    void deleteById() {
        Coin coin = createCoin(1,"ada",12.2,100.2);

        coinService.deleteById(coin.getId());
        verify(coinRepo).deleteById((long)1);
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