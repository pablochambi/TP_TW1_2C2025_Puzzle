/* =========================
   editar_perfil.js
   ========================= */

// Referencias a elementos
const usernameInput = document.getElementById('username');
const passwordInput = document.getElementById('password');
const avatarPreview = document.getElementById('avatarPreview');
const avatarUpload = document.getElementById('avatarUpload');
const avatarOptions = document.querySelectorAll('.avatar-option');

// Valores originales del avatar
let currentAvatarType = 'letter';
let currentAvatarValue = 'auto';

// ===============================
// FUNCIONES PRINCIPALES
// ===============================

// Función para mostrar/ocultar contraseña
function togglePassword() {
    const passwordInput = document.getElementById('password');
    const showBtn = event.target;

    if (passwordInput.type === 'password') {
        passwordInput.type = 'text';
        showBtn.textContent = '\uD83D\uDD12'; // 🔒
        showBtn.title = 'Ocultar contraseña';
    } else {
        passwordInput.type = 'password';
        showBtn.textContent = '\uD83D\uDC41\uFE0F'; // 👁️
        showBtn.title = 'Mostrar contraseña';
    }
}

// Función para actualizar avatar según tipo seleccionado
function actualizarVistaPreviaDeAvatar() {
    const username = usernameInput.value.trim();

    if (currentAvatarType === 'letter') {
        avatarPreview.className = 'avatar';
        avatarPreview.innerHTML = username.charAt(0).toUpperCase() || 'U';
        // Actualizar también el botón de letra automática
        const letterOption = document.querySelector('[data-type="letter"]');
        if (letterOption) {
            letterOption.textContent = username.charAt(0).toUpperCase() || 'U';
        }
    } else if (currentAvatarType === 'emoji') {
        avatarPreview.className = 'avatar emoji-avatar';
        avatarPreview.innerHTML = currentAvatarValue;
    } else if (currentAvatarType === 'image') {
        avatarPreview.className = 'avatar';
        avatarPreview.innerHTML = `<img src="${currentAvatarValue}" alt="Avatar personalizado">`;
    }
}

// Función para seleccionar avatar
function selectAvatar(option) {
    // Remover selección anterior
    avatarOptions.forEach(opt => opt.classList.remove('selected'));
    // Seleccionar nuevo
    option.classList.add('selected');

    currentAvatarType = option.dataset.type;
    currentAvatarValue = option.dataset.value;

    actualizarVistaPreviaDeAvatar();
}

// ===============================
// AVATARES GRATIS
// ===============================

// Escuchador para opciones de avatar gratis
avatarOptions.forEach(option => {
    option.addEventListener('click', () => selectAvatar(option));
});

// ===============================
// AVATARES PREMIUM (COMPRA)
// ===============================

// Referencias al modal
const modalCompra = new bootstrap.Modal(document.getElementById('confirmCompraModal'));
const confirmarCompraLink = document.getElementById('confirmarCompraBtn');

// Escuchadores para todos los botones de compra
document.querySelectorAll('.comprar-btn').forEach(btn => {
    btn.addEventListener('click', function(e) {
        e.preventDefault();

        // Obtener información del avatar
        const avatarId = this.getAttribute('data-avatar-id');
        const avatarName = this.getAttribute('data-avatar-name');
        const avatarPrice = this.getAttribute('data-avatar-price');
        const avatarImg = this.getAttribute('data-avatar-img');

        // Actualizar contenido del modal
        document.getElementById('modalAvatarName').textContent = avatarName;
        document.getElementById('modalAvatarPrice').textContent = avatarPrice;
        document.getElementById('modalAvatarImg').src = `http://localhost:8080/spring/img/${avatarImg}`;

        // Actualizar href del enlace con el ID del avatar
        confirmarCompraLink.href = `http://localhost:8080/spring/perfil/avatar/comprar/${avatarId}`;

        // Mostrar modal
        modalCompra.show();
    });
});

// Validación mínima antes de permitir la compra (opcional)
confirmarCompraLink.addEventListener('click', function(e) {
    // Validación simple: verificar que el href no esté vacío o sea el default '#'
    if (!this.href || this.href.endsWith('#')) {
        e.preventDefault();
        alert('Error: No se ha seleccionado un avatar válido');
        return false;
    }
    // Si todo está bien, el enlace se ejecuta normalmente
});

// Función para usar un avatar premium comprado
function usarAvatarPremium(btn) {
    const avatarImg = btn.getAttribute('data-avatar-img');

    // Actualizar el avatar en la Card de Vista Previa
    currentAvatarType = 'premium';
    currentAvatarValue = `http://localhost:8080/spring/img/${avatarImg}`;

    avatarPreview.className = 'avatar avatar-premium';
    avatarPreview.innerHTML = `<img src="${currentAvatarValue}" alt="Avatar Premium">`;
}

// ===============================
// ACTUALIZAR AVATAR AL CAMBIAR NOMBRE
// ===============================

usernameInput.addEventListener('input', function() {
    if (currentAvatarType === 'letter') {
        actualizarVistaPreviaDeAvatar();
    }
});

// ===============================
// INICIALIZACIÓN
// ===============================

// Inicializar vista previa del avatar
actualizarVistaPreviaDeAvatar();

// ===============================
// COLOCA AVATAR SELECCIONADO EN INPUT HIDDEN para enviarlo al controlador desde el formulario
// ===============================
const avatarInput = document.getElementById('avatarSeleccionadoInput');

avatarOptions.forEach(option => {
    option.addEventListener('click', () => {
        const value = option.getAttribute('data-value');

        // Actualiza la vista previa
        avatarPreview.textContent = value;

        // Guarda el valor en el input hidden
        avatarInput.value = value;
    });
});