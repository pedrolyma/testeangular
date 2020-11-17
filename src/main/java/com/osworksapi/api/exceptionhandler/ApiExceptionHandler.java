package com.osworksapi.api.exceptionhandler;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.ArrayList;

import javax.net.ssl.SSLEngineResult.Status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.osworksapi.domain.exception.EntidadeNaoEncontradaException;
import com.osworksapi.domain.exception.NegocioException;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {
	
	@Autowired
	private MessageSource messageSource;
	
	@ExceptionHandler(EntidadeNaoEncontradaException.class)  // erros nao expecificos 
	public ResponseEntity<Object> handleEntidadeNaoEncontrada(NegocioException ex, WebRequest request) {
		var status = HttpStatus.NOT_FOUND;
		var error = new ModelError();
		error.setStatus(status.value());
		error.setTitulo(ex.getMessage());
		error.setDataHora(OffsetDateTime.now());
		return handleExceptionInternal(ex, error, new HttpHeaders(), status, request);
	}
	
	@ExceptionHandler(NegocioException.class)  // erros nao expecificos 
	public ResponseEntity<Object> handleException(NegocioException ex, WebRequest request) {
		var status = HttpStatus.BAD_REQUEST;
		var error = new ModelError();
		error.setStatus(status.value());
		error.setTitulo(ex.getMessage());
		error.setDataHora(OffsetDateTime.now());
		return handleExceptionInternal(ex, error, new HttpHeaders(), status, request);
	}
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		var listacampo = new ArrayList<ModelError.Campo>();
		for (ObjectError campoerro : ex.getBindingResult().getAllErrors()) {
//		    String mensagem = campoerro.getDefaultMessage();
			String mensagem = messageSource.getMessage(campoerro, LocaleContextHolder.getLocale()); // traduzir para pt-br
		    String nomecampo = ((FieldError) campoerro).getField();
		    listacampo.add(new ModelError.Campo(nomecampo, mensagem));
		}
		var error = new ModelError();  // instancia class modelerror e set os campos
		error.setStatus(status.value());
		error.setTitulo("Verifique Compo com Preenchimento Obrigatorio");
		error.setDataHora(OffsetDateTime.now());
		error.setCampo(listacampo);
		
		return super.handleExceptionInternal(ex, error, headers, status, request);
//		return super.handleMethodArgumentNotValid(ex, headers, status, request);
	}

}
