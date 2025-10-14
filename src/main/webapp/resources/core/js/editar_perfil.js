// ===============================
// REFERENCIAS A ELEMENTOS
// ===============================
const usernameInput = document.getElementById('username');
const avatarPreview = document.getElementById('avatarPreview');
const avatarOptions = document.querySelectorAll('.avatar-option');
const idAvatarInput = document.getElementById('idAvatarInput');

// ===============================
// FUNCIONES
// ===============================

// Mostrar/ocultar contraseña
function togglePassword() {
    const passwordInput = document.getElementById('password');
    const showBtn = event.target;

    if (passwordInput.type === 'password') {
        passwordInput.type = 'text';
        showBtn.textContent = '🔒';
        showBtn.title = 'Ocultar contraseña';
    } else {
        passwordInput.type = 'password';
        showBtn.textContent = '👁️';
        showBtn.title = 'Mostrar contraseña';
    }
}

// Actualizar vista previa del avatar
function actualizarVistaPrevia(tipo, valor) {
    avatarPreview.className = 'avatar';
    avatarPreview.textContent = ''; // limpiar

    if (tipo === 'letter') {
        avatarPreview.textContent = valor.charAt(0).toUpperCase();
    } else if (tipo === 'emoji') {
        avatarPreview.innerHTML = valor;
        avatarPreview.classList.add('emoji-avatar');
    } else if (tipo === 'premium') {
        avatarPreview.innerHTML = `<img src="/spring/img/${valor}" alt="Avatar Premium" style="width:100px;height:100px;object-fit:cover;">`;
        avatarPreview.classList.add('avatar-premium');
    }
}

// ===============================
// AVATARES GRATIS Y EMOJIS
// ===============================
avatarOptions.forEach(option => {
    option.addEventListener('click', () => {
        // Quitar selección de todos
        avatarOptions.forEach(opt => opt.classList.remove('selected'));
        option.classList.add('selected');

        const tipo = option.dataset.type;
        const valor = option.dataset.value;
        const avatarId = option.dataset.avatarId || 0;

        idAvatarInput.value = avatarId; // puede ser 0 para letra o emoji
        actualizarVistaPrevia(tipo, valor);
    });
});

// ===============================
// AVATARES PREMIUM COMPRADOS
// ===============================
document.querySelectorAll('.usar-btn').forEach(btn => {
    btn.addEventListener('click', () => {
        const avatarImg = btn.dataset.avatarImg;
        const avatarId = btn.dataset.avatarId;

        idAvatarInput.value = avatarId;
        actualizarVistaPrevia('premium', avatarImg);

        // Quitar selección de otros avatares gratis
        avatarOptions.forEach(opt => opt.classList.remove('selected'));
    });
});

// ===============================
// MODAL DE COMPRA
// ===============================
const modalCompra = new bootstrap.Modal(document.getElementById('confirmCompraModal'));
const confirmarCompraLink = document.getElementById('confirmarCompraBtn');

document.querySelectorAll('.comprar-btn').forEach(btn => {
    btn.addEventListener('click', e => {
        e.preventDefault();

        const avatarId = btn.dataset.avatarId;
        const avatarName = btn.dataset.avatarName;
        const avatarPrice = btn.dataset.avatarPrice;
        const avatarImg = btn.dataset.avatarImg;

        document.getElementById('modalAvatarName').textContent = avatarName;
        document.getElementById('modalAvatarPrice').textContent = avatarPrice;
        document.getElementById('modalAvatarImg').src = `/spring/img/${avatarImg}`;

        confirmarCompraLink.href = `/spring/perfil/avatar/comprar/${avatarId}`;
        modalCompra.show();
    });
});

// Validación de compra
confirmarCompraLink.addEventListener('click', function(e) {
    if (!this.href || this.href.endsWith('#')) {
        e.preventDefault();
        alert('Error: No se ha seleccionado un avatar válido');
    }
});

// ===============================
// ACTUALIZAR AVATAR POR LETRA AL CAMBIAR NOMBRE
// ===============================
usernameInput.addEventListener('input', () => {
    const letra = usernameInput.value.trim().charAt(0).toUpperCase() || 'U';
    const option = document.querySelector('[data-type="letter"]');

    if (option) {
        option.textContent = letra;
        if (option.classList.contains('selected')) {
            actualizarVistaPrevia('letter', letra);
        }
    }
});

// ===============================
// INICIALIZACIÓN DE VISTA PREVIA
// ===============================
