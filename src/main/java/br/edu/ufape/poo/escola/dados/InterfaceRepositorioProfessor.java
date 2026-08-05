package br.edu.ufape.poo.escola.dados;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.edu.ufape.poo.escola.negocio.basica.Professor;

@Repository
public interface InterfaceRepositorioProfessor extends JpaRepository<Professor, Long> {

	List<Professor> findByEspecialidadeIgnoreCase(String especialidade);
}
