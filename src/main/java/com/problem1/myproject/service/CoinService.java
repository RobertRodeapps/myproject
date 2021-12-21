package com.problem1.myproject.service;

import com.problem1.myproject.exceptions.ObjectNotFoundException;
import com.problem1.myproject.model.Coin;
import com.problem1.myproject.repository.CoinRepositoryJPA;
import com.problem1.myproject.service.implementation.ICoinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class CoinService implements ICoinService {

    private CoinRepositoryJPA coinRepo;

    @Autowired
    public CoinService(CoinRepositoryJPA coinRepo) {
        this.coinRepo = coinRepo;
    }

    @Override
    @Transactional
    public List<Coin> findAll() {
        return this.coinRepo.findAll();
    }

    @Override
    @Transactional
    public Coin findById(long theId) {

        Optional<Coin> result = coinRepo.findById(theId);

        Coin theCoin = null;

        if (result.isPresent()) {
            theCoin = result.get();
        }
        else {
            // we didn't find the coin
            throw new ObjectNotFoundException("Did not find employee id - " + theId);
        }

        return theCoin;
    }

    @Override
    @Transactional
    public void save(Coin theCoin) {
        coinRepo.save(theCoin);
    }

    @Override
    @Transactional
    public void deleteById(long theId) {
        coinRepo.deleteById(theId);
    }
}
