package br.edu.ufape.poo.mensageria.negocio.cadastro;

import java.util.List;

import br.edu.ufape.poo.mensageria.negocio.basica.Grupo;

public interface InterfaceCadastroGrupo {

	void salvarGrupo(Grupo novo) throws RegistroDuplicadoException;

	void atualizarGrupo(Grupo novo) throws RegistroDuplicadoException;

	List<Grupo> listarTodosGrupos();

	Grupo procurarGrupoPorNome(String nome) throws RegistroInexistenteException;

	void apagarGrupo(Grupo entity);

	Grupo carregarGrupo(Long id) throws RegistroInexistenteException;


}