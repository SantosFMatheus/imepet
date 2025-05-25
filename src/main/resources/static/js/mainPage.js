// Função que torna o botão de sair vermelho ao dar hover
document.addEventListener("DOMContentLoaded", () => {
    const quitBtn = document.getElementById("quitBtn");

    if (quitBtn) {
        quitBtn.addEventListener("mouseover", () => {
            quitBtn.src = "/img/mainPage/quit-red.png";
        });

        quitBtn.addEventListener("mouseout", () => {
            quitBtn.src = "/img/mainPage/quit.png";
        });
    }
});

//Função para tornar os botões dinâmicos
document.addEventListener("DOMContentLoaded", () => {
    const links = document.querySelectorAll(".menu-link");

    links.forEach(link => {
        link.addEventListener("click", (e) => {
            e.preventDefault();

            // Remove "active" de todos
            links.forEach(l => l.classList.remove("active"));

            // Adiciona "active" ao clicado
            link.classList.add("active");
        });
    });
});

// Função tornar a página dinâmica dependendo do botão que for clicado, ele devolve uma seção correspondente
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

    // Atualiza a seção ativa
    window.secaoAtual = id;
}

// Função de Popup das telas de cadastro
function abrirPopup() {
    let url = '#';

    switch (window.secaoAtual) {
        case 'visao-geral-section':
            url = '/tutores/novo';
            break;
        case 'administradores-section':
            url = 'https://www.google.com'; // lembre-se de incluir http:// ou https://
            break;
        default:
            alert('Selecione uma seção antes de adicionar!');
            return;
    }

    window.open(
        url,
        'popupWindow',
        'width=600,height=400,scrollbars=yes'
    );

}

function carregarTutoresResumidos() {
    fetch('/tutores/resumidos')
        .then(response => response.json())
        .then(tutores => {
            const tbody = document.getElementById('tabela-tutores-body');
            tbody.innerHTML = ''; // limpa a tabela

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
    return data.toLocaleDateString('pt-BR'); // converte para dd/mm/yyyy
}

document.addEventListener("DOMContentLoaded", () => {
    carregarTutoresResumidos();
});
