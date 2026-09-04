package br.edu.ufape.poo.escola.comunicacao;

import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import br.edu.ufape.poo.escola.comunicacao.dto.MatriculaRequest;
import br.edu.ufape.poo.escola.comunicacao.dto.MatriculaResponse;
import br.edu.ufape.poo.escola.negocio.basica.Matricula;
import br.edu.ufape.poo.escola.negocio.servico.MatriculaService;
import jakarta.validation.Valid;

@RestController @RequestMapping("/api/matriculas")
public class MatriculaController {
	private final MatriculaService servico;
	public MatriculaController(MatriculaService servico) { this.servico = servico; }
	@GetMapping public List<MatriculaResponse> listar() { return servico.listar().stream().map(MatriculaResponse::de).toList(); }
	@GetMapping("/{id}") public MatriculaResponse buscar(@PathVariable Long id) { return MatriculaResponse.de(servico.buscar(id)); }
	@PostMapping public ResponseEntity<MatriculaResponse> criar(@Valid @RequestBody MatriculaRequest dto) { Matricula salvo=servico.criar(dto); URI uri=ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(salvo.getId()).toUri(); return ResponseEntity.created(uri).body(MatriculaResponse.de(salvo)); }
	@PutMapping("/{id}") public MatriculaResponse atualizar(@PathVariable Long id, @Valid @RequestBody MatriculaRequest dto) { return MatriculaResponse.de(servico.atualizar(id,dto)); }
	@DeleteMapping("/{id}") public ResponseEntity<Void> excluir(@PathVariable Long id) { servico.excluir(id); return ResponseEntity.noContent().build(); }
}
