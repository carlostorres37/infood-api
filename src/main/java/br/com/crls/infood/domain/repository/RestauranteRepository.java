package br.com.crls.infood.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.crls.infood.domain.model.Restaurante;

public interface RestauranteRepository extends JpaRepository<Restaurante, Long> {

}
