// Accordion functionality
function toggleAccordion() {
    const accordion = document.querySelector('.accordion-menu');
    const userSection = document.querySelector('.user-section');

    accordion.classList.toggle('active');
    userSection.classList.toggle('active');
}

// Close accordion when clicking outside
document.addEventListener('click', function(event) {
    const userSection = document.querySelector('.user-section');
    const accordion = document.querySelector('.accordion-menu');

    if (!userSection.contains(event.target) && !accordion.contains(event.target)) {
        accordion.classList.remove('active');
        userSection.classList.remove('active');
    }
});

// Menu functions
// function viewProfile() {
//     alert('Abriendo perfil de usuario...');
//     closeAccordion();
// }
//
// function viewHistory() {
//     alert('Abriendo historial de partidas...');
//     closeAccordion();
// }
//
// function viewStats() {
//     alert('Abriendo estadísticas...');
//     closeAccordion();
// }
//
// function logout() {
//     alert('Cerrando sesión...');
//     closeAccordion();
// }

function closeAccordion() {
    const accordion = document.querySelector('.accordion-menu');
    const userSection = document.querySelector('.user-section');
    accordion.classList.remove('active');
    userSection.classList.remove('active');
}

function startGame(gameType) {
    alert(`Iniciando Sudoku ${gameType}...`);
}

// Close accordion on ESC key
document.addEventListener('keydown', function(event) {
    if (event.key === 'Escape') {
        closeAccordion();
    }
});

// Add smooth scroll behavior
document.documentElement.style.scrollBehavior = 'smooth';

// Add loading animation effect
window.addEventListener('load', function() {
    const elements = document.querySelectorAll('.fade-in-up');
    elements.forEach((element, index) => {
        setTimeout(() => {
            element.style.opacity = '1';
        }, index * 100);
    });
});

// Coin counter animation (optional enhancement)
function animateCoins() {
    const coinsElement = document.querySelector('.coins-display span');
    const currentValue = parseInt(coinsElement.textContent.replace(',', ''));
    // This could be used to animate coin changes
}