document.addEventListener('DOMContentLoaded', function () {
    const temFilhosSelect = document.getElementById('temFilhos');
    const quantidadeFilhosSelect = document.getElementById('quantidadeFilhos');
    const estadoCivilSelect = document.getElementById('estadoCivil');
    const nomeMaridoInput = document.getElementById('nomeConjuge');
    const buttonAlert = document.querySelector('.button-next');
    const situacaoImovelSelect = document.getElementById('situacaoImovel');
    const valorAluguelImovelInput = document.getElementById('valorAluguel');
    const situacaoMoradiaSelect = document.getElementById('select-situacao-moradia');
    const moradiaColetivaInput = document.getElementById('moradia-coletiva-especificacao');
    const valorAluguelMoradiaInput = document.getElementById('valor-aluguel');
    const outrosInput = document.getElementById('outros-especificacao');
    const temOutrasFontesSelect = document.getElementById('temOutrasFontesSelect');
    const valorOutrasFontesInput = document.getElementById('valorOutrasFontesInput');
    const temContaBancariaSelect = document.getElementById('temContaBancariaSelect');
    const nomeBancoInput = document.getElementById('nomeBancoInput');
    const selectPrograma = document.getElementById('selectProgramaSocial');
    const inputOutro = document.getElementById('inputOutroProgramaSocial');

    // Tem filhos?
    if (temFilhosSelect && quantidadeFilhosSelect) {
        function toggleQuantidadeFilhos() {
            if (temFilhosSelect.value === 'Sim') {
                quantidadeFilhosSelect.disabled = false;
            } else {
                quantidadeFilhosSelect.value = '';
                quantidadeFilhosSelect.disabled = true;
            }
        }
        temFilhosSelect.addEventListener('change', toggleQuantidadeFilhos);
        toggleQuantidadeFilhos();
    }

    // Estado civil e nome do cônjuge
    if (estadoCivilSelect && nomeMaridoInput) {
        function toggleNomeMarido() {
            if (estadoCivilSelect.value === 'Casado') {
                nomeMaridoInput.disabled = false;
            } else {
                nomeMaridoInput.value = '';
                nomeMaridoInput.disabled = true;
            }
        }
        estadoCivilSelect.addEventListener('change', toggleNomeMarido);
        toggleNomeMarido();
    }

    // Situação do imóvel e valor do aluguel
    if (situacaoImovelSelect && valorAluguelImovelInput) {
        function toggleValorAluguelImovel() {
            if (situacaoImovelSelect.value === 'Alugado') {
                valorAluguelImovelInput.disabled = false;
            } else {
                valorAluguelImovelInput.value = '';
                valorAluguelImovelInput.disabled = true;
            }
        }
        situacaoImovelSelect.addEventListener('change', toggleValorAluguelImovel);
        toggleValorAluguelImovel();
    }

    // Situação da moradia
    if (situacaoMoradiaSelect && moradiaColetivaInput && valorAluguelMoradiaInput && outrosInput) {
        function toggleInputs() {
            moradiaColetivaInput.disabled = situacaoMoradiaSelect.value !== 'Moradia coletiva';
            valorAluguelMoradiaInput.disabled = situacaoMoradiaSelect.value !== 'Alugado';
            outrosInput.disabled = situacaoMoradiaSelect.value !== 'Outros';

            if (moradiaColetivaInput.disabled) moradiaColetivaInput.value = '';
            if (valorAluguelMoradiaInput.disabled) valorAluguelMoradiaInput.value = '';
            if (outrosInput.disabled) outrosInput.value = '';
        }

        situacaoMoradiaSelect.addEventListener('change', toggleInputs);
        toggleInputs();
    }

    // Outras fontes de renda
    if (temOutrasFontesSelect && valorOutrasFontesInput) {
        function toggleValorOutrasFontes() {
            valorOutrasFontesInput.disabled = temOutrasFontesSelect.value !== 'Sim';
            if (valorOutrasFontesInput.disabled) valorOutrasFontesInput.value = '';
        }
        temOutrasFontesSelect.addEventListener('change', toggleValorOutrasFontes);
        toggleValorOutrasFontes();
    }

    // Conta bancária
    if (temContaBancariaSelect && nomeBancoInput) {
        function toggleNomeBanco() {
            nomeBancoInput.disabled = temContaBancariaSelect.value !== 'Sim';
            if (nomeBancoInput.disabled) nomeBancoInput.value = '';
        }
        temContaBancariaSelect.addEventListener('change', toggleNomeBanco);
        toggleNomeBanco();
    }

    // Programa social "Outros"
    if (selectPrograma && inputOutro) {
        function toggleOutroInput() {
            const isOutros = selectPrograma.value === 'Outros';
            inputOutro.disabled = !isOutros;
            inputOutro.required = isOutros;
            if (!isOutros) inputOutro.value = '';
        }

        selectPrograma.addEventListener('change', toggleOutroInput);
        toggleOutroInput();
    }

 // Validação + Envio com fetch
    if (form && buttonAlert) {
        buttonAlert.addEventListener('click', async function (e) {
            e.preventDefault();

            const requiredFields = form.querySelectorAll('input[required], select[required], textarea[required]');
            let allFilled = true;

            requiredFields.forEach(field => {
                if (!field.value.trim()) {
                    allFilled = false;
                    field.classList.add('is-invalid');
                } else {
                    field.classList.remove('is-invalid');
                }
            });

            if (!allFilled) {
                alert('Preencha todos os campos obrigatórios.');
                return;
            }

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

                    if (window.opener && !window.opener.closed) {
                        window.opener.carregarTutoresResumidos();
                    }

                    alert('Tutor cadastrado com sucesso!');
                    form.reset(); // limpa o formulário
                } else {
                    alert("Erro ao salvar tutor.");
                }
            } catch (error) {
                console.error('Erro ao enviar o formulário:', error);
            }
        });
    }
});

// Essas funções não precisam estar dentro do DOMContentLoaded
function carregarTutoresResumidos() {
    fetch('/tutores/resumidos')
        .then(response => response.json())
        .then(tutores => {
            const tbody = document.getElementById('tabela-tutores-body');
            tbody.innerHTML = '';

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
    return data.toLocaleDateString('pt-BR');
}

