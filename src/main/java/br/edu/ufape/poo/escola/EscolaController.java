package br.edu.ufape.poo.escola;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ufape.poo.escola.dados.InterfaceRepositorioDisciplina;
import br.edu.ufape.poo.escola.dados.InterfaceRepositorioTurma;
import br.edu.ufape.poo.escola.negocio.basica.Aluno;
import br.edu.ufape.poo.escola.negocio.basica.Disciplina;
import br.edu.ufape.poo.escola.negocio.basica.Turma;
import br.edu.ufape.poo.escola.negocio.fachada.EscolaFacade;

// Controller legado mantido apenas como referência. A API atual usa os
// controllers tipados do pacote comunicacao.
public class EscolaController {

    private final EscolaFacade escolaFacade;
    private final InterfaceRepositorioDisciplina repositorioDisciplina;
    private final InterfaceRepositorioTurma repositorioTurma;

    public EscolaController(EscolaFacade escolaFacade,
            InterfaceRepositorioDisciplina repositorioDisciplina,
            InterfaceRepositorioTurma repositorioTurma) {
        this.escolaFacade = escolaFacade;
        this.repositorioDisciplina = repositorioDisciplina;
        this.repositorioTurma = repositorioTurma;
    }

    @GetMapping("/alunos")
    public List<Aluno> listarAlunos() {
        return escolaFacade.buscarTodosAlunos();
    }

    @PostMapping("/alunos")
    public ResponseEntity<Aluno> criarAluno(@RequestBody Aluno aluno) {
        Aluno salvo = escolaFacade.criarAluno(aluno);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }

    @GetMapping("/disciplinas")
    public List<Disciplina> listarDisciplinas() {
        return escolaFacade.buscarTodasDisciplinas();
    }

    @PostMapping("/disciplinas")
    public ResponseEntity<Disciplina> criarDisciplina(@RequestBody Disciplina disciplina) {
        Disciplina salvo = escolaFacade.criarDisciplina(disciplina);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }

    @GetMapping("/turmas")
    public List<Turma> listarTurmas() {
        return repositorioTurma.findAll();
    }

    @PostMapping("/turmas")
    public ResponseEntity<Turma> criarTurma(@RequestBody Turma turma) {
        Disciplina disciplina = turma.getDisciplina();

        if (disciplina == null || disciplina.getNome() == null || disciplina.getNome().isBlank()) {
            throw new IllegalArgumentException("A disciplina da turma é obrigatória.");
        }

        Disciplina disciplinaExistente = repositorioDisciplina.findByNome(disciplina.getNome());
        if (disciplinaExistente == null) {
            disciplinaExistente = escolaFacade.criarDisciplina(disciplina);
        }

        turma.setDisciplina(disciplinaExistente);
        Turma salvo = escolaFacade.criarTurma(turma);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }
}
