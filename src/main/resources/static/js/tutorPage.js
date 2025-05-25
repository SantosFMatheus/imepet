document.addEventListener('DOMContentLoaded', function () {
    const temFilhosSelect = document.getElementById('temFilhos');
    const quantidadeFilhosSelect = document.getElementById('quantidadeFilhos');
    const estadoCivilSelect = document.getElementById('estadoCivil');
    const nomeMaridoInput = document.getElementById('nomeMarido');
    const buttonAlert = document.querySelector(".button-next"); // Correção aqui

    // Verifica se o botão foi encontrado antes de adicionar o listener
    if (buttonAlert) {
        buttonAlert.addEventListener("click", function () {
            alert("Tutor Cadastrado com Sucesso!");
        });
    }

    function toggleQuantidadeFilhos() {
        if (temFilhosSelect.value === 'Sim') {
            quantidadeFilhosSelect.disabled = false;
        } else {
            quantidadeFilhosSelect.value = '';
            quantidadeFilhosSelect.disabled = true;
        }
    }

    function toggleNomeMarido() {
        if (estadoCivilSelect.value === 'Casado') {
            nomeMaridoInput.disabled = false;
        } else {
            nomeMaridoInput.value = '';
            nomeMaridoInput.disabled = true;
        }
    }

    toggleQuantidadeFilhos();
    toggleNomeMarido();

    temFilhosSelect.addEventListener('change', toggleQuantidadeFilhos);
    estadoCivilSelect.addEventListener('change', toggleNomeMarido);

    document.addEventListener('DOMContentLoaded', () => {
        const form = document.querySelector('form');

        form.addEventListener('submit', async function (event) {
            event.preventDefault();

            const formData = new FormData(form);
            const jsonData = {};

            for (const [key, value] of formData.entries()) {
                jsonData[key] = value;
            }

            try {
                const response = await fetch('/tutores/salvar', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json',
                    },
                    body: JSON.stringify(jsonData)
                });

                if (response.ok) {
                    const novoTutor = await response.json();

                    // Atualiza a tabela na janela principal
                    if (window.opener && !window.opener.closed) {
                        window.opener.carregarTutoresResumidos();
                    }

                    // Fecha o popup
                    window.close();

                    // Opcional: resetar o formulário
                    form.reset();
                }
                 else {
                    alert("Erro ao salvar tutor.");
                }
            } catch (error) {
                console.error('Erro ao enviar o formulário:', error);
            }
        });
    });
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

});
