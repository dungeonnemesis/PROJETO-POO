package br.edu.ufape.poo.mensageria.dados;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.edu.ufape.poo.mensageria.negocio.basica.Grupo;

@Repository
public interface InterfaceRepositorioGrupo
	extends JpaRepository<Grupo, Long>{
	
	public Grupo findByNomeIgnoreCase(String nome);

}
