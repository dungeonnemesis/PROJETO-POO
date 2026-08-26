const apiBase = '/api';

async function fetchJson(url, options = {}) {
  const response = await fetch(url, {
    headers: { 'Content-Type': 'application/json' },
    ...options
  });

  const data = await response.text();
  const payload = data ? JSON.parse(data) : null;

  if (!response.ok) {
    throw new Error(payload?.message || 'Erro na requisição');
  }

  return payload;
}

function renderEmptyState(lista, mensagem) {
  lista.innerHTML = `<li class="empty">${mensagem}</li>`;
}

async function carregarAlunos() {
  const lista = document.getElementById('listaAlunos');
  try {
    const alunos = await fetchJson(`${apiBase}/alunos`);
    lista.innerHTML = alunos.length
      ? alunos.map(aluno => `<li><strong>${aluno.nome}</strong><br>${aluno.matricula} • ${aluno.email}</li>`).join('')
      : renderEmptyState(lista, 'Nenhum aluno cadastrado.');
  } catch (error) {
    lista.innerHTML = `<li class="empty">Erro: ${error.message}</li>`;
  }
}

async function carregarDisciplinas() {
  const lista = document.getElementById('listaDisciplinas');
  try {
    const disciplinas = await fetchJson(`${apiBase}/disciplinas`);
    lista.innerHTML = disciplinas.length
      ? disciplinas.map(disciplina => `<li><strong>${disciplina.nome}</strong><br>${disciplina.cargaHoraria}h de carga horária</li>`).join('')
      : renderEmptyState(lista, 'Nenhuma disciplina cadastrada.');
  } catch (error) {
    lista.innerHTML = `<li class="empty">Erro: ${error.message}</li>`;
  }
}

async function carregarTurmas() {
  const lista = document.getElementById('listaTurmas');
  try {
    const turmas = await fetchJson(`${apiBase}/turmas`);
    lista.innerHTML = turmas.length
      ? turmas.map(turma => `<li><strong>${turma.nome}</strong><br>${turma.ano} • ${turma.disciplina?.nome || 'Sem disciplina'}</li>`).join('')
      : renderEmptyState(lista, 'Nenhuma turma cadastrada.');
  } catch (error) {
    lista.innerHTML = `<li class="empty">Erro: ${error.message}</li>`;
  }
}

document.getElementById('formAluno').addEventListener('submit', async (event) => {
  event.preventDefault();
  const status = document.getElementById('statusAluno');
  const payload = {
    nome: document.getElementById('nomeAluno').value,
    cpf: document.getElementById('cpfAluno').value,
    email: document.getElementById('emailAluno').value,
    matricula: document.getElementById('matriculaAluno').value
  };

  try {
    await fetchJson(`${apiBase}/alunos`, {
      method: 'POST',
      body: JSON.stringify(payload)
    });
    status.textContent = 'Aluno salvo com sucesso!';
    event.target.reset();
    carregarAlunos();
  } catch (error) {
    status.textContent = error.message;
  }
});

document.getElementById('formDisciplina').addEventListener('submit', async (event) => {
  event.preventDefault();
  const status = document.getElementById('statusDisciplina');
  const payload = {
    nome: document.getElementById('nomeDisciplina').value,
    cargaHoraria: Number(document.getElementById('cargaHoraria').value)
  };

  try {
    await fetchJson(`${apiBase}/disciplinas`, {
      method: 'POST',
      body: JSON.stringify(payload)
    });
    status.textContent = 'Disciplina salva com sucesso!';
    event.target.reset();
    carregarDisciplinas();
  } catch (error) {
    status.textContent = error.message;
  }
});

document.getElementById('formTurma').addEventListener('submit', async (event) => {
  event.preventDefault();
  const status = document.getElementById('statusTurma');
  const payload = {
    nome: document.getElementById('nomeTurma').value,
    ano: Number(document.getElementById('anoTurma').value),
    disciplina: {
      nome: document.getElementById('disciplinaTurma').value,
      cargaHoraria: 0
    }
  };

  try {
    await fetchJson(`${apiBase}/turmas`, {
      method: 'POST',
      body: JSON.stringify(payload)
    });
    status.textContent = 'Turma salva com sucesso!';
    event.target.reset();
    carregarTurmas();
  } catch (error) {
    status.textContent = error.message;
  }
});

carregarAlunos();
carregarDisciplinas();
carregarTurmas();
