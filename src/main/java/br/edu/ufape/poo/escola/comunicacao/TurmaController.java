package br.edu.ufape.poo.escola.comunicacao;

import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import br.edu.ufape.poo.escola.comunicacao.dto.TurmaRequest;
import br.edu.ufape.poo.escola.comunicacao.dto.TurmaResponse;
import br.edu.ufape.poo.escola.negocio.basica.Turma;
import br.edu.ufape.poo.escola.negocio.servico.TurmaService;
import jakarta.validation.Valid;

@RestController @RequestMapping("/api/turmas")
public class TurmaController {
	private final TurmaService servico;
	public TurmaController(TurmaService servico) { this.servico = servico; }
	@GetMapping public List<TurmaResponse> listar() { return servico.listar().stream().map(TurmaResponse::de).toList(); }
	@GetMapping("/{id}") public TurmaResponse buscar(@PathVariable Long id) { return TurmaResponse.de(servico.buscar(id)); }
	@PostMapping public ResponseEntity<TurmaResponse> criar(@Valid @RequestBody TurmaRequest dto) { Turma salva = servico.criar(dto); URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(salva.getId()).toUri(); return ResponseEntity.created(uri).body(TurmaResponse.de(salva)); }
	@PutMapping("/{id}") public TurmaResponse atualizar(@PathVariable Long id, @Valid @RequestBody TurmaRequest dto) { return TurmaResponse.de(servico.atualizar(id, dto)); }
	@DeleteMapping("/{id}") public ResponseEntity<Void> excluir(@PathVariable Long id) { servico.excluir(id); return ResponseEntity.noContent().build(); }
}
