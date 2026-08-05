package br.edu.ufape.poo.mensageria.dados;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.ufape.poo.mensageria.negocio.basica.MensagemGrupo;

public interface InterfaceRepositorioMensagemGrupo
	extends JpaRepository<MensagemGrupo, Long>{
	
	List<MensagemGrupo> findByRemetente_id(Long id);

}
