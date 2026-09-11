package br.edu.ufape.poo.escola.negocio.servico;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.edu.ufape.poo.escola.comunicacao.dto.JustificativaRequest;
import br.edu.ufape.poo.escola.dados.InterfaceRepositorioJustificativa;
import br.edu.ufape.poo.escola.negocio.basica.Frequencia;
import br.edu.ufape.poo.escola.negocio.basica.Justificativa;
import br.edu.ufape.poo.escola.negocio.basica.StatusFrequencia;
import br.edu.ufape.poo.escola.negocio.excecao.RecursoNaoEncontradoException;

@Service
public class JustificativaService {

	private final InterfaceRepositorioJustificativa repositorio;
	private final FrequenciaService frequenciaService;

	public JustificativaService(InterfaceRepositorioJustificativa repositorio, FrequenciaService frequenciaService) {
		this.repositorio = repositorio;
		this.frequenciaService = frequenciaService;
	}

	@Transactional(readOnly = true)
	public List<Justificativa> listar() {
		return repositorio.findAll();
	}

	@Transactional(readOnly = true)
	public Justificativa buscar(Long id) {
		return repositorio.findById(id).orElseThrow(() -> new RecursoNaoEncontradoException("Justificativa", id));
	}

	/**
	 * Cria a justificativa e atualiza o status da frequência associada
	 * para JUSTIFICADA. A validação de que a frequência precisa estar
	 * AUSENTE antes de chegar aqui é responsabilidade da EscolaFacade
	 * (regra de fachada) — este método assume que essa checagem já
	 * ocorreu.
	 */
	@Transactional
	public Justificativa criar(JustificativaRequest dto) {
		Frequencia frequencia = frequenciaService.buscar(dto.frequenciaId());
		if (repositorio.existsByFrequenciaId(frequencia.getId())) {
			throw new IllegalArgumentException("Esta frequencia ja possui uma justificativa.");
		}
		Justificativa salva = repositorio.save(new Justificativa(dto.motivo(), frequencia));
		frequencia.setStatus(StatusFrequencia.JUSTIFICADA);
		return salva;
	}

	@Transactional
	public void excluir(Long id) {
		repositorio.delete(buscar(id));
	}
}
