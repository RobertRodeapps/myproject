package com.problem1.myproject.controller;


import com.problem1.myproject.exceptions.ObjectNotFoundException;
import com.problem1.myproject.model.Coin;
import com.problem1.myproject.model.dto.CoinDTO;
import com.problem1.myproject.service.implementation.CoinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;

@RestController
@RequestMapping("/menu")
public class CoinRestController {
        private CoinService coinService;

    @Autowired
    public CoinRestController(CoinService coinService) {
        this.coinService = coinService;
    }

    @GetMapping("/coins")
    @Transactional
    public List<Coin> getAll(){
        return this.coinService.findAll();
    }

    @GetMapping("/coins/{coinId}")
    @Transactional
    public Coin getCoin(@PathVariable long coinId){
        Coin theCoin = this.coinService.findById(coinId);
        if (theCoin == null){
            throw new ObjectNotFoundException("The Coin with id " + coinId + " was not found.\n");
        }
        return theCoin;
    }

    @PostMapping("/coins")
    @Transactional
    public Coin addCoin(@RequestBody CoinDTO theCoin){

        Coin newCoin = new Coin();

        ///Transforming a Coin dto into a Coin Object
        newCoin.setCoinName(theCoin.getCoinName());
        newCoin.setId(0);
        newCoin.setSupply(theCoin.getSupply());
        newCoin.setPrice(theCoin.getPrice());

        this.coinService.save(newCoin);
        return newCoin;
    }

    @PutMapping("/coins/{coinId}")
    @Transactional
    public Coin updateCoin(@RequestBody CoinDTO newCoin, @PathVariable long coinId){
        Coin theCoin = this.coinService.findById(coinId);
        if (theCoin == null){
            throw new ObjectNotFoundException("The Coin with id " + coinId + " was not found.\n");
        }
        theCoin.setCoinName(newCoin.getCoinName());
        theCoin.setPrice(newCoin.getPrice());
        theCoin.setSupply(newCoin.getSupply());
        return theCoin;
    }

    @DeleteMapping("/coins/{coinId}")
    @Transactional
    public Coin deleteCoin(@PathVariable long coinId){
        Coin deletedCoin = this.coinService.findById(coinId);

        if (deletedCoin == null){
            throw new ObjectNotFoundException("The Coin with id " + coinId + " was not found.\n");
        }

        this.coinService.deleteById(coinId);
        return deletedCoin;
    }
}
