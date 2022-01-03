package com.problem1.myproject.repository;

import com.problem1.myproject.model.Coin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoinRepository extends JpaRepository<Coin,Long> {
}
