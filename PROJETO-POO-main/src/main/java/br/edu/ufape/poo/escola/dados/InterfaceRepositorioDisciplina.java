package br.edu.ufape.poo.escola.dados;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.edu.ufape.poo.escola.negocio.basica.Disciplina;

@Repository
public interface InterfaceRepositorioDisciplina extends JpaRepository<Disciplina, Long> {

	Disciplina findByNome(String nome);
}
