package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Partida;
import org.junit.jupiter.api.Test;

public class ControladorGameTest {

    @Test
    public void mostrarPartida() {
        whenSeIniciaUnaPartida();
        thenMuestraLaVistaDeGame();
    }

    private void whenSeIniciaUnaPartida() {
        Partida partida = new Partida();
    }

    private void thenMuestraLaVistaDeGame() {
    }
}
