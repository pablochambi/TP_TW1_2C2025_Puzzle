
    // Variables del juego
    let gameTimer = 0;
    let timerInterval = null;
    let isPaused = false;
    let selectedCell = null;
    let hintsRemaining = 3;

    // Tablero ejemplo (0 = vacío)
    const initialBoard = [
    [5, 3, 0, 0, 7, 0, 0, 0, 0],
    [6, 0, 0, 1, 9, 5, 0, 0, 0],
    [0, 9, 8, 0, 0, 0, 0, 6, 0],
    [8, 0, 0, 0, 6, 0, 0, 0, 3],
    [4, 0, 0, 8, 0, 3, 0, 0, 1],
    [7, 0, 0, 0, 2, 0, 0, 0, 6],
    [0, 6, 0, 0, 0, 0, 2, 8, 0],
    [0, 0, 0, 4, 1, 9, 0, 0, 5],
    [0, 0, 0, 0, 8, 0, 0, 7, 9]
    ];

    // Inicializar juego
    function initGame() {
    createBoard();
    startTimer();
    setupEventListeners();
}

    // Crear tablero
    function createBoard() {
    const grid = document.getElementById('sudokuGrid');
    grid.innerHTML = '';

    for (let row = 0; row < 9; row++) {
    for (let col = 0; col < 9; col++) {
    const cell = document.createElement('div');
    cell.className = 'sudoku-cell';
    cell.dataset.row = row;
    cell.dataset.col = col;

    const value = initialBoard[row][col];
    if (value !== 0) {
    cell.textContent = value;
    cell.classList.add('given');
}

    grid.appendChild(cell);
}
}
}

    // Event listeners
    function setupEventListeners() {
    // Celdas del tablero
    document.addEventListener('click', function(e) {
        if (e.target.classList.contains('sudoku-cell')) {
            selectCell(e.target);
        }
    });

    // Botones numéricos
    document.querySelectorAll('.number-btn[data-number]').forEach(btn => {
    btn.addEventListener('click', function() {
    if (selectedCell && !isPaused) {
    placeNumber(this.dataset.number);
}
});
});

    // Botón borrar
    document.getElementById('eraseBtn').addEventListener('click', function() {
    if (selectedCell && !isPaused) {
    eraseCell();
}
});

    // Botón pausa
    document.getElementById('pauseBtn').addEventListener('click', togglePause);
    document.getElementById('resumeBtn').addEventListener('click', togglePause);

    // Botón pista
    document.getElementById('hintBtn').addEventListener('click', useHint);

    // Botón abandonar
    document.getElementById('abandonBtn').addEventListener('click', abandonGame);
}

    // Seleccionar celda
    function selectCell(cell) {
    if (cell.classList.contains('given') || isPaused) return;

    // Quitar selección anterior
    const prevSelected = document.querySelector('.sudoku-cell.selected');
    if (prevSelected) {
    prevSelected.classList.remove('selected');
}

    // Seleccionar nueva celda
    cell.classList.add('selected');
    selectedCell = cell;
}

    // Colocar número
    function placeNumber(number) {
    if (selectedCell && !selectedCell.classList.contains('given')) {
    selectedCell.textContent = number;
    selectedCell.classList.remove('hint');
}
}

    // Borrar celda
    function eraseCell() {
    if (selectedCell && !selectedCell.classList.contains('given')) {
    selectedCell.textContent = '';
    selectedCell.classList.remove('hint');
}
}

    // Cronómetro
    function startTimer() {
    timerInterval = setInterval(function() {
        if (!isPaused) {
            gameTimer++;
            updateTimerDisplay();
        }
    }, 1000);
}

    function updateTimerDisplay() {
    const minutes = Math.floor(gameTimer / 60);
    const seconds = gameTimer % 60;
    document.getElementById('timer').textContent =
    String(minutes).padStart(2, '0') + ':' + String(seconds).padStart(2, '0');
}

    // Pausar/reanudar
    function togglePause() {
    isPaused = !isPaused;
    const pauseBtn = document.getElementById('pauseBtn');
    const pauseOverlay = document.getElementById('pauseOverlay');

    if (isPaused) {
    pauseBtn.textContent = '▶️';
    pauseOverlay.classList.add('active');
} else {
    pauseBtn.textContent = '⏸️';
    pauseOverlay.classList.remove('active');
}
}

    // Usar pista
    function useHint() {
    if (hintsRemaining <= 0 || isPaused) return;

    const emptyCells = Array.from(document.querySelectorAll('.sudoku-cell:not(.given)'))
    .filter(cell => !cell.textContent);

    if (emptyCells.length > 0) {
    const randomCell = emptyCells[Math.floor(Math.random() * emptyCells.length)];
    const hintNumber = Math.floor(Math.random() * 9) + 1; // Número aleatorio

    randomCell.textContent = hintNumber;
    randomCell.classList.add('hint');

    hintsRemaining--;
    document.getElementById('hintsCount').textContent = hintsRemaining;

    if (hintsRemaining <= 0) {
    document.getElementById('hintBtn').disabled = true;
}
}
}

    // Abandonar partida
    function abandonGame() {
    if (confirm('¿Seguro que quieres abandonar esta partida?')) {
    clearInterval(timerInterval);
    alert('Partida abandonada');
    // Aquí redirigirías o reiniciarías
}
}

    // Iniciar cuando cargue la página
    document.addEventListener('DOMContentLoaded', initGame);
