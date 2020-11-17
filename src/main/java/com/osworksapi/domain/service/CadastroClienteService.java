package com.osworksapi.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.osworksapi.domain.exception.NegocioException;
import com.osworksapi.domain.model.MCliente;
import com.osworksapi.domain.repository.ClienteRepository;

@Service
public class CadastroClienteService {
	
	@Autowired
	ClienteRepository clienteRepository;
	
	public MCliente salvar(MCliente objCliente) {
		MCliente clienteExistente = clienteRepository.findByEmail(objCliente.getEmail());
		if (clienteExistente != null && !clienteExistente.equals(objCliente)) {
			throw new NegocioException("Ja Existe Um Cliente Cadastrado Com Este Email");
		}
		return clienteRepository.save(objCliente);
	}

	public void excluir(Long ClienteId) {
		clienteRepository.deleteById(ClienteId);
	}
}
