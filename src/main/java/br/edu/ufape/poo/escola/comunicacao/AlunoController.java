package br.edu.ufape.poo.escola.comunicacao;

import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import br.edu.ufape.poo.escola.comunicacao.dto.AlunoRequest;
import br.edu.ufape.poo.escola.comunicacao.dto.AlunoResponse;
import br.edu.ufape.poo.escola.negocio.basica.Aluno;
import br.edu.ufape.poo.escola.negocio.servico.AlunoService;
import jakarta.validation.Valid;

@RestController @RequestMapping("/api/alunos")
public class AlunoController {
	private final AlunoService servico;
	public AlunoController(AlunoService servico) { this.servico = servico; }
	@GetMapping public List<AlunoResponse> listar() { return servico.listar().stream().map(AlunoResponse::de).toList(); }
	@GetMapping("/{id}") public AlunoResponse buscar(@PathVariable Long id) { return AlunoResponse.de(servico.buscar(id)); }
	@PostMapping public ResponseEntity<AlunoResponse> criar(@Valid @RequestBody AlunoRequest dto) {
		Aluno salvo = servico.criar(dto); URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(salvo.getId()).toUri(); return ResponseEntity.created(uri).body(AlunoResponse.de(salvo));
	}
	@PutMapping("/{id}") public AlunoResponse atualizar(@PathVariable Long id, @Valid @RequestBody AlunoRequest dto) { return AlunoResponse.de(servico.atualizar(id, dto)); }
	@DeleteMapping("/{id}") public ResponseEntity<Void> excluir(@PathVariable Long id) { servico.excluir(id); return ResponseEntity.noContent().build(); }
}
