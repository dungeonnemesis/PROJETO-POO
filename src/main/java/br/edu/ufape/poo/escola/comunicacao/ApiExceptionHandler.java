package br.edu.ufape.poo.escola.comunicacao;

import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import br.edu.ufape.poo.escola.negocio.excecao.RecursoNaoEncontradoException;

@RestControllerAdvice
public class ApiExceptionHandler {
	@ExceptionHandler(RecursoNaoEncontradoException.class)
	public ResponseEntity<ErroApi> naoEncontrado(RecursoNaoEncontradoException ex) { return resposta(HttpStatus.NOT_FOUND, ex.getMessage(), Map.of()); }

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErroApi> validacao(MethodArgumentNotValidException ex) {
		Map<String, String> campos = new LinkedHashMap<>();
		ex.getBindingResult().getFieldErrors().forEach(erro -> campos.putIfAbsent(erro.getField(), erro.getDefaultMessage()));
		return resposta(HttpStatus.BAD_REQUEST, "Dados invalidos", campos);
	}

	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<ErroApi> conflito(DataIntegrityViolationException ex) {
		String mensagem = ex.getMessage() != null && ex.getMessage().contains("Aluno ja possui matricula ativa neste ano")
				? "Este aluno ja possui uma matricula ativa neste ano." : "Registro duplicado ou vinculado a outro recurso";
		return resposta(HttpStatus.CONFLICT, mensagem, Map.of());
	}

	private ResponseEntity<ErroApi> resposta(HttpStatus status, String mensagem, Map<String, String> campos) {
		return ResponseEntity.status(status).body(new ErroApi(Instant.now(), status.value(), status.getReasonPhrase(), mensagem, campos));
	}

	public record ErroApi(Instant instante, int status, String erro, String mensagem, Map<String, String> campos) {}
}
