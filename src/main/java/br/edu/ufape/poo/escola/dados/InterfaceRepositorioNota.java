package br.edu.ufape.poo.escola.dados;

import org.springframework.data.jpa.repository.JpaRepository;
import br.edu.ufape.poo.escola.negocio.basica.Nota;

public interface InterfaceRepositorioNota extends JpaRepository<Nota, Long> {
}
