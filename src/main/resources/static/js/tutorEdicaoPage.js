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

function atualizarTutor() {
    const form = document.getElementById("formAtualizarTutor");
    const formData = new FormData(form);

    const jsonObject = {};
    formData.forEach((value, key) => {
        // Se já existe, transforma em array (para campos repetidos como checkboxes)
        if (jsonObject.hasOwnProperty(key)) {
            if (!Array.isArray(jsonObject[key])) {
                jsonObject[key] = [jsonObject[key]];
            }
            jsonObject[key].push(value);
        } else {
            jsonObject[key] = value;
        }
    });

    fetch('/tutores/atualizar', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(jsonObject)
    })
    .then(response => {
        if (response.ok) {
            alert("Tutor atualizado com sucesso!");
            // Fecha o popup (ajuste ao seu método, ex: usando modal do Bootstrap)
            fecharPopup();
            // Recarrega a lista de tutores na página principal
            atualizarTabelaTutores();
        } else {
            return response.text().then(text => { throw new Error(text) });
        }
    })
    .catch(error => {
        console.error("Erro ao atualizar tutor:", error);
        alert("Erro ao atualizar tutor.");
    });
}

function atualizarStatusTutor(novoStatus) {
            const idTutor = document.querySelector('input[name="tutor.id"]').value;

            fetch(`/tutores/${idTutor}/status`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({ status: novoStatus })
            })
            .then(response => {
                if (response.ok) {
                    alert(`Tutor ${novoStatus.toLowerCase()} com sucesso!`);
                    window.location.reload(); // ou feche o popup se for o caso
                } else {
                    alert("Erro ao atualizar o status do tutor.");
                }
            })
            .catch(error => {
                console.error("Erro:", error);
                alert("Erro na requisição.");
            });
        }

function fecharPopup() {
    // Exemplo: se usar Bootstrap Modal:
    const modal = bootstrap.Modal.getInstance(document.getElementById('tutorModal'));
    modal.hide();
}

function atualizarTabelaTutores() {
    // Suponha que você tenha esta função na página principal
    if (window.carregarTutoresResumidos) {
        window.carregarTutoresResumidos();
    } else {
        location.reload(); // fallback
    }
}

