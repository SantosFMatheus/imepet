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

