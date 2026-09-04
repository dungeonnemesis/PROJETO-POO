package br.edu.ufape.poo.escola.comunicacao;

import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import br.edu.ufape.poo.escola.comunicacao.dto.ProfessorRequest;
import br.edu.ufape.poo.escola.comunicacao.dto.ProfessorResponse;
import br.edu.ufape.poo.escola.negocio.basica.Professor;
import br.edu.ufape.poo.escola.negocio.servico.ProfessorService;
import jakarta.validation.Valid;

@RestController @RequestMapping("/api/professores")
public class ProfessorController {
	private final ProfessorService servico;
	public ProfessorController(ProfessorService servico) { this.servico = servico; }
	@GetMapping public List<ProfessorResponse> listar() { return servico.listar().stream().map(ProfessorResponse::de).toList(); }
	@GetMapping("/{id}") public ProfessorResponse buscar(@PathVariable Long id) { return ProfessorResponse.de(servico.buscar(id)); }
	@PostMapping public ResponseEntity<ProfessorResponse> criar(@Valid @RequestBody ProfessorRequest dto) { Professor salvo = servico.criar(dto); URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(salvo.getId()).toUri(); return ResponseEntity.created(uri).body(ProfessorResponse.de(salvo)); }
	@PutMapping("/{id}") public ProfessorResponse atualizar(@PathVariable Long id, @Valid @RequestBody ProfessorRequest dto) { return ProfessorResponse.de(servico.atualizar(id, dto)); }
	@DeleteMapping("/{id}") public ResponseEntity<Void> excluir(@PathVariable Long id) { servico.excluir(id); return ResponseEntity.noContent().build(); }
}
