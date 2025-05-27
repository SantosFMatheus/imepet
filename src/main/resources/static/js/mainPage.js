document.addEventListener("DOMContentLoaded", () => {
    // Botões menu dinâmicos (toggle active)
    const links = document.querySelectorAll(".menu-link");
    links.forEach(link => {
        link.addEventListener("click", (e) => {
            e.preventDefault();
            links.forEach(l => l.classList.remove("active"));
            link.classList.add("active");
        });
    });

    // Destacar linha da tabela ao clicar
    const tabelas = document.querySelectorAll('.table-cadastro tbody');
    tabelas.forEach(tbody => {
        tbody.addEventListener('click', (event) => {
            let tr = event.target.closest('tr');
            if (!tr) return;

            tbody.querySelectorAll('tr').forEach(row => row.classList.remove('highlighted'));
            tr.classList.add('highlighted');
        });
    });

    const botaoFiltrar = document.getElementById("botao-filtrar-nome");
            if (botaoFiltrar) {
                botaoFiltrar.addEventListener("click", () => {
                    const nome = document.getElementById("filtro-nome-tutor").value;
                    carregarTutoresResumidos(nome);
                });
    }

    // Carregar tutores resumidos via fetch
    carregarTutoresResumidos();

        const botaoAprovado = document.getElementById("button-aprovado");
        if (botaoAprovado) {
            botaoAprovado.addEventListener("click", () => {
                carregarTutoresResumidos('', 'APROVADO');
            });
        }

        const botaoReprovado = document.getElementById("button-reprovado");
        if (botaoReprovado) {
            botaoReprovado.addEventListener("click", () => {
                carregarTutoresResumidos('', 'REPROVADO');
            });
        }

});

// Função para mostrar seção conforme menu clicado
function mostrarSecao(id, link) {
    const secaoInicial = document.getElementById("initial-section");
    secaoInicial.classList.add('hidden');

    const secoes = document.querySelectorAll('.table-section');
    secoes.forEach(secao => secao.classList.add('hidden'));

    const links = document.querySelectorAll('.menu-link');
    links.forEach(el => el.classList.remove('active'));

    const secaoAlvo = document.getElementById(id);
    if (secaoAlvo) {
        secaoAlvo.classList.remove('hidden');
    }

    if (link) {
        link.classList.add('active');
    }

    // Oculta ou mostra os botões de deletar/editar com base na seção ativa
    const btnEditar = document.getElementById('btn-editar');
    const btnRelatorio = document.getElementById('btn-relatorio');

    if (id === 'administradores-section') {
        if (btnEditar) btnEditar.classList.add('hidden');
        if (btnRelatorio) btnRelatorio.classList.add('hidden');
    } else {
        if (btnEditar) btnEditar.classList.remove('hidden');
        if (btnRelatorio) btnRelatorio.classList.remove('hidden');
    }

    window.secaoAtual = id;
}


// Função abrir popup dependendo da seção atual
function abrirPopup() {
    let url = '#';

    switch (window.secaoAtual) {
        case 'visao-geral-section':
            url = '/tutores/novo';
            break;
        case 'administradores-section':
            url = '/usuarios/novo';
            break;
        default:
            alert('Selecione uma seção antes de adicionar!');
            return;
    }

    window.open(
        url,
        'popupWindow',
        'width=600,height=1200,scrollbars=yes'
    );
}

function carregarTutoresResumidos(nome = '', status = '') {
    let url = '/tutores/resumidos';
    const params = [];

    if (nome) params.push(`nome=${encodeURIComponent(nome)}`);
    if (status) params.push(`status=${encodeURIComponent(status)}`);

    if (params.length > 0) {
        url += '?' + params.join('&');
    }

    fetch(url)
        .then(response => response.json())
        .then(tutores => {
            const tbody = document.getElementById('tabela-tutores-body');
            tbody.innerHTML = ''; // limpa tabela

            tutores.forEach(tutor => {
                const tr = document.createElement('tr');
                tr.innerHTML = `
                    <td>${tutor.id}</td>
                    <td>${formatarData(tutor.dataNascimento)}</td>
                    <td>${tutor.nome}</td>
                    <td>${tutor.status}</td>
                    <td>${tutor.cpf}</td>
                    <td>${tutor.rg}</td>
                    <td>${tutor.celular}</td>
                `;
                tbody.appendChild(tr);
            });
        })
        .catch(error => {
            console.error('Erro ao carregar tutores:', error);
        });
}


function formatarData(dataISO) {
    const data = new Date(dataISO);
    return isNaN(data) ? '' : data.toLocaleDateString('pt-BR');
}

function editarTutorSelecionado() {
    const linhaSelecionada = document.querySelector('#tabela-tutores-body tr.highlighted');
    if (!linhaSelecionada) {
        alert("Por favor, selecione uma linha da tabela antes de editar.");
        return;
    }

    const id = linhaSelecionada.children[0].textContent.trim();
    abrirPopupEdicao(id);
}

function abrirPopupEdicao(id) {
    const url = `/tutores/editar/${id}`;
    window.open(
        url,
        'popupEdicao',
        'width=600,height=1200,scrollbars=yes'
    );
}

function exportarTabela() {
    // Seleciona a tabela pelo seletor da classe
    const tabela = document.querySelector(".table-cadastro");

    if (!tabela) {
        console.warn("Tabela não encontrada!");
        return;
    }

    // Converte a tabela para um workbook
    const workbook = XLSX.utils.table_to_book(tabela, {
        sheet: "Tutores"
    });

    // Salva o arquivo
    XLSX.writeFile(workbook, 'tabela_tutores.xlsx');
}

const botaoFiltrarAdmins = document.querySelector('#administradores-section .filter-div button');
if (botaoFiltrarAdmins) {
    botaoFiltrarAdmins.addEventListener('click', () => {
        const nomeAdmin = document.querySelector('#administradores-section .filter-div input').value;
        carregarAdminsResumidos(nomeAdmin);
    });
}


// Carrega a tabela de administradores inicialmente (sem filtro)
carregarAdminsResumidos();

function carregarAdminsResumidos(nome = '') {
    let url = '/usuarios/resumidos';  // ajuste para o endpoint correto
    if (nome) {
        url += `?nome=${encodeURIComponent(nome)}`;
    }

    fetch(url)
        .then(response => response.json())
        .then(admins => {
            const tbody = document.querySelector('#administradores-section table.table-cadastro tbody');
            tbody.innerHTML = ''; // limpa tabela

            admins.forEach(admin => {
                const tr = document.createElement('tr');
                tr.innerHTML = `
                    <td>#${admin.id}</td>
                    <td>${formatarData(admin.dataCadastro)}</td>
                    <td>${admin.nome}</td>
                    <td>${admin.usuario}</td>
                    <td>${admin.emailResponsavel}</td>
                    <td>${admin.telefone}</td>
                `;
                tbody.appendChild(tr);
            });
        })
        .catch(error => {
            console.error('Erro ao carregar administradores:', error);
        });
}

// Destacar linha da tabela ao clicar na tabela administradores
const adminTbody = document.querySelector('#administradores-section table.table-cadastro tbody');
if (adminTbody) {
    adminTbody.addEventListener('click', (event) => {
        let tr = event.target.closest('tr');
        if (!tr) return;

        adminTbody.querySelectorAll('tr').forEach(row => row.classList.remove('highlighted'));
        tr.classList.add('highlighted');
    });
}



