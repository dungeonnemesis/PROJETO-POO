const resources = {
  disciplinas:{label:'Disciplinas',singular:'disciplina',fields:[['nome','Nome','text'],['cargaHoraria','Carga horária','number']]},
  alunos:{label:'Alunos',singular:'aluno',fields:[['nome','Nome','text'],['cpf','CPF','text'],['email','E-mail','email'],['matricula','Código de matrícula','text']]},
  professores:{label:'Professores',singular:'professor',fields:[['nome','Nome','text'],['cpf','CPF','text'],['email','E-mail','email'],['especialidade','Especialidade','text']]},
  turmas:{label:'Turmas',singular:'turma',fields:[['nome','Nome','text'],['ano','Ano','number'],['disciplinaId','ID da disciplina','number'],['professorId','ID do professor','number']]},
  matriculas:{label:'Matrículas',singular:'matrícula',fields:[['data','Data','date'],['status','Status','select',['ATIVA','TRANCADA','CANCELADA']],['alunoId','ID do aluno','number'],['turmaId','ID da turma','number']]},
  notas:{label:'Notas',singular:'nota',fields:[['valor','Valor (0 a 10)','number'],['matriculaId','ID da matrícula','number']]}
};
let current='disciplinas';
const $=id=>document.getElementById(id);

function init(){
  Object.entries(resources).forEach(([key,r],i)=>{const b=document.createElement('button');b.textContent=r.label;b.dataset.key=key;b.onclick=()=>select(key,i+1);$('tabs').appendChild(b)});
  $('refresh').onclick=load; select(current,1);
}
function select(key,n){current=key;document.querySelectorAll('#tabs button').forEach(b=>b.classList.toggle('active',b.dataset.key===key));$('resource-number').textContent=String(n).padStart(2,'0');$('form-title').textContent=`Nova ${resources[key].singular}`;$('list-title').textContent=resources[key].label;buildForm();load()}
function buildForm(){const form=$('resource-form');form.innerHTML='';resources[current].fields.forEach(([name,label,type,options])=>{const l=document.createElement('label');l.htmlFor=name;l.textContent=label;form.appendChild(l);let input;if(type==='select'){input=document.createElement('select');options.forEach(v=>input.add(new Option(v,v)))}else{input=document.createElement('input');input.type=type;if(type==='number'){input.step=name==='valor'?'0.1':'1';input.min='0'}if(type==='date')input.value=new Date().toISOString().slice(0,10)}input.id=name;input.name=name;input.required=true;form.appendChild(input)});const b=document.createElement('button');b.className='primary';b.type='submit';b.textContent='Salvar registro';form.appendChild(b);form.onsubmit=save}
async function save(e){e.preventDefault();clearMessage();const data={};resources[current].fields.forEach(([name,,type])=>{const value=$(name).value;data[name]=type==='number'?Number(value):value});try{const res=await fetch(`/api/${current}`,{method:'POST',headers:{'Content-Type':'application/json'},body:JSON.stringify(data)});const body=await res.json().catch(()=>({}));if(!res.ok)throw new Error(body.mensagem||'Não foi possível salvar');showMessage('Registro salvo com sucesso.','ok');e.target.reset();load()}catch(err){showMessage(err.message,'error')}}
async function load(){const area=$('records');area.innerHTML='<div class="empty">Carregando...</div>';try{const res=await fetch(`/api/${current}`);if(!res.ok)throw new Error('Falha ao consultar a API');const list=await res.json();$('list-count').textContent=`${list.length} registro(s)`;area.innerHTML=list.length?'':'<div class="empty">Nenhum registro cadastrado.</div>';list.forEach(item=>area.appendChild(render(item)))}catch(err){area.innerHTML=`<div class="empty">${err.message}</div>`;$('status').textContent='API indisponível'}}
function render(item){const row=document.createElement('div');row.className='record';const text=document.createElement('div');const summary=Object.entries(item).filter(([k])=>k!=='id').map(([k,v])=>`${pretty(k)}: ${typeof v==='object'?(v?.nome||v?.id||'-'):v}`).join(' · ');text.innerHTML=`<strong>#${item.id} ${item.nome||item.aluno||resources[current].singular}</strong><small>${summary}</small>`;const del=document.createElement('button');del.className='delete';del.textContent='Excluir';del.onclick=()=>remove(item.id);row.append(text,del);return row}
async function remove(id){if(!confirm(`Excluir o registro #${id}?`))return;const res=await fetch(`/api/${current}/${id}`,{method:'DELETE'});if(res.ok){showMessage('Registro excluído.','ok');load()}else showMessage('Não foi possível excluir. Verifique os vínculos.','error')}
function pretty(k){return k.replace(/([A-Z])/g,' $1').replace(/^./,c=>c.toUpperCase())}
function showMessage(text,type){clearMessage();const d=document.createElement('div');d.id='form-message';d.className=`message ${type}`;d.textContent=text;$('resource-form').appendChild(d)}
function clearMessage(){$('form-message')?.remove()}
init();
