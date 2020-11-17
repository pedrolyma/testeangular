package com.osworksapi.api.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.osworksapi.domain.model.MCliente;
import com.osworksapi.domain.repository.ClienteRepository;
import com.osworksapi.domain.service.CadastroClienteService;

@RestController
@RequestMapping("/clientes")
public class ClienteController {
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private CadastroClienteService cadastroClienteService;
	
//	@GetMapping("/clientes")
	@GetMapping
	public List<MCliente> Listar() {
      return clienteRepository.findAll();
//      return clienteRepository.findByName(nome);
//      return  clienteRepository.findByNameContainig(nome);
	}

//	@GetMapping("/cliente/{ClienteId}")
	@GetMapping("/{ClienteId}")
	public ResponseEntity<MCliente> buscar(@PathVariable Long ClienteId) {
		Optional<MCliente> objCliente = clienteRepository.findById(ClienteId);
		if (objCliente.isPresent()) {
			return ResponseEntity.ok(objCliente.get());
		}
		return ResponseEntity.notFound().build();
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public MCliente adicionar(@Valid @RequestBody MCliente objCliente) {
	    return cadastroClienteService.salvar(objCliente);	
	}
	
	@PutMapping("/{ClienteId}")
	public ResponseEntity<MCliente> atualizar(@Valid @PathVariable Long ClienteId, @RequestBody MCliente objCliente) {
		if (!clienteRepository.existsById(ClienteId)) {
			return ResponseEntity.notFound().build();
		}
		objCliente.setId(ClienteId);
		objCliente = clienteRepository.save(objCliente);
		return ResponseEntity.ok(objCliente);
	}
	
	@DeleteMapping("/{ClienteId}")
	public ResponseEntity<Void> remover(@PathVariable Long ClienteId) {
		if (!clienteRepository.existsById(ClienteId)) {
			return ResponseEntity.notFound().build();
		}
		cadastroClienteService.excluir(ClienteId);
		return ResponseEntity.noContent().build();
	}
}
