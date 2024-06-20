function desplegarForm() {
    var desplegarFormContainer = document.getElementById("form_container");

    var botones = document.querySelectorAll('.btn_desplegarForm');
    botones.forEach(function (boton) {
        boton.addEventListener('click', function () {
            var idSale = this.value;
            var xmlhttp = new XMLHttpRequest();
            xmlhttp.open("GET", "/sales/saleForm?sale=" + idSale, true);
            xmlhttp.send();

            xmlhttp.onreadystatechange = function () {
                if (this.readyState === 4 && this.status === 200) {
                    desplegarFormContainer.innerHTML = this.responseText;
                } else if (this.status === 500) {
                    console.error("Error al cargar el formulario.");
                }
            };
        });
    });
}

function ocultarForm() {
    document.getElementById("form_container").innerHTML = "";
}

function editarVenta() {
    var editarForm = document.querySelector('.editForm');
    editarForm.addEventListener('submit', function (event) {
        event.preventDefault();
        Swal.fire({
            title: '¿Desea continuar con la edición de esta venta?',
            text: '¡Asegúrate de tener los datos correctos!',
            icon: 'question',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: 'Sí, continuar',
            cancelButtonText: 'Cancelar'
        }).then((result) => {
            if (result.isConfirmed) {
                const formData = new FormData(editarForm);
                const jsonData = {};
                formData.forEach((value, key) => {
                    if (key === 'idAgreement' || key === 'idClient' || key === 'idRealStateAgent' || key === 'idPaymentMethod') {
                        jsonData[key] = { id: value };
                    } else {
                        jsonData[key] = value;
                    }
                });
                const requestBody = JSON.stringify(jsonData);

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
                                window.location.href = "./listarVentas";
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
    event.preventDefault();

    var form = document.getElementById('form-crear');
    var inputs = form.getElementsByTagName('input');
    var mensajeContainer = document.getElementById('texto_error');

    for (var i = 0; i < inputs.length; i++) {
        var input = inputs[i];
        if (input.value.trim() === '') {
            var mensaje = "Por favor, completa todos los campos.";
            mostrarMensaje(mensaje, mensajeContainer);
            return false;
        }
    }

    return true;
}

function mostrarMensaje(mensaje, container) {
    container.innerHTML = mensaje;
}

function validarCreacionVenta() {
    var crearForm = document.querySelector('.form-crear');

    crearForm.addEventListener('submit', function (event) {
        event.preventDefault();
        if (validarFormularioCrear(event)) {
            Swal.fire({
                title: "¿Estas seguro de crear esta venta?",
                text: '¡Asegurate de tener los datos correctos!',
                icon: 'question',
                showCancelButton: true,
                confirmButtonColor: '#3085d6',
                cancelButtonColor: '#d33',
                confirmButtonText: 'Sí, continuar',
                cancelButtonText: 'Cancelar'
            }).then((result) => {
                if (result.isConfirmed) {
                    const formData = new FormData(crearForm);
                    const jsonData = {};
                    formData.forEach((value, key) => {
                        if (key === 'idAgreement' || key === 'idClient' || key === 'idRealStateAgent' || key === 'idPaymentMethod') {
                            jsonData[key] = { id: value };
                        } else {
                            jsonData[key] = value;
                        }
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
                                    window.location.href = './listarVentas';
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