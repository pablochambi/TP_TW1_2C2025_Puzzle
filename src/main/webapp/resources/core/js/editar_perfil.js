// Referencias a elementos
const usernameInput = document.getElementById('username');
const passwordInput = document.getElementById('password');
const avatarPreview = document.getElementById('avatarPreview');
const avatarUpload = document.getElementById('avatarUpload'); //Subir imagen personalizada
const avatarOptions = document.querySelectorAll('.avatar-option'); //Opciones de avatar

// Valores originales
let currentAvatarType = 'letter';
let currentAvatarValue = 'auto';

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

// Escuchador para opciones de avatar
avatarOptions.forEach(option => {
    option.addEventListener('click', () => selectAvatar(option));
});

// Subir imagen personalizada
avatarUpload.addEventListener('change', function(e) {
    const file = e.target.files[0];
    if (!file) return;

    // Validar tipo de archivo
    if (!file.type.match(/^image\/(jpeg|jpg|png)$/)) {
        alert('❌ Por favor selecciona una imagen JPG o PNG');
        return;
    }

    // Validar tamaño (2MB máximo)
    if (file.size > 2 * 1024 * 1024) {
        alert('❌ La imagen debe ser menor a 2MB');
        return;
    }

    // Leer archivo
    const reader = new FileReader();
    reader.onload = function(e) {
        // Crear opción personalizada si no existe
        let customOption = document.querySelector('[data-type="image"]');
        if (!customOption) {
            customOption = document.createElement('div');
            customOption.className = 'avatar-option';
            customOption.setAttribute('data-type', 'image');
            customOption.setAttribute('title', 'Imagen personalizada');
            document.querySelector('.avatar-options').appendChild(customOption);
        }

        // Actualizar imagen
        customOption.innerHTML = `<img src="${e.target.result}" alt="Personalizada">`;

        customOption.setAttribute('data-value', e.target.result);

        // Seleccionar automáticamente
        currentAvatarType = 'image';
        currentAvatarValue = e.target.result;

        selectAvatar(customOption);
    };
    reader.readAsDataURL(file);
});

// Escuchador del input de username para actualizar avatar de letra
usernameInput.addEventListener('input', function() {
    if (currentAvatarType === 'letter') {
        actualizarVistaPreviaDeAvatar();
    }
});

// Inicializar
actualizarVistaPreviaDeAvatar();