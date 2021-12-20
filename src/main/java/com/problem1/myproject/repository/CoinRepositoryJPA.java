package com.problem1.myproject.repository;

import com.problem1.myproject.model.Coin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoinRepositoryJPA extends JpaRepository<Coin,Long> {
}
