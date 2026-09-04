package br.edu.ufape.poo.escola.dados;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import br.edu.ufape.poo.escola.negocio.basica.TurmaDisciplina;

@Repository
public interface InterfaceRepositorioTurmaDisciplina extends JpaRepository<TurmaDisciplina, Long> {
	List<TurmaDisciplina> findByTurmaId(Long turmaId);
	boolean existsByTurmaIdAndDisciplinaId(Long turmaId, Long disciplinaId);
}
