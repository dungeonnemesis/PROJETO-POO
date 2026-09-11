package br.edu.ufape.poo.escola.dados;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.ufape.poo.escola.negocio.basica.Frequencia;

public interface InterfaceRepositorioFrequencia extends JpaRepository<Frequencia, Long> {

	boolean existsByMatriculaIdAndGradeIdAndData(Long matriculaId, Long gradeId, LocalDate data);

	List<Frequencia> findByMatriculaId(Long matriculaId);

	List<Frequencia> findByGradeIdAndData(Long gradeId, LocalDate data);
}
