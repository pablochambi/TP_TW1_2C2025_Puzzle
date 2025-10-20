package com.tallerwebi.dominio;

import com.tallerwebi.dominio.enums.NIVEL;
import com.tallerwebi.dominio.implementacion.ServicioEstadisticaImpl;
import com.tallerwebi.dominio.interfaces.RepositorioPartida;
import com.tallerwebi.dominio.interfaces.ServicioEstadistica;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

public class ServicioEstadisticaTest {

    private ServicioEstadistica servicioEstadistica;
    private RepositorioPartida repositorioPartidaMock;
    private List<Partida> partidas;
    private Usuario usuarioMock;

    @BeforeEach
    public void init() {
        repositorioPartidaMock = mock(RepositorioPartida.class);
        servicioEstadistica = new ServicioEstadisticaImpl(repositorioPartidaMock);

        usuarioMock = new Usuario();
        usuarioMock.setId(1L);
        usuarioMock.setEmail("test@test.com");
        usuarioMock.setNombreUsuario("jugador123");
        usuarioMock.setMonedas(100);

        // Configurar partidas de nivel fácil
        partidas = new ArrayList<>();
        partidas.add(crearPartida(1L, usuarioMock, NIVEL.FACIL, true, 100));
        partidas.add(crearPartida(2L, usuarioMock, NIVEL.FACIL, true, 150));
        partidas.add(crearPartida(3L, usuarioMock, NIVEL.FACIL, false, 50));
        partidas.add(crearPartida(4L, usuarioMock, NIVEL.FACIL, true, 200));
        partidas.add(crearPartida(5L, usuarioMock, NIVEL.FACIL, false, 75));
    }

    // ==================== TESTS DE obtenerDeNivelFacil ====================

    @Test
    public void queAlObtenerEstadisticasDeNivelFacilRetorneMapaConDatosCorrectos() {
        when(repositorioPartidaMock.obtenerPartidasPorUsuarioYNivel(1L, NIVEL.FACIL))
                .thenReturn(partidas);

        Map<String, Object> estadisticas = servicioEstadistica.obtenerEstadisticas(1L, NIVEL.FACIL);

        assertThat(estadisticas, is(notNullValue()));
        assertThat(estadisticas.get("Partidas jugadas"), is(5));
        assertThat(estadisticas.get("Partidas ganadas"), is(3));
        assertThat(estadisticas.get("Partidas perdidas"), is(2));
        assertThat(estadisticas.get("Porcentaje de Victoria"), is("60,0%"));
    }

    @Test
    public void queAlObtenerEstadisticasFacilesSinPartidasNoRetorneUnMapaVacio() {
        when(repositorioPartidaMock.obtenerPartidasPorUsuarioYNivel(1L, NIVEL.FACIL))
                .thenReturn(new ArrayList<>());

        Map<String, Object> estadisticas = servicioEstadistica.obtenerEstadisticas(1L, NIVEL.FACIL);

        assertThat(estadisticas, is(notNullValue()));
        assertThat(estadisticas.isEmpty(), is(false));
    }

    // ==================== METODO AUXILIAR ====================

    private Partida crearPartida(Long id, Usuario usuario, NIVEL nivel, boolean ganada, Integer puntos) {
        Partida partida = new Partida();
        partida.setId(id);
        partida.setUsuario(usuario);
        partida.setNivel(nivel);
        partida.setGanada(ganada);
        partida.setPuntaje(puntos);
        return partida;
    }
}