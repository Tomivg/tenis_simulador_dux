document.addEventListener('DOMContentLoaded', function () {
    const formulario = document.getElementById('simulacionForm');
    const resultadoDiv = document.getElementById('resultado');

    formulario.addEventListener('submit', function (event) {
        event.preventDefault();

        const formData = new FormData(formulario);

        fetch('/api/simularPartido', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(Object.fromEntries(formData))
        })
        .then(response => response.json())
        .then(result => {
            // Muestra los resultados en el div de resultados
            resultadoDiv.innerHTML = '';
            result.forEach(item => {
                const p = document.createElement('p');
                p.textContent = item.mensaje;
                resultadoDiv.appendChild(p);
            });
        })
        .catch(error => {
            console.error('Error:', error);
            resultadoDiv.innerHTML = 'Hubo un error al simular el partido.';
        });
    });
});