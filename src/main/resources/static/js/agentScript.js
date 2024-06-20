function desplegarForm() {
    var desplegarFormContainer = document.getElementById("form_container");

    // Obtener todos los botones de desplegar formularios y agregar un evento clic a cada uno
    var botones = document.querySelectorAll('.btn_desplegarForm');
    botones.forEach(function (boton) {
        boton.addEventListener('click', function () {
            // Obtener el valor (ID del agente) del botón clicado
            var idAgent = this.value;

            var xmlhttp = new XMLHttpRequest();
            xmlhttp.open("GET", "/agents/agentForm?agent=" + idAgent, true);
            xmlhttp.send();

            xmlhttp.onreadystatechange = function () {
                if (this.readyState === 4 && this.status === 200) {
                    desplegarFormContainer.innerHTML = this.responseText;
                }
            };
        });
    });
}

function ocultarForm() {
    document.getElementById("form_container").innerHTML = "";
}

function editarAgent() {
    var editarForm = document.querySelector('.editForm');
    editarForm.addEventListener('submit', function (event) {
        event.preventDefault();
        Swal.fire({
            title: '¿Desea continuar con la edición de este agente?',
            text: '¡Asegúrate de tener los datos correctos!',
            icon: 'question',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: 'Sí, continuar',
            cancelButtonText: 'Cancelar'
        }).then((result) => {
            if (result.isConfirmed) {
                // Obtener datos del formulario y convertirlos a JSON
                const formData = new FormData(editarForm);
                const jsonData = {};
                formData.forEach((value, key) => {
                    jsonData[key] = value;
                });
                const requestBody = JSON.stringify(jsonData);

                // Enviar la solicitud con el cuerpo en formato JSON
                fetch(editarForm.action, {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: requestBody
                })
                    .then(response => response.json())
                    .then(data => {
                        if (data.success) {
                            mostrarToastConfirmacion(data.message);
                            setTimeout(function () {
                                window.location.href = "./listarAgentes";
                            }, 1000);
                        } else {
                            mostrarToastError(data.message);
                        }
                    })
                    .catch(error => {
                        console.error('Error:', error);
                    });
            }
        });
    });
}

function validarFormularioCrear(event) {
    event.preventDefault(); // Evitar que el evento por defecto se ejecute

    var form = document.getElementById('form-crear');
    var inputs = form.getElementsByTagName('input');
    var mensajeContainer = document.getElementById('texto_error');

    for (var i = 0; i < inputs.length; i++) {
        var input = inputs[i];
        if (input.value.trim() === '') {
            var mensaje = "Por favor, completa todos los campos.";
            mostrarMensaje(mensaje, mensajeContainer);
            return false; // Detener la validación y no enviar el formulario
        }
    }

    return true;
}

function mostrarMensaje(mensaje, container) {
    container.innerHTML = mensaje;
}

function validarCreacionAgent() {
    var crearForm = document.querySelector('.form-crear');

    crearForm.addEventListener('submit', function (event) {
        event.preventDefault();
        if (validarFormularioCrear(event)) {
            Swal.fire({
                title: "¿Estas seguro de crear este agente?",
                text: '¡Asegurate de tener los datos correctos!',
                icon: 'question',
                showCancelButton: true,
                confirmButtonColor: '#3085d6',
                cancelButtonColor: '#d33',
                confirmButtonText: 'Sí, continuar',
                cancelButtonText: 'Cancelar'
            }).then((result) => {
                if (result.isConfirmed) {
                    // Obtener datos del formulario y convertirlos a JSON
                    const formData = new FormData(crearForm);
                    const jsonData = {};
                    formData.forEach((value, key) => {
                        jsonData[key] = value;
                    });
                    const requestBody = JSON.stringify(jsonData);
                    fetch(crearForm.action, {
                        method: 'POST',
                        headers: {
                            'Content-Type': 'application/json'
                        },
                        body: requestBody
                    })
                        .then(response => response.json())
                        .then(data => {
                            if (data.success) {
                                // Si el proceso de agregar el agente fue exitoso, mostrar mensaje de éxito
                                mostrarToastConfirmacion(data.message);
                                // Redirigir después de un pequeño retraso
                                setTimeout(function () {
                                    window.location.href = './listarAgentes';
                                }, 1000); // 1000 milisegundos de retraso
                            } else {
                                // Si el proceso de agregar el agente falló, mostrar mensaje de error
                                mostrarToastError(data.message);
                            }
                        })
                        .catch(error => {
                            console.error('Error:', error);
                        });
                }
            });
        }
    });
}

function validarEliminacion(selector, redirectUrl) {
    var botones = document.querySelectorAll(selector);
    botones.forEach(function (boton) {
        boton.addEventListener('click', function (event) {
            event.preventDefault();
            var href = boton.querySelector('a').getAttribute('href');
            Swal.fire({
                title: '¿Estás seguro?',
                text: "¡No podrás revertir esto!",
                icon: 'warning',
                showCancelButton: true,
                confirmButtonColor: '#3085d6',
                cancelButtonColor: '#d33',
                confirmButtonText: 'Sí, eliminarlo',
                cancelButtonText: 'Cancelar'
            }).then((result) => {
                if (result.isConfirmed) {
                    fetch(href, {
                        method: 'DELETE'
                    })
                        .then(response => response.json())
                        .then(data => {
                            if (data.success) {
                                mostrarToastConfirmacion(data.message);
                                setTimeout(function () {
                                    window.location.href = redirectUrl;
                                }, 1000); // 1000 milisegundos de retraso
                            } else {
                                mostrarToastError(data.message);
                            }
                        })
                        .catch(error => {
                            console.error('Error:', error);
                        });
                }
            });
        });
    });
}

document.addEventListener("DOMContentLoaded", function () {
    validarEliminacion('.btn_delete', './listarAgentes');
    desplegarForm();
});
