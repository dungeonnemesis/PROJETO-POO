package br.edu.ufape.poo.escola.comunicacao;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.edu.ufape.poo.escola.comunicacao.dto.JustificativaRequest;
import br.edu.ufape.poo.escola.comunicacao.dto.JustificativaResponse;
import br.edu.ufape.poo.escola.negocio.basica.Justificativa;
import br.edu.ufape.poo.escola.negocio.fachada.EscolaFacade;
import br.edu.ufape.poo.escola.negocio.servico.JustificativaService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/justificativas")
public class JustificativaController {

	private final EscolaFacade fachada;
	private final JustificativaService servico;

	public JustificativaController(EscolaFacade fachada, JustificativaService servico) {
		this.fachada = fachada;
		this.servico = servico;
	}

	@GetMapping
	public List<JustificativaResponse> listar() {
		return servico.listar().stream().map(JustificativaResponse::de).toList();
	}

	@GetMapping("/{id}")
	public JustificativaResponse buscar(@PathVariable Long id) {
		return JustificativaResponse.de(servico.buscar(id));
	}

	@PostMapping
	public ResponseEntity<JustificativaResponse> criar(@Valid @RequestBody JustificativaRequest dto) {
		// Passa pela fachada de propósito: é ali que mora a regra de que só se
		// pode justificar uma frequência com status AUSENTE.
		Justificativa salva = fachada.registrarJustificativaDeFalta(dto.frequenciaId(), dto.motivo());
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(salva.getId()).toUri();
		return ResponseEntity.created(uri).body(JustificativaResponse.de(salva));
	}
}
