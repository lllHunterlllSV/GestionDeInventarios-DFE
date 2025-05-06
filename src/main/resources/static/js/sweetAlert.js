function showAlert() {
    Swal.fire({
        title: 'Exito',
        text: 'Completado con exito!',
        icon: 'success',
        confirmButtonText: 'Aceptar',
        timer: 2500, // dura más
        timerProgressBar: true
    }).then(() => {
        document.querySelector('form').submit();
    });

}
function confirmarEnvio() {
    Swal.fire({
        title: '¿Estás seguro?',
        text: '¿Deseas guardar este proveedor?',
        icon: 'question',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: 'Sí, guardar',
        cancelButtonText: 'Cancelar'
    }).then((result) => {
        if (result.isConfirmed) {
            document.querySelector('form').submit();
        }
    });
}

function confirmarEliminar(formulario) {
    const nombre = formulario.querySelector('button').getAttribute('data-nombre') || 'este proveedor';

    Swal.fire({
        title: '¿Estás seguro?',
        text: `¿Deseas eliminar al proveedor "${nombre}"?`,
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#e3342f',
        cancelButtonColor: '#6c757d',
        confirmButtonText: 'Sí, eliminar',
        cancelButtonText: 'Cancelar'
    }).then((result) => {
        if (result.isConfirmed) {
            formulario.submit();
        }
    });
}

///producto

function confirmarEliminarProd(formulario) {
    const nombre = formulario.querySelector('button').getAttribute('data-nombre') || 'este producto';

    Swal.fire({
        title: '¿Estás seguro?',
        text: `¿Deseas eliminar "${nombre}"?`,
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#e3342f',
        cancelButtonColor: '#6c757d',
        confirmButtonText: 'Sí, eliminar',
        cancelButtonText: 'Cancelar'
    }).then((result) => {
        if (result.isConfirmed) {
            formulario.submit();
        }
    });
}

window.addEventListener('DOMContentLoaded', () => {
    const exito = document.body.getAttribute('data-exito');
    const error = document.body.getAttribute('data-error');

    if (exito) {
        Swal.fire({
            icon: 'success',
            title: '¡Éxito!',
            text: exito,
            timer: 3000,
            showConfirmButton: false,
            timerProgressBar: true
        });
    }

    if (error) {
        Swal.fire({
            icon: 'error',
            title: '¡Error!',
            text: error,
            confirmButtonText: 'Cerrar',
            confirmButtonColor: '#d33'
        });
    }
});

//producto
function confirmarEnvioProd() {
    Swal.fire({
        title: '¿Estás seguro?',
        text: '¿Deseas guardar este producto?',
        icon: 'question',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: 'Sí, guardar',
        cancelButtonText: 'Cancelar'
    }).then((result) => {
        if (result.isConfirmed) {
            document.getElementById('formProducto').submit();
        }
    });
}


