package br.edu.ufape.poo.mensageria.dados;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.ufape.poo.mensageria.negocio.basica.MensagemPrivada;

public interface InterfaceRepositorioMensagemPrivada 
	extends JpaRepository<MensagemPrivada, Long>{
	
	List<MensagemPrivada> findByRemetente_id(Long id);
	List<MensagemPrivada> findByDestinatario_id(Long id);

}
