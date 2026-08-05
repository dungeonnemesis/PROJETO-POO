package br.edu.ufape.poo.mensageria.negocio.cadastro;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.ufape.poo.mensageria.dados.InterfaceRepositorioMensagemPrivada;
import br.edu.ufape.poo.mensageria.negocio.basica.MensagemPrivada;

@Service
public class CadastroMensagemPrivada implements InterfaceCadastroMensagemPrivada {
	@Autowired
	private InterfaceRepositorioMensagemPrivada repositorioMensagemPrivada;

	@Override
	public List<MensagemPrivada> listarMensagemPrivadaPorRemetente(Long id) {
		return repositorioMensagemPrivada.findByRemetente_id(id);
	}

	@Override
	public List<MensagemPrivada> listarMensagemPrivadaPorDestinatario(Long id) {
		return repositorioMensagemPrivada.findByDestinatario_id(id);
	}

	@Override
	public MensagemPrivada salvarMensagem(MensagemPrivada entity) {
		return repositorioMensagemPrivada.save(entity);
	}

	@Override
	public MensagemPrivada carregarMensagem(Long id) throws RegistroInexistenteException {
		return repositorioMensagemPrivada.findById(id).orElseThrow( 
				() -> new RegistroInexistenteException("Não existe usuário com o id = " + id));

	}

	@Override
	public void apagarMensagemPrivada(MensagemPrivada entity) {
		repositorioMensagemPrivada.delete(entity);
	}
	
	

}
