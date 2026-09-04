# Diagrama de Classes

Diagrama baseado no modelo atual do sistema escolar.

```mermaid
classDiagram
    class Pessoa {
        <<abstract>>
        -Long id
        -String nome
        -String cpf
        -String email
        +String exibirDados()
    }

    class Aluno {
        -String matricula
        +String exibirDados()
    }

    class Professor {
        -String especialidade
        +String exibirDados()
    }

    class Disciplina {
        -Long id
        -String nome
        -Integer cargaHoraria
    }

    class Turma {
        -Long id
        -String nome
        -Integer ano
        -String turno
        -List~TurmaDisciplina~ grade
    }

    class TurmaDisciplina {
        -Long id
        -Turma turma
        -Disciplina disciplina
        -Professor professor
    }

    class Matricula {
        -Long id
        -LocalDate data
        -String status
        -Aluno aluno
        -Turma turma
        -TurmaDisciplina grade legado opcional
    }

    class Nota {
        -Long id
        -Double valor
        -Matricula matricula
        -TurmaDisciplina grade
    }

    Pessoa <|-- Aluno
    Pessoa <|-- Professor

    Turma "1" *-- "0..*" TurmaDisciplina : possui grade
    Disciplina "1" <-- "0..*" TurmaDisciplina : compoe
    Professor "1" <-- "0..*" TurmaDisciplina : ministra

    Aluno "1" <-- "0..*" Matricula : aluno
    Turma "1" <-- "0..*" Matricula : turma
    TurmaDisciplina "0..1" <-- "0..*" Matricula : vinculo legado

    Matricula "1" <-- "0..*" Nota : recebe
    TurmaDisciplina "0..1" <-- "0..*" Nota : disciplina avaliada
```

## Fluxo do domínio

1. `Turma` é criada com nome, ano e turno.
2. `TurmaDisciplina` vincula uma disciplina e um professor à turma.
3. `Matricula` vincula um aluno à turma.
4. `Nota` registra o valor da avaliação para uma matrícula em uma disciplina da grade.

## Principais operações

- `POST /api/turmas`: cria uma turma básica.
- `POST /api/turmas/{turmaId}/grade`: vincula disciplina e professor à turma.
- `GET /api/turmas/{turmaId}/grade`: lista a grade da turma.
- `POST /api/matriculas`: matricula aluno na turma.
- `POST /api/notas`: registra uma nota individual.
- `POST /api/notas/lote`: registra várias notas de uma turma e disciplina.
