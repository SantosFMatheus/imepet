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
            window.carregarTutoresResumidos(nome); // Chame a função global
        });
    }

    // Carregar tutores resumidos via fetch na carga inicial
    window.carregarTutoresResumidos(); // Chame a função global na inicialização

    const botaoAprovado = document.getElementById("button-aprovado");
    if (botaoAprovado) {
        botaoAprovado.addEventListener("click", () => {
            window.carregarTutoresResumidos('', 'APROVADO'); // Chame a função global
        });
    }

    const botaoReprovado = document.getElementById("button-reprovado");
    if (botaoReprovado) {
        botaoReprovado.addEventListener("click", () => {
            window.carregarTutoresResumidos('', 'REPROVADO'); // Chame a função global
        });
    }

    const btnDeletar = document.getElementById("btn-deletar");

    btnDeletar.addEventListener("click", function (e) {
        e.preventDefault();

        const linhaSelecionada = document.querySelector(".table-cadastro tbody tr.highlighted");
        if (!linhaSelecionada) {
            alert("Selecione um item para deletar.");
            return;
        }

        const idRaw = linhaSelecionada.querySelector("td")?.innerText.trim();
        const id = idRaw?.replace(/^#/, ''); // remove # se existir

        if (!id || isNaN(id)) {
            alert("ID inválido.");
            return;
        }

        let url = '';
        let entidade = '';

        switch (window.secaoAtual) {
            case 'visao-geral-section':
                url = '/tutores/deletar';
                entidade = 'tutor';
                break;
            case 'administradores-section':
                url = '/usuarios/deletar';
                entidade = 'administrador';
                break;
            default:
                alert('Seção inválida ou não suportada para deletar.');
                return;
        }

        // ⚠️ Verifica se o nome é "admin"
        const nome = linhaSelecionada.children[2]?.textContent.trim().toLowerCase();
        if (entidade === 'administrador' && nome === 'administrador do sistema') {
            alert("O administrador principal não pode ser excluído.");
            return;
        }

        if (!confirm(`Tem certeza que deseja deletar o ${entidade} com ID ${id}?`)) return;

        fetch(url, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded',
            },
            body: `id=${encodeURIComponent(id)}`
        })
            .then(response => {
                if (response.ok) {
                    linhaSelecionada.remove();
                    alert(`${entidade.charAt(0).toUpperCase() + entidade.slice(1)} ${id} deletado com sucesso.`);
                    // Recarregar a tabela após a exclusão
                    if (window.secaoAtual === 'visao-geral-section') {
                        window.carregarTutoresResumidos();
                    } else if (window.secaoAtual === 'administradores-section') {
                        window.carregarAdminsResumidos();
                    }
                } else {
                    alert(`Erro ao deletar ${entidade}.`);
                }
            })
            .catch(error => {
                console.error(`Erro ao deletar ${entidade}:`, error);
                alert("Erro de rede.");
            });
    });

}); // Fim do DOMContentLoaded

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
        // Ao mudar para a seção de administradores, carregue os admins
        window.carregarAdminsResumidos();
    } else {
        if (btnEditar) btnEditar.classList.remove('hidden');
        if (btnRelatorio) btnRelatorio.classList.remove('hidden');
        // **NOVO: Ao mudar para a seção de Visão Geral (ou qualquer outra que mostre tutores), recarregue os tutores**
        if (id === 'visao-geral-section') {
            window.carregarTutoresResumidos(); // Recarrega os dados dos tutores
        }
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

// Torne a função global para que possa ser acessada de outras janelas (popups)
window.carregarTutoresResumidos = function(nome = '', status = '') {
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
                    <td class="status ${tutor.status ? tutor.status.toLowerCase().replace(' ', '-') : ''}">${tutor.status}</td>
                    <td>${tutor.cpf}</td>
                    <td>${tutor.rg}</td>
                    <td>${tutor.celular}</td>
                `;
                tbody.appendChild(tr);
            });
        })
        .catch(error => {
            console.error('Erro ao carregar tutores:', error);
            // Adicione uma mensagem de erro na tabela para feedback ao usuário
            const tbody = document.getElementById('tabela-tutores-body');
            tbody.innerHTML = '<tr><td colspan="7">Erro ao carregar dados dos tutores.</td></tr>';
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

// NOVA FUNÇÃO EXPORTAR TABELA PARA INCLUIR TODOS OS DADOS
async function exportarTabela() {
    try {
        const response = await fetch('/tutores/todos-dados'); // Chama o novo endpoint
        if (!response.ok) {
            throw new Error(`Erro ao buscar dados: ${response.statusText}`);
        }
        const tutoresCompletos = await response.json();

        if (tutoresCompletos.length === 0) {
            alert("Não há dados de tutores para exportar.");
            return;
        }

        // Preparar os dados para a planilha
        const dadosPlanilha = [];

        // Cabeçalhos das colunas
        const cabecalhosTutor = [
            'ID', 'Nome', 'Celular', 'CPF', 'RG', 'Data Nascimento', 'Naturalidade',
            'Estado Civil', 'Nome Cônjuge', 'Tem Filhos', 'Qtde Filhos', 'CEP', 'Município',
            'UF', 'Rua', 'Número', 'Bairro', 'Telefone', 'Tipo Residência',
            'Situação Imóvel', 'Valor Aluguel Imóvel', 'Status'
        ];
        const cabecalhosSocioeconomicos = [
            'Situação Moradia', 'Moradia Coletiva Esp.', 'Valor Aluguel Moradia',
            'Outros Moradia Esp.', 'Local Trabalho Tutor', 'Valor Remuneração Tutor',
            'Tem Outras Fontes Renda', 'Valor Outras Fontes Renda', 'Tem Conta Bancária',
            'Nome Banco', 'Local Trabalho Pai', 'Local Trabalho Mãe',
            'Local Trabalho Cônjuge', 'Local Trabalho Filhos', 'Renda Pai',
            'Renda Mãe', 'Renda Cônjuge', 'Renda Familiar Total',
            'Nº Pessoas Grupo Familiar', 'Programa Social', 'Outro Programa Social',
            'Bens Familiares'
        ];

        dadosPlanilha.push([...cabecalhosTutor, ...cabecalhosSocioeconomicos]);

        // Preencher as linhas com os dados
        tutoresCompletos.forEach(tutorCompleto => {
            const tutor = tutorCompleto.tutor;
            const dadosSocioeconomicos = tutorCompleto.dadosSocioeconomicos;

            const linha = [
                tutor.id,
                tutor.nome,
                tutor.celular,
                tutor.cpf,
                tutor.rg,
                formatarData(tutor.dataNascimento), // Reutiliza sua função de formatação
                tutor.naturalidade,
                tutor.estadoCivil,
                tutor.nomeMarido,
                tutor.temFilhos,
                tutor.quantidadeFilhos,
                tutor.cep,
                tutor.municipio,
                tutor.uf,
                tutor.rua,
                tutor.numero,
                tutor.bairro,
                tutor.telefone,
                tutor.tipoResidencia,
                tutor.situacaoImovel,
                tutor.valorAluguel,
                tutor.status,
                // Dados Socioeconômicos (verificar se o objeto não é nulo antes de acessar)
                dadosSocioeconomicos ? dadosSocioeconomicos.situacaoMoradia : '',
                dadosSocioeconomicos ? dadosSocioeconomicos.moradiaColetivaEspecificacao : '',
                dadosSocioeconomicos ? dadosSocioeconomicos.valorAluguel : '',
                dadosSocioeconomicos ? dadosSocioeconomicos.outrosEspecificacao : '',
                dadosSocioeconomicos ? dadosSocioeconomicos.localTrabalho : '',
                dadosSocioeconomicos ? dadosSocioeconomicos.valorRemuneracao : '',
                dadosSocioeconomicos ? dadosSocioeconomicos.temOutrasFontesDeRenda : '',
                dadosSocioeconomicos ? dadosSocioeconomicos.valorOutrasFontesDeRenda : '',
                dadosSocioeconomicos ? dadosSocioeconomicos.temContaBancaria : '',
                dadosSocioeconomicos ? dadosSocioeconomicos.nomeBanco : '',
                dadosSocioeconomicos ? dadosSocioeconomicos.localTrabalhoPai : '',
                dadosSocioeconomicos ? dadosSocioeconomicos.localTrabalhoMae : '',
                dadosSocioeconomicos ? dadosSocioeconomicos.localTrabalhoConjuge : '',
                dadosSocioeconomicos ? dadosSocioeconomicos.localTrabalhoFilhos : '',
                dadosSocioeconomicos ? dadosSocioeconomicos.rendaPai : '',
                dadosSocioeconomicos ? dadosSocioeconomicos.rendaMae : '',
                dadosSocioeconomicos ? dadosSocioeconomicos.rendaConjuge : '',
                dadosSocioeconomicos ? dadosSocioeconomicos.valorTotalRemuneracao : '',
                dadosSocioeconomicos ? dadosSocioeconomicos.numeroPessoasGrupoFamiliar : '',
                dadosSocioeconomicos ? dadosSocioeconomicos.programaSocial : '',
                dadosSocioeconomicos ? dadosSocioeconomicos.outroProgramaSocial : '',
                dadosSocioeconomicos ? dadosSocioeconomicos.bensFamiliares : ''
            ];
            dadosPlanilha.push(linha);
        });

        // Criar a planilha e o arquivo Excel
        const ws = XLSX.utils.aoa_to_sheet(dadosPlanilha);
        const wb = XLSX.utils.book_new();
        XLSX.utils.book_append_sheet(wb, ws, "Dados Completos Tutores");
        XLSX.writeFile(wb, 'relatorio_completo_tutores.xlsx');

        alert("Relatório completo gerado com sucesso!");

    } catch (error) {
        console.error('Erro ao gerar relatório completo:', error);
        alert("Erro ao gerar relatório completo. Verifique o console para mais detalhes.");
    }
}

const botaoFiltrarAdmins = document.querySelector('#administradores-section .filter-div button');
if (botaoFiltrarAdmins) {
    botaoFiltrarAdmins.addEventListener('click', () => {
        const nomeAdmin = document.querySelector('#administradores-section .filter-div input').value;
        window.carregarAdminsResumidos(nomeAdmin); // Chame a função global
    });
}


// Carrega a tabela de administradores inicialmente (sem filtro)
window.carregarAdminsResumidos(); // Chame a função global na inicialização

// Torne carregarAdminsResumidos global também para consistência
window.carregarAdminsResumidos = function(nome = '') {
    let url = '/usuarios/resumidos';
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
            // Adicione uma mensagem de erro na tabela para feedback ao usuário
            const tbody = document.querySelector('#administradores-section table.table-cadastro tbody');
            tbody.innerHTML = '<tr><td colspan="6">Erro ao carregar dados dos administradores.</td></tr>';
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

// Recarrega a página de origem quando o popup for fechado
window.addEventListener('unload', () => {
    if (window.opener && !window.opener.closed) {
        window.opener.location.reload();
    }
});

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

// NOVA FUNÇÃO EXPORTAR TABELA PARA INCLUIR TODOS OS DADOS
async function exportarTabela() {
    try {
        const response = await fetch('/tutores/todos-dados'); // Chama o novo endpoint
        if (!response.ok) {
            throw new Error(`Erro ao buscar dados: ${response.statusText}`);
        }
        const tutoresCompletos = await response.json();

        if (tutoresCompletos.length === 0) {
            alert("Não há dados de tutores para exportar.");
            return;
        }

        // Preparar os dados para a planilha
        const dadosPlanilha = [];

        // Cabeçalhos das colunas
        const cabecalhosTutor = [
            'ID', 'Nome', 'Celular', 'CPF', 'RG', 'Data Nascimento', 'Naturalidade',
            'Estado Civil', 'Nome Cônjuge', 'Tem Filhos', 'Qtde Filhos', 'CEP', 'Município',
            'UF', 'Rua', 'Número', 'Bairro', 'Telefone', 'Tipo Residência',
            'Situação Imóvel', 'Valor Aluguel Imóvel', 'Status'
        ];
        const cabecalhosSocioeconomicos = [
            'Situação Moradia', 'Moradia Coletiva Esp.', 'Valor Aluguel Moradia',
            'Outros Moradia Esp.', 'Local Trabalho Tutor', 'Valor Remuneração Tutor',
            'Tem Outras Fontes Renda', 'Valor Outras Fontes Renda', 'Tem Conta Bancária',
            'Nome Banco', 'Local Trabalho Pai', 'Local Trabalho Mãe',
            'Local Trabalho Cônjuge', 'Local Trabalho Filhos', 'Renda Pai',
            'Renda Mãe', 'Renda Cônjuge', 'Renda Familiar Total',
            'Nº Pessoas Grupo Familiar', 'Programa Social', 'Outro Programa Social',
            'Bens Familiares'
        ];

        dadosPlanilha.push([...cabecalhosTutor, ...cabecalhosSocioeconomicos]);

        // Preencher as linhas com os dados
        tutoresCompletos.forEach(tutorCompleto => {
            const tutor = tutorCompleto.tutor;
            const dadosSocioeconomicos = tutorCompleto.dadosSocioeconomicos;

            const linha = [
                tutor.id,
                tutor.nome,
                tutor.celular,
                tutor.cpf,
                tutor.rg,
                formatarData(tutor.dataNascimento), // Reutiliza sua função de formatação
                tutor.naturalidade,
                tutor.estadoCivil,
                tutor.nomeMarido,
                tutor.temFilhos,
                tutor.quantidadeFilhos,
                tutor.cep,
                tutor.municipio,
                tutor.uf,
                tutor.rua,
                tutor.numero,
                tutor.bairro,
                tutor.telefone,
                tutor.tipoResidencia,
                tutor.situacaoImovel,
                tutor.valorAluguel,
                tutor.status,
                // Dados Socioeconômicos (verificar se o objeto não é nulo antes de acessar)
                dadosSocioeconomicos ? dadosSocioeconomicos.situacaoMoradia : '',
                dadosSocioeconomicos ? dadosSocioeconomicos.moradiaColetivaEspecificacao : '',
                dadosSocioeconomicos ? dadosSocioeconomicos.valorAluguel : '',
                dadosSocioeconomicos ? dadosSocioeconomicos.outrosEspecificacao : '',
                dadosSocioeconomicos ? dadosSocioeconomicos.localTrabalho : '',
                dadosSocioeconomicos ? dadosSocioeconomicos.valorRemuneracao : '',
                dadosSocioeconomicos ? dadosSocioeconomicos.temOutrasFontesDeRenda : '',
                dadosSocioeconomicos ? dadosSocioeconomicos.valorOutrasFontesDeRenda : '',
                dadosSocioeconomicos ? dadosSocioeconomicos.temContaBancaria : '',
                dadosSocioeconomicos ? dadosSocioeconomicos.nomeBanco : '',
                dadosSocioeconomicos ? dadosSocioeconomicos.localTrabalhoPai : '',
                dadosSocioeconomicos ? dadosSocioeconomicos.localTrabalhoMae : '',
                dadosSocioeconomicos ? dadosSocioeconomicos.localTrabalhoConjuge : '',
                dadosSocioeconomicos ? dadosSocioeconomicos.localTrabalhoFilhos : '',
                dadosSocioeconomicos ? dadosSocioeconomicos.rendaPai : '',
                dadosSocioeconomicos ? dadosSocioeconomicos.rendaMae : '',
                dadosSocioeconomicos ? dadosSocioeconomicos.rendaConjuge : '',
                dadosSocioeconomicos ? dadosSocioeconomicos.valorTotalRemuneracao : '',
                dadosSocioeconomicos ? dadosSocioeconomicos.numeroPessoasGrupoFamiliar : '',
                dadosSocioeconomicos ? dadosSocioeconomicos.programaSocial : '',
                dadosSocioeconomicos ? dadosSocioeconomicos.outroProgramaSocial : '',
                dadosSocioeconomicos ? dadosSocioeconomicos.bensFamiliares : ''
            ];
            dadosPlanilha.push(linha);
        });

        // Criar a planilha e o arquivo Excel
        const ws = XLSX.utils.aoa_to_sheet(dadosPlanilha);
        const wb = XLSX.utils.book_new();
        XLSX.utils.book_append_sheet(wb, ws, "Dados Completos Tutores");
        XLSX.writeFile(wb, 'relatorio_completo_tutores.xlsx');

        alert("Relatório completo gerado com sucesso!");

    } catch (error) {
        console.error('Erro ao gerar relatório completo:', error);
        alert("Erro ao gerar relatório completo. Verifique o console para mais detalhes.");
    }
}

const botaoFiltrarAdmins = document.querySelector('#administradores-section .filter-div button');
if (botaoFiltrarAdmins) {
    botaoFiltrarAdmins.addEventListener('click', () => {
        const nomeAdmin = document.querySelector('#administradores-section .filter-div input').value;
        window.carregarAdminsResumidos(nomeAdmin); // Chame a função global
    });
}


// Carrega a tabela de administradores inicialmente (sem filtro)
window.carregarAdminsResumidos(); // Chame a função global na inicialização

// Torne carregarAdminsResumidos global também para consistência
window.carregarAdminsResumidos = function(nome = '') {
    let url = '/usuarios/resumidos';
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
            // Adicione uma mensagem de erro na tabela para feedback ao usuário
            const tbody = document.querySelector('#administradores-section table.table-cadastro tbody');
            tbody.innerHTML = '<tr><td colspan="6">Erro ao carregar dados dos administradores.</td></tr>';
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

// Recarrega a página de origem quando o popup for fechado
window.addEventListener('unload', () => {
    if (window.opener && !window.opener.closed) {
        window.opener.location.reload();
    }
});