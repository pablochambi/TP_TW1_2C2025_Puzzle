function seleccionarPaquete(paqueteId, cantidadMonedas, precio) {
    document.getElementById('paqueteId').value = paqueteId;
    document.getElementById('cantidadMonedas').textContent = cantidadMonedas;
    document.getElementById('precioPaquete').textContent = precio;

}

document.addEventListener('DOMContentLoaded', function() {

    // Verificar modal de error
    const modalError = document.getElementById('modalError');
    if (modalError && modalError.getAttribute('data-mostrar') === 'true') {
        const modal = new bootstrap.Modal(modalError);
        modal.show();
    }

    // Verificar modal de éxito
    const modalExito = document.getElementById('modalExito');
    if (modalExito && modalExito.getAttribute('data-mostrar') === 'true') {
        const modal = new bootstrap.Modal(modalExito);
        modal.show();
    }
});