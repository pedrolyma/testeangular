package com.osworksapi.domain.service;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.osworksapi.api.model.Comentario;
import com.osworksapi.domain.exception.EntidadeNaoEncontradaException;
import com.osworksapi.domain.exception.NegocioException;
import com.osworksapi.domain.model.MCliente;
import com.osworksapi.domain.model.OrdemServico;
import com.osworksapi.domain.model.StatusOrdemServico;
import com.osworksapi.domain.repository.ClienteRepository;
import com.osworksapi.domain.repository.ComentarioRepository;
import com.osworksapi.domain.repository.OrdemServicoRepository;

@Service
public class GestaoOrdemServicoService {

	@Autowired
	private OrdemServicoRepository ordemServicoRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;
		
	@Autowired
	private ComentarioRepository comentarioRepository;
		
	public OrdemServico criarOS(OrdemServico ordemServico) {
		MCliente obCliente = clienteRepository.findById(ordemServico.getObjCliente().getId())
				.orElseThrow(() -> new NegocioException("Cliente Nao Encontrado"));
		ordemServico.setObjCliente(obCliente);
		ordemServico.setStatus(StatusOrdemServico.ABERTA);
		ordemServico.setDataAbertura(OffsetDateTime.now());
		
	    return ordemServicoRepository.save(ordemServico);	
	}
	
	public void finalizar(Long ordemServicoId) {
		OrdemServico ordemServico = buscar(ordemServicoId);
		ordemServico.Finalizar();
		ordemServicoRepository.save(ordemServico);
	}
	
	public Comentario adicionarComentario(Long ordemServicoId, String descricao) {
		OrdemServico ordemServico = buscar(ordemServicoId);
//		OrdemServico ordemServicoRepository.findById(ordemServicoId)
//				.orElseThrow(() -> new EntidadeNaoEncontradaException("Ordem de Servico nao Encontrada"));
		Comentario comentario = new Comentario();
		comentario.setDataEnvio(OffsetDateTime.now());
		comentario.setDescricao(descricao);
		comentario.setOrdemServico(ordemServico);
		return comentarioRepository.save(comentario);
	}
	
	private OrdemServico buscar(Long ordemServicoId) {
		OrdemServico ordemServico = ordemServicoRepository.findById(ordemServicoId)
				.orElseThrow(() -> new EntidadeNaoEncontradaException("Ordem de Servico nao Encontrada"));
	}
}
