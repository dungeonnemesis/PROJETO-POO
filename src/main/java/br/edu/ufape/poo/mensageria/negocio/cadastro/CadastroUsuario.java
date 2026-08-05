package br.edu.ufape.poo.mensageria.negocio.cadastro;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.ufape.poo.mensageria.dados.InterfaceRepositorioUsuario;
import br.edu.ufape.poo.mensageria.negocio.basica.Usuario;

@Service
public class CadastroUsuario implements InterfaceCadastroUsuario {
	@Autowired
	private InterfaceRepositorioUsuario repositorioUsuario;

	@Override
	public Usuario salvarUsuario(Usuario novo) {
		return repositorioUsuario.save(novo);
	}
	
	@Override
	public Usuario atualizarUsuario(Usuario novo) {
		return repositorioUsuario.save(novo);
	}

	@Override
	public List<Usuario> listarUsuarios() {
		return repositorioUsuario.findAll();
	}

	@Override
	public Usuario carregarUsuario(Long id) throws RegistroInexistenteException {
		return repositorioUsuario.findById(id).orElseThrow( 
				() -> new RegistroInexistenteException("Não existe usuário com o id = " + id));
	}

	@Override
	public void apagarUsuario(Usuario usuario) {
		repositorioUsuario.delete(usuario);
	}
	
	
	

}
