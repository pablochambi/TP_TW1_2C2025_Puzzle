


// Inicializar letra del avatar al cargar la página
document.addEventListener('DOMContentLoaded', function() {
    const UserNameActual = document.getElementById('profileName').textContent;
    actualizarAvatarDesdeUserName(UserNameActual);
});

function actualizarAvatarDesdeUserName(username) {
    const avatar = document.getElementById('userAvatar');
    const firstLetter = username.trim().charAt(0).toUpperCase();
    avatar.textContent = firstLetter || 'U'; // 'U' por defecto si está vacío
}

// Función para observar cambios en el nombre (útil si se actualiza dinámicamente)
function observarCambioDelUserName() {
    const profileNameElement = document.getElementById('profileName');

    // Crear un observer para detectar cambios en el texto del nombre
    const observer = new MutationObserver(function(mutations) {
        mutations.forEach(function(mutation) {
            if (mutation.type === 'childList' || mutation.type === 'characterData') {
                const newUsername = profileNameElement.textContent;
                actualizarAvatarDesdeUserName(newUsername);
            }
        });
    });

    // Configurar el observer
    observer.observe(profileNameElement, {
        childList: true,
        characterData: true,
        subtree: true
    });
}

// Inicializar observer al cargar la página
document.addEventListener('DOMContentLoaded', function() {
    observarCambioDelUserName();
});


window.addEventListener("pageshow", function (event) {
    // Si la página fue cargada desde caché del navegador (por "Atrás")
    if (event.persisted) {
        window.location.reload(); // fuerza recarga desde el servidor
    }
});

window.addEventListener("popstate", function (event) {
    // Cuando el usuario presiona la flecha "Atrás"
    window.location.href = "/spring/home"; // <-- Cambiá por tu controlador real
});