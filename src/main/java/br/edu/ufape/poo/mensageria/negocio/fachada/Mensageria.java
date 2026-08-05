package br.edu.ufape.poo.mensageria.negocio.fachada;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.ufape.poo.mensageria.negocio.basica.Grupo;
import br.edu.ufape.poo.mensageria.negocio.basica.MensagemPrivada;
import br.edu.ufape.poo.mensageria.negocio.basica.Usuario;
import br.edu.ufape.poo.mensageria.negocio.cadastro.InterfaceCadastroGrupo;
import br.edu.ufape.poo.mensageria.negocio.cadastro.InterfaceCadastroMensagemPrivada;
import br.edu.ufape.poo.mensageria.negocio.cadastro.InterfaceCadastroUsuario;
import br.edu.ufape.poo.mensageria.negocio.cadastro.RegistroDuplicadoException;
import br.edu.ufape.poo.mensageria.negocio.cadastro.RegistroInexistenteException;

@Service
public class Mensageria {
	@Autowired
	private InterfaceCadastroGrupo cadastroGrupo;
	@Autowired
	private InterfaceCadastroUsuario cadastroUsuario;
	@Autowired
	private InterfaceCadastroMensagemPrivada cadastroMensagemPrivada;
	
	
	public void ingressarEmUmGrupo(Long idUsuario, Long idGrupo) 
			throws RegistroInexistenteException {
		Grupo g = cadastroGrupo.carregarGrupo(idGrupo);
		Usuario u = cadastroUsuario.carregarUsuario(idUsuario);
		u.inscreverGrupo(g);
		cadastroUsuario.atualizarUsuario(u);
	}
	
	public void salvarGrupo(Grupo novo) throws RegistroDuplicadoException {
		cadastroGrupo.salvarGrupo(novo);
	}
	public void atualizarGrupo(Grupo novo) throws RegistroDuplicadoException {
		cadastroGrupo.atualizarGrupo(novo);
	}
	public List<Grupo> listarTodosGrupos() {
		return cadastroGrupo.listarTodosGrupos();
	}
	public Grupo procurarGrupoPorNome(String nome) throws RegistroInexistenteException {
		return cadastroGrupo.procurarGrupoPorNome(nome);
	}
	public void apagarGrupo(Grupo entity) {
		cadastroGrupo.apagarGrupo(entity);
	}
	public Grupo carregarGrupo(Long id) throws RegistroInexistenteException {
		return cadastroGrupo.carregarGrupo(id);
	}
	public Usuario salvarUsuario(Usuario novo) {
		return cadastroUsuario.salvarUsuario(novo);
	}
	public Usuario atualizarUsuario(Usuario novo) {
		return cadastroUsuario.atualizarUsuario(novo);
	}
	public List<Usuario> listarUsuarios() {
		return cadastroUsuario.listarUsuarios();
	}
	public Usuario carregarUsuario(Long id) throws RegistroInexistenteException {
		return cadastroUsuario.carregarUsuario(id);
	}
	public void apagarUsuario(Usuario usuario) {
		cadastroUsuario.apagarUsuario(usuario);
	}
	public List<MensagemPrivada> listarMensagemPrivadaPorRemetente(Long id) {
		return cadastroMensagemPrivada.listarMensagemPrivadaPorRemetente(id);
	}
	public List<MensagemPrivada> listarMensagemPrivadaPorDestinatario(Long id) {
		return cadastroMensagemPrivada.listarMensagemPrivadaPorDestinatario(id);
	}
	public MensagemPrivada salvarMensagem(MensagemPrivada entity) {
		return cadastroMensagemPrivada.salvarMensagem(entity);
	}
	public MensagemPrivada carregarMensagem(Long id) throws RegistroInexistenteException {
		return cadastroMensagemPrivada.carregarMensagem(id);
	}
	public void apagarMensagemPrivada(MensagemPrivada entity) {
		cadastroMensagemPrivada.apagarMensagemPrivada(entity);
	}
	
	
	
	

}
