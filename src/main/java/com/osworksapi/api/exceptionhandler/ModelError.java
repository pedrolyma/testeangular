package com.osworksapi.api.exceptionhandler;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)  // nao retorna campos nulos
public class ModelError {
	
	private Integer Status;
	private OffsetDateTime DataHora;
	private String Titulo;
	private List<Campo> campo;
	
	public static class Campo {
		private String nomecampo;
		private String msgcampo;
		public String getNomecampo() {
			return nomecampo;
		}
		
		public void setNomecampo(String nomecampo) {
			this.nomecampo = nomecampo;
		}
		public String getMsgcampo() {
			return msgcampo;
		}
		public void setMsgcampo(String msgcampo) {
			this.msgcampo = msgcampo;
		}
		public Campo(String nomecampo, String msgcampo) {
			super();
			this.nomecampo = nomecampo;
			this.msgcampo = msgcampo;
		}
		
	}
	
	public Integer getStatus() {
		return Status;
	}
	public void setStatus(Integer status) {
		Status = status;
	}
	public OffsetDateTime getDataHora() {
		return DataHora;
	}
	public void setDataHora(OffsetDateTime dataHora) {
		DataHora = dataHora;
	}
	public String getTitulo() {
		return Titulo;
	}
	public void setTitulo(String titulo) {
		Titulo = titulo;
	}
	public List<Campo> getCampo() {
		return campo;
	}
	public void setCampo(List<Campo> campo) {
		this.campo = campo;
	}

}
