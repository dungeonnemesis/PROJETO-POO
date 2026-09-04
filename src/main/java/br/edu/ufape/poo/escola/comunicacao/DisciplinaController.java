package br.edu.ufape.poo.escola.comunicacao;

import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import br.edu.ufape.poo.escola.comunicacao.dto.DisciplinaRequest;
import br.edu.ufape.poo.escola.comunicacao.dto.DisciplinaResponse;
import br.edu.ufape.poo.escola.negocio.basica.Disciplina;
import br.edu.ufape.poo.escola.negocio.servico.DisciplinaService;
import jakarta.validation.Valid;

@RestController @RequestMapping("/api/disciplinas")
public class DisciplinaController {
	private final DisciplinaService servico;
	public DisciplinaController(DisciplinaService servico) { this.servico = servico; }
	@GetMapping public List<DisciplinaResponse> listar() { return servico.listar().stream().map(DisciplinaResponse::de).toList(); }
	@GetMapping("/{id}") public DisciplinaResponse buscar(@PathVariable Long id) { return DisciplinaResponse.de(servico.buscar(id)); }
	@PostMapping public ResponseEntity<DisciplinaResponse> criar(@Valid @RequestBody DisciplinaRequest dto) { Disciplina salvo = servico.criar(dto); URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(salvo.getId()).toUri(); return ResponseEntity.created(uri).body(DisciplinaResponse.de(salvo)); }
	@PutMapping("/{id}") public DisciplinaResponse atualizar(@PathVariable Long id, @Valid @RequestBody DisciplinaRequest dto) { return DisciplinaResponse.de(servico.atualizar(id, dto)); }
	@DeleteMapping("/{id}") public ResponseEntity<Void> excluir(@PathVariable Long id) { servico.excluir(id); return ResponseEntity.noContent().build(); }
}
