package com.osworksapi.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.osworksapi.domain.model.MCliente;

@Repository
public interface ClienteRepository extends JpaRepository<MCliente, Long> {
	
	List<MCliente> findByName(String nome);
	List<MCliente> findByNameContainig(String nome); // parte do nome
	MCliente findByEmail(String email);
}
