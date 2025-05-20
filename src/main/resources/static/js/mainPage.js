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

function mostrarSecao(id, link) {
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
}

function abrirPopup() {
    window.open(
        '/tutores/novo', // esta rota EXISTE no controller
        'popupWindow',
        'width=600,height=400,scrollbars=yes'
    );
}






