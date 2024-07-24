package com.bolete.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bolete.models.Boleto;

@Repository
public interface BoletoRepository extends JpaRepository<Boleto, Integer> {

}
