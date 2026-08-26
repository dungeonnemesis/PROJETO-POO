package br.edu.ufape.poo.escola.dados;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.edu.ufape.poo.escola.negocio.basica.Pessoa;

@Repository
public interface InterfaceRepositorioPessoa extends JpaRepository<Pessoa, Long> {

	Pessoa findByCpf(String cpf);

	Pessoa findByEmail(String email);
}
