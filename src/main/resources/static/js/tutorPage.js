document.addEventListener('DOMContentLoaded', function () {
    const temFilhosSelect = document.getElementById('temFilhos');
    const quantidadeFilhosSelect = document.getElementById('quantidadeFilhos');
    const estadoCivilSelect = document.getElementById('estadoCivil');
    const nomeMaridoInput = document.getElementById('nomeMarido');

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

    // Executa ao carregar a página
    toggleQuantidadeFilhos();
    toggleNomeMarido();

    // Adiciona ouvintes de mudança
    temFilhosSelect.addEventListener('change', toggleQuantidadeFilhos);
    estadoCivilSelect.addEventListener('change', toggleNomeMarido);
});