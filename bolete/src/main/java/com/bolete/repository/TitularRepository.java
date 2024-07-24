package com.bolete.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bolete.models.Titular;

@Repository
public interface TitularRepository extends JpaRepository<Titular, Integer> {
  Optional<Titular> findByNome(String nome);
}
