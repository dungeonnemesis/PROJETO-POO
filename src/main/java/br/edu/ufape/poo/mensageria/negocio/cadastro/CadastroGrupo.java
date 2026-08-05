package br.edu.ufape.poo.mensageria.negocio.cadastro;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.ufape.poo.mensageria.dados.InterfaceRepositorioGrupo;
import br.edu.ufape.poo.mensageria.negocio.basica.Grupo;
import br.edu.ufape.poo.mensageria.negocio.basica.Usuario;

@Service
public class CadastroGrupo implements InterfaceCadastroGrupo {

	@Autowired
	private InterfaceRepositorioGrupo repositorioGrupo;

	@Override
	public void salvarGrupo(Grupo novo) 
			throws RegistroDuplicadoException{
		if(repositorioGrupo.findByNomeIgnoreCase(novo.getNome()) == null) {
			repositorioGrupo.save(novo);
		} else {
			RegistroDuplicadoException erro = 
					new RegistroDuplicadoException("Não é possível cadastrar mais de um grupo com o mesmo nome. Escolha um nome diferente.");
			throw erro;
		}
	}

	@Override
	public void atualizarGrupo(Grupo novo) 
			throws RegistroDuplicadoException{
		Grupo existente = repositorioGrupo.findByNomeIgnoreCase(novo.getNome());
		if(existente == null || existente.getId() == novo.getId()) {
			repositorioGrupo.save(novo);
		} else {
			RegistroDuplicadoException erro = 
					new RegistroDuplicadoException("Não é possível cadastrar mais de um grupo com o mesmo nome. Escolha um nome diferente.");
			throw erro;
		}	

	}

	@Override
	public List<Grupo> listarTodosGrupos() {
		return repositorioGrupo.findAll();
	}

	@Override
	public Grupo procurarGrupoPorNome(String nome) 
			throws RegistroInexistenteException{
		Grupo procurado = repositorioGrupo.findByNomeIgnoreCase(nome);
		if(procurado != null)
			return procurado;
		RegistroInexistenteException erro =
				new RegistroInexistenteException("Não existe grupo com o nome " + nome);
		throw erro;

	}

	@Override
	public void apagarGrupo(Grupo entity) {
		repositorioGrupo.delete(entity);
	}
	
	
	@Override
	public Grupo carregarGrupo(Long id) throws RegistroInexistenteException {
		return repositorioGrupo.findById(id).orElseThrow( 
				() -> new RegistroInexistenteException("Não existe grupo com o id = " + id));
	}
}
