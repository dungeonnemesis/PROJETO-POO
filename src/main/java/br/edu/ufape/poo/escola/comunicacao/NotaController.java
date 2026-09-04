package br.edu.ufape.poo.escola.comunicacao;

import java.net.URI;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import br.edu.ufape.poo.escola.comunicacao.dto.NotaRequest;
import br.edu.ufape.poo.escola.comunicacao.dto.NotaLoteRequest;
import br.edu.ufape.poo.escola.comunicacao.dto.NotaResponse;
import br.edu.ufape.poo.escola.negocio.basica.Nota;
import br.edu.ufape.poo.escola.negocio.servico.NotaService;
import jakarta.validation.Valid;

@RestController @RequestMapping("/api/notas")
public class NotaController {
	private final NotaService servico;
	public NotaController(NotaService servico) { this.servico = servico; }
	@GetMapping public List<NotaResponse> listar() { return servico.listar().stream().map(NotaResponse::de).toList(); }
	@GetMapping("/{id}") public NotaResponse buscar(@PathVariable Long id) { return NotaResponse.de(servico.buscar(id)); }
	@PostMapping public ResponseEntity<NotaResponse> criar(@Valid @RequestBody NotaRequest dto) { Nota salvo=servico.criar(dto); URI uri=ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(salvo.getId()).toUri(); return ResponseEntity.created(uri).body(NotaResponse.de(salvo)); }
	@PostMapping("/lote") public ResponseEntity<List<NotaResponse>> criarEmLote(@Valid @RequestBody NotaLoteRequest dto) { return ResponseEntity.status(HttpStatus.CREATED).body(servico.criarEmLote(dto).stream().map(NotaResponse::de).toList()); }
	@PutMapping("/{id}") public NotaResponse atualizar(@PathVariable Long id, @Valid @RequestBody NotaRequest dto) { return NotaResponse.de(servico.atualizar(id,dto)); }
	@DeleteMapping("/{id}") public ResponseEntity<Void> excluir(@PathVariable Long id) { servico.excluir(id); return ResponseEntity.noContent().build(); }
}
