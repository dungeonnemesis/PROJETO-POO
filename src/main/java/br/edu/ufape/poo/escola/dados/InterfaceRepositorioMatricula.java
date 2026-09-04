package br.edu.ufape.poo.escola.dados;

import org.springframework.data.jpa.repository.JpaRepository;
import br.edu.ufape.poo.escola.negocio.basica.Matricula;

public interface InterfaceRepositorioMatricula extends JpaRepository<Matricula, Long> {
	boolean existsByAlunoIdAndTurmaId(Long alunoId, Long turmaId);
}
