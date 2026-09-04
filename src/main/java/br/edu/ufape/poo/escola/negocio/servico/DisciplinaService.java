package br.edu.ufape.poo.escola.negocio.servico;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import br.edu.ufape.poo.escola.comunicacao.dto.DisciplinaRequest;
import br.edu.ufape.poo.escola.dados.InterfaceRepositorioDisciplina;
import br.edu.ufape.poo.escola.negocio.basica.Disciplina;
import br.edu.ufape.poo.escola.negocio.excecao.RecursoNaoEncontradoException;

@Service
public class DisciplinaService {
	private final InterfaceRepositorioDisciplina repositorio;
	public DisciplinaService(InterfaceRepositorioDisciplina repositorio) { this.repositorio = repositorio; }
	@Transactional(readOnly = true) public List<Disciplina> listar() { return repositorio.findAll(); }
	@Transactional(readOnly = true) public Disciplina buscar(Long id) { return repositorio.findById(id).orElseThrow(() -> new RecursoNaoEncontradoException("Disciplina", id)); }
	@Transactional public Disciplina criar(DisciplinaRequest dto) { return repositorio.save(new Disciplina(dto.nome(), dto.cargaHoraria())); }
	@Transactional public Disciplina atualizar(Long id, DisciplinaRequest dto) {
		Disciplina d = buscar(id); d.setNome(dto.nome()); d.setCargaHoraria(dto.cargaHoraria()); return repositorio.save(d);
	}
	@Transactional public void excluir(Long id) { repositorio.delete(buscar(id)); }
}
