document.addEventListener("DOMContentLoaded", function () {

    const btnAprovar = document.getElementById("btnAprovar");
        const btnReprovar = document.getElementById("btnReprovar");

        if (btnAprovar) {
            btnAprovar.addEventListener("click", function () {
                atualizarStatusTutor("APROVADO");
            });
        }

        if (btnReprovar) {
            btnReprovar.addEventListener("click", function () {
                atualizarStatusTutor("REPROVADO");
            });
        }

});


function atualizarStatusTutor(status) {
const tutorId = document.getElementById("tutorId").value;
     console.log("Atualizando tutor", tutorId, "para status", status);

    const formData = new FormData();
    formData.append("status", status);

    fetch(`/tutores/${tutorId}/status`, {
        method: "POST",
        body: formData
    })
    .then(response => {
        if (!response.ok) throw new Error("Erro ao atualizar status");
        return response.text(); // porque seu controller retorna texto
    })
    .then(msg => {
        alert(msg); // exemplo: "Status atualizado com sucesso."
        fecharPopup();
        atualizarTabelaTutores();
    })
    .catch(error => {
        console.error("Erro:", error);
        alert("Erro ao atualizar status do tutor.");
    });
}

function fecharPopup() {
    if (window.opener && !window.opener.closed) {
        window.opener.location.reload();  // recarrega a página mãe
    }
    window.close();  // fecha a janela popup
}

function atualizarTabelaTutores() {
    // Suponha que você tenha esta função na página principal
    if (window.carregarTutoresResumidos) {
        window.carregarTutoresResumidos();
    } else {
        location.reload(); // fallback
    }
}

