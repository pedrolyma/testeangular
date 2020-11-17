package com.osworksapi.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.groups.ConvertGroup;
import javax.validation.groups.Default;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.osworksapi.api.model.Comentario;
import com.osworksapi.domain.ValidationGroulps;
import com.osworksapi.domain.exception.NegocioException;

@Entity
public class OrdemServico {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotNull
	@ConvertGroup(from = Default.class, to = ValidationGroulps.ClienteId.class)
	@ManyToOne
	@Valid
	private MCliente objCliente;  // um pra um um cli uma os
	@NotBlank
	private String descricao;
	@NotNull
	private BigDecimal preco;
	@Enumerated(EnumType.STRING)  // mostra a string do enum
	@JsonProperty(access = Access.READ_ONLY) // nao deixa enviar
	private StatusOrdemServico status;
	@JsonProperty(access = Access.READ_ONLY) // nao deixa enviar	
	private OffsetDateTime dataAbertura;
	@JsonProperty(access = Access.READ_ONLY) // nao deixa enviar	
	private OffsetDateTime DataFinalizacao;
	
	@OneToMany(mappedBy = "ordemServico")
	private List<Comentario> comentario = new ArrayList<>();
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public MCliente getObjCliente() {
		return objCliente;
	}
	public void setObjCliente(MCliente objCliente) {
		this.objCliente = objCliente;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public BigDecimal getPreco() {
		return preco;
	}
	public void setPreco(BigDecimal preco) {
		this.preco = preco;
	}
	public StatusOrdemServico getStatus() {
		return status;
	}
	public void setStatus(StatusOrdemServico status) {
		this.status = status;
	}
	public OffsetDateTime getDataAbertura() {
		return dataAbertura;
	}
	public void setDataAbertura(OffsetDateTime dataAbertura) {
		this.dataAbertura = dataAbertura;
	}
	public OffsetDateTime getDataFinalizacao() {
		return DataFinalizacao;
	}
	public void setDataFinalizacao(OffsetDateTime dataFinalizacao) {
		DataFinalizacao = dataFinalizacao;
	}
	
	public List<Comentario> getComentario() {
		return comentario;
	}
	public void setComentario(List<Comentario> comentario) {
		this.comentario = comentario;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OrdemServico other = (OrdemServico) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	public boolean podeSerFinalizada() {
		return StatusOrdemServico.ABERTA.equals(getStatus());
	}
	
	public boolean naoPodeSerFinalizada() {
		return !podeSerFinalizada();
	}
	
	public void Finalizar() {
		if (naoPodeSerFinalizada()) {
//		(!StatusOrdemServico.ABERTA.equals(getStatus())) {
			throw new NegocioException("Status da Ordem Nao Pode Ser Finalizada");
		}
		setStatus(StatusOrdemServico.FINALIZADA);
		setDataFinalizacao(OffsetDateTime.now());
	}
	
}
