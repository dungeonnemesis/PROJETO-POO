package br.edu.ufape.poo.escola.dados;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.edu.ufape.poo.escola.negocio.basica.Aluno;

@Repository
public interface InterfaceRepositorioAluno extends JpaRepository<Aluno, Long> {

	Aluno findByMatricula(String matricula);
}
