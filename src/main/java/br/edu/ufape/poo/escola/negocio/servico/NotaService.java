package br.edu.ufape.poo.escola.negocio.servico;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import br.edu.ufape.poo.escola.comunicacao.dto.NotaRequest;
import br.edu.ufape.poo.escola.dados.InterfaceRepositorioNota;
import br.edu.ufape.poo.escola.negocio.basica.Nota;
import br.edu.ufape.poo.escola.negocio.excecao.RecursoNaoEncontradoException;

@Service
public class NotaService {
	private final InterfaceRepositorioNota repositorio;
	private final MatriculaService matriculaService;
	public NotaService(InterfaceRepositorioNota repositorio, MatriculaService matriculaService) { this.repositorio = repositorio; this.matriculaService = matriculaService; }
	@Transactional(readOnly = true) public List<Nota> listar() { return repositorio.findAll(); }
	@Transactional(readOnly = true) public Nota buscar(Long id) { return repositorio.findById(id).orElseThrow(() -> new RecursoNaoEncontradoException("Nota", id)); }
	@Transactional public Nota criar(NotaRequest dto) { return repositorio.save(new Nota(dto.valor(), matriculaService.buscar(dto.matriculaId()))); }
	@Transactional public Nota atualizar(Long id, NotaRequest dto) { Nota n = buscar(id); n.setValor(dto.valor()); n.setMatricula(matriculaService.buscar(dto.matriculaId())); return repositorio.save(n); }
	@Transactional public void excluir(Long id) { repositorio.delete(buscar(id)); }
}
