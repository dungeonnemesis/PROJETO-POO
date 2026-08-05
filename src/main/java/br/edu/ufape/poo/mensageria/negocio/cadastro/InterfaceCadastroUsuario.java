package br.edu.ufape.poo.mensageria.negocio.cadastro;

import java.util.List;

import br.edu.ufape.poo.mensageria.negocio.basica.Usuario;

public interface InterfaceCadastroUsuario {

	Usuario salvarUsuario(Usuario novo);

	Usuario atualizarUsuario(Usuario novo);

	List<Usuario> listarUsuarios();

	Usuario carregarUsuario(Long id) throws RegistroInexistenteException;

	void apagarUsuario(Usuario usuario);

}