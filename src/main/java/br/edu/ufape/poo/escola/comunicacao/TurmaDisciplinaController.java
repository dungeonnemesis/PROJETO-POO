package br.edu.ufape.poo.escola.comunicacao;

import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import br.edu.ufape.poo.escola.comunicacao.dto.TurmaDisciplinaRequest;
import br.edu.ufape.poo.escola.comunicacao.dto.TurmaDisciplinaResponse;
import br.edu.ufape.poo.escola.negocio.basica.TurmaDisciplina;
import br.edu.ufape.poo.escola.negocio.servico.TurmaDisciplinaService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/turmas/{turmaId}/grade")
public class TurmaDisciplinaController {
	private final TurmaDisciplinaService servico;
	public TurmaDisciplinaController(TurmaDisciplinaService servico) { this.servico = servico; }

	@GetMapping
	public List<TurmaDisciplinaResponse> listar(@PathVariable Long turmaId) { return servico.listar(turmaId).stream().map(TurmaDisciplinaResponse::de).toList(); }

	@PostMapping
	public ResponseEntity<TurmaDisciplinaResponse> adicionar(@PathVariable Long turmaId, @Valid @RequestBody TurmaDisciplinaRequest dto) {
		TurmaDisciplina salva = servico.adicionar(turmaId, dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(salva.getId()).toUri();
		return ResponseEntity.created(uri).body(TurmaDisciplinaResponse.de(salva));
	}

	@DeleteMapping("/{gradeId}")
	public ResponseEntity<Void> remover(@PathVariable Long turmaId, @PathVariable Long gradeId) { servico.remover(turmaId, gradeId); return ResponseEntity.noContent().build(); }
}
