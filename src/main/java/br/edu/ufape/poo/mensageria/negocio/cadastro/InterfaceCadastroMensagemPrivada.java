package br.edu.ufape.poo.mensageria.negocio.cadastro;

import java.util.List;

import br.edu.ufape.poo.mensageria.negocio.basica.MensagemPrivada;

public interface InterfaceCadastroMensagemPrivada {

	List<MensagemPrivada> listarMensagemPrivadaPorRemetente(Long id);

	List<MensagemPrivada> listarMensagemPrivadaPorDestinatario(Long id);

	MensagemPrivada salvarMensagem(MensagemPrivada entity);

	MensagemPrivada carregarMensagem(Long id) throws RegistroInexistenteException;

	void apagarMensagemPrivada(MensagemPrivada entity);

}