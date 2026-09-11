package br.edu.ufape.poo.escola.comunicacao;

import java.net.URI;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.edu.ufape.poo.escola.comunicacao.dto.FrequenciaLoteRequest;
import br.edu.ufape.poo.escola.comunicacao.dto.FrequenciaRequest;
import br.edu.ufape.poo.escola.comunicacao.dto.FrequenciaResponse;
import br.edu.ufape.poo.escola.negocio.basica.Frequencia;
import br.edu.ufape.poo.escola.negocio.servico.FrequenciaService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/frequencias")
public class FrequenciaController {

	private final FrequenciaService servico;

	public FrequenciaController(FrequenciaService servico) {
		this.servico = servico;
	}

	@GetMapping
	public List<FrequenciaResponse> listar() {
		return servico.listar().stream().map(FrequenciaResponse::de).toList();
	}

	@GetMapping("/{id}")
	public FrequenciaResponse buscar(@PathVariable Long id) {
		return FrequenciaResponse.de(servico.buscar(id));
	}

	@GetMapping("/matricula/{matriculaId}")
	public List<FrequenciaResponse> buscarPorMatricula(@PathVariable Long matriculaId) {
		return servico.buscarPorMatricula(matriculaId).stream().map(FrequenciaResponse::de).toList();
	}

	@GetMapping("/matricula/{matriculaId}/percentual")
	public double percentualPresenca(@PathVariable Long matriculaId) {
		return servico.calcularPercentualPresenca(matriculaId);
	}

	@PostMapping
	public ResponseEntity<FrequenciaResponse> criar(@Valid @RequestBody FrequenciaRequest dto) {
		Frequencia salvo = servico.criar(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(salvo.getId()).toUri();
		return ResponseEntity.created(uri).body(FrequenciaResponse.de(salvo));
	}

	@PostMapping("/lote")
	public ResponseEntity<List<FrequenciaResponse>> criarEmLote(@Valid @RequestBody FrequenciaLoteRequest dto) {
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(servico.criarEmLote(dto).stream().map(FrequenciaResponse::de).toList());
	}

	@PutMapping("/{id}")
	public FrequenciaResponse atualizar(@PathVariable Long id, @Valid @RequestBody FrequenciaRequest dto) {
		return FrequenciaResponse.de(servico.atualizar(id, dto));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> excluir(@PathVariable Long id) {
		servico.excluir(id);
		return ResponseEntity.noContent().build();
	}
}
