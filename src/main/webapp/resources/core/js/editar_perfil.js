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

// Array para almacenar avatares comprados (simula persistencia)
let avataresPremiumComprados = [];

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
let avatarSeleccionadoParaCompra = null;

// Escuchadores para todos los botones de compra
document.querySelectorAll('.comprar-btn').forEach(btn => {
    btn.addEventListener('click', function(e) {
        e.preventDefault();

        // Obtener información del avatar
        const avatarName = this.getAttribute('data-avatar-name');
        const avatarPrice = this.getAttribute('data-avatar-price');
        const avatarImg = this.getAttribute('data-avatar-img');

        // Guardar referencia para la confirmación
        avatarSeleccionadoParaCompra = {
            name: avatarName,
            price: avatarPrice,
            img: avatarImg,
            button: this
        };

        // Actualizar contenido del modal
        document.getElementById('modalAvatarName').textContent = avatarName;
        document.getElementById('modalAvatarPrice').textContent = avatarPrice;
        document.getElementById('modalAvatarImg').src = `http://localhost:8080/spring/img/${avatarImg}`;

        // Mostrar modal
        modalCompra.show();
    });
});

// Confirmar la compra
document.getElementById('confirmarCompraBtn').addEventListener('click', function() {
    if (!avatarSeleccionadoParaCompra) return;

    // Simular proceso de compra (aquí harías la petición al backend)
    procesarCompra(avatarSeleccionadoParaCompra);

    // Cerrar modal
    modalCompra.hide();

    // Limpiar selección
    avatarSeleccionadoParaCompra = null;
});

// Función para procesar la compra
function procesarCompra(avatar) {
    // Agregar avatar a la lista de comprados
    avataresPremiumComprados.push(avatar.img);

    // Cambiar el botón de "Comprar" a "Usar"
    const btnComprar = avatar.button;
    const parentCard = btnComprar.closest('.card');

    // Reemplazar contenido del botón
    btnComprar.className = 'btn btn-primary usar-btn mt-2 d-flex align-items-center justify-content-center';
    btnComprar.innerHTML = 'Usar';

    // Guardar datos del avatar en el botón
    btnComprar.setAttribute('data-avatar-type', 'premium');
    btnComprar.setAttribute('data-avatar-img', avatar.img);

    // Remover evento de compra
    const newBtn = btnComprar.cloneNode(true);
    btnComprar.parentNode.replaceChild(newBtn, btnComprar);

    // Agregar evento "Usar" al nuevo botón
    newBtn.addEventListener('click', function(e) {
        e.preventDefault();
        usarAvatarPremium(this);
    });
}

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
// SUBIR IMAGEN PERSONALIZADA
// ===============================

// if (avatarUpload) {
//     avatarUpload.addEventListener('change', function(e) {
//         const file = e.target.files[0];
//         if (!file) return;
//
//         // Validar tipo de archivo
//         if (!file.type.match(/^image\/(jpeg|jpg|png)$/)) {
//             alert('❌ Por favor selecciona una imagen JPG o PNG');
//             return;
//         }
//
//         // Validar tamaño (2MB máximo)
//         if (file.size > 2 * 1024 * 1024) {
//             alert('❌ La imagen debe ser menor a 2MB');
//             return;
//         }
//
//         // Leer archivo
//         const reader = new FileReader();
//         reader.onload = function(e) {
//             // Crear opción personalizada si no existe
//             let customOption = document.querySelector('[data-type="image"]');
//             if (!customOption) {
//                 customOption = document.createElement('div');
//                 customOption.className = 'avatar-option';
//                 customOption.setAttribute('data-type', 'image');
//                 customOption.setAttribute('title', 'Imagen personalizada');
//                 document.querySelector('.avatar-options').appendChild(customOption);
//             }
//
//             // Actualizar imagen
//             customOption.innerHTML = `<img src="${e.target.result}" alt="Personalizada">`;
//             customOption.setAttribute('data-value', e.target.result);
//
//             // Seleccionar automáticamente
//             currentAvatarType = 'image';
//             currentAvatarValue = e.target.result;
//
//             selectAvatar(customOption);
//         };
//         reader.readAsDataURL(file);
//     });
// }

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

