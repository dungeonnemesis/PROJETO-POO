package br.edu.ufape.poo.escola.dados;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.ufape.poo.escola.negocio.basica.Justificativa;

public interface InterfaceRepositorioJustificativa extends JpaRepository<Justificativa, Long> {

	boolean existsByFrequenciaId(Long frequenciaId);

	Justificativa findByFrequenciaId(Long frequenciaId);
}
