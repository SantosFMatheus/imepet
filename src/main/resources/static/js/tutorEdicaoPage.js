document.addEventListener("DOMContentLoaded", function () {
    const btnSalvar = document.getElementById("btnSalvarEdicao");

    btnSalvar.addEventListener("click", function () {
        const form = document.getElementById("formTutorEdicao");
        const formData = new FormData(form);

        // Converte o FormData para um objeto JS
        const tutorData = {};
        formData.forEach((value, key) => {
            // Lida com campos aninhados tipo dadosSocioeconomicos.campo
            const keys = key.split('.');
            if (keys.length > 1) {
                if (!tutorData[keys[0]]) {
                    tutorData[keys[0]] = {};
                }
                tutorData[keys[0]][keys[1]] = value;
            } else {
                tutorData[key] = value;
            }
        });

        fetch("/tutores/atualizar", {
            method: "PUT",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(tutorData)
        })
        .then(response => {
            if (response.ok) {
                alert("Cadastro atualizado com sucesso!");
                window.location.reload(); // ou feche o popup, se for o caso
            } else {
                alert("Erro ao atualizar o cadastro.");
            }
        })
        .catch(error => {
            console.error("Erro ao atualizar:", error);
            alert("Erro ao atualizar o cadastro.");
        });
    });
});

function atualizarStatusTutor(novoStatus) {
    const tutorId = document.querySelector('input[name="tutor.id"]').value;

    fetch(`/tutores/${tutorId}/status`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ status: novoStatus })
    })
    .then(response => {
        if (response.ok) {
            alert(`Status atualizado para: ${novoStatus}`);
            window.close(); // fecha o popup
            window.opener.location.reload(); // recarrega a página principal
        } else {
            alert('Erro ao atualizar o status.');
        }
    })
    .catch(error => {
        console.error(error);
        alert('Erro ao conectar com o servidor.');
    });
}
