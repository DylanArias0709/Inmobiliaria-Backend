function desplegarFormClientPreference() {
    var desplegarFormContainer = document.getElementById("form_container");

    // Obtener todos los botones de desplegar formularios y agregar un evento clic a cada uno
    var botones = document.querySelectorAll('.btn_desplegarForm');
    botones.forEach(function (boton) {
        boton.addEventListener('click', function () {
            // Obtener el valor (ID de la preferencia de cliente) del botón clicado
            var idClientPreference = this.value;

            var xmlhttp = new XMLHttpRequest();
            xmlhttp.open("GET", "/clientPreferences/clientPreferenceForm?clientPreference=" + idClientPreference, true);
            xmlhttp.send();

            xmlhttp.onreadystatechange = function () {
                if (this.readyState === 4 && this.status === 200) {
                    desplegarFormContainer.innerHTML = this.responseText;
                }
            };
        });
    });
}

function ocultarFormClientPreference() {
    document.getElementById("form_container").innerHTML = "";
}

function editarClientPreference() {
    var editarForm = document.querySelector('.editForm');
    editarForm.addEventListener('submit', function (event) {
        event.preventDefault();
        Swal.fire({
            title: '¿Desea continuar con la edición de esta preferencia de cliente?',
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
                                window.location.href = "/clientPreferences/listarClientPreferences";
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

function validarFormularioCrearClientPreference(event) {
    event.preventDefault(); // Evitar que el evento por defecto se ejecute

    var form = document.getElementById('form-crear');
    var inputs = form.getElementsByTagName('input');
    var mensajeContainer = document.getElementById('texto_error');

    for (var i = 0; i < inputs.length; i++) {
        var input = inputs[i];
        if (input.value.trim() === '') {
            var mensaje = "Por favor, completa todos los campos.";
            mostrarMensajeClientPreference(mensaje, mensajeContainer);
            return false; // Detener la validación y no enviar el formulario
        }
    }

    return true;
}

function mostrarMensajeClientPreference(mensaje, container) {
    container.innerHTML = mensaje;
}

function validarCreacionClientPreference() {
    var crearForm = document.querySelector('.form-crear');

    crearForm.addEventListener('submit', function (event) {
        event.preventDefault();
        if (validarFormularioCrearClientPreference(event)) {
            Swal.fire({
                title: "¿Está seguro de crear esta Preferencia de Cliente?",
                text: '¡Asegúrese de tener los datos correctos!',
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
                                mostrarToastConfirmacion(data.message);
                                setTimeout(function () {
                                    window.location.href = './listarClientPreferences';
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
        }
    });
}

// Funciones para mostrar mensajes Toast usando Toastify.js (o cualquier otro sistema de notificaciones)

function mostrarToastConfirmacion(mensaje) {
    Toastify({
        text: mensaje,
        backgroundColor: "linear-gradient(to right, #00b09b, #96c93d)",
        className: "info",
    }).showToast();
}

function mostrarToastError(mensaje) {
    Toastify({
        text: mensaje,
        backgroundColor: "linear-gradient(to right, #ff416c, #ff4b2b)",
        className: "error",
    }).showToast();
}
