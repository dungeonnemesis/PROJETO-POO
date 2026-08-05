package br.edu.ufape.poo.mensageria.dados;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.edu.ufape.poo.mensageria.negocio.basica.Usuario;

@Repository
public interface InterfaceRepositorioUsuario 
	extends JpaRepository<Usuario, Long>{
	
	Usuario findByEmail(String email);
}
