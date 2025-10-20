package com.tallerwebi.dominio;

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
        partidas.add(crearPartida(1L, usuarioMock, "FACIL", true, 100));
        partidas.add(crearPartida(2L, usuarioMock, "FACIL", true, 150));
        partidas.add(crearPartida(3L, usuarioMock, "FACIL", false, 50));
        partidas.add(crearPartida(4L, usuarioMock, "FACIL", true, 200));
        partidas.add(crearPartida(5L, usuarioMock, "FACIL", false, 75));


    }

    // ==================== TESTS DE obtenerDeNivelFacil ====================

    @Test
    public void queAlObtenerEstadisticasDeNivelFacilRetorneMapaConDatosCorrectos() {
        when(repositorioPartidaMock.obtenerPartidasPorUsuarioYNivel(1L, "FACIL"))
                .thenReturn(partidas);

        Map<String, Object> estadisticas = servicioEstadistica.obtenerEstadisticas(1L, "FACIL");

        assertThat(estadisticas, is(notNullValue()));
        assertThat(estadisticas.get("Partidas jugadas"), is(5));
        assertThat(estadisticas.get("Partidas ganadas"), is(3));
        assertThat(estadisticas.get("Partidas perdidas"), is(2));
        assertThat(estadisticas.get("Porcentaje de Victoria"), is("60,0%"));
    }


    @Test
    public void queAlObtenerEstadisticasFacilesSinPartidasNoRetorneUnMapaVacio() {
        when(repositorioPartidaMock.obtenerPartidasPorUsuarioYNivel(1L, "FACIL"))
                .thenReturn(new ArrayList<>());

        Map<String, Object> estadisticas = servicioEstadistica.obtenerEstadisticas(1L, "FACIL");

        assertThat(estadisticas, is(notNullValue()));
        assertThat(estadisticas.isEmpty(), is(false));
    }


//
//    @Test
//    public void queElMapaDeEstadisticasMantenga_ElOrdenDeInsercion() {
//        when(repositorioPartidaMock.obtenerPartidasPorUsuarioYNivel(1L, "FACIL"))
//                .thenReturn(partidas);
//
//        Map<String, Object> estadisticas = servicioEstadistica.obtenerDeNivelFacil(1L);
//
//        List<String> keys = new ArrayList<>(estadisticas.keySet());
//        assertThat(keys.get(0), is("Partidas Jugadas"));
//        assertThat(keys.get(1), is("Partidas Ganadas"));
//        assertThat(keys.get(2), is("Partidas Perdidas"));
//        assertThat(keys.get(3), is("Porcentaje de Victoria"));
//    }

//
//    @Test
//    public void queAlObtenerEstadisticasDeDistintosUsuariosRetorneDatosIndependientes() {
//        List<Partida> partidasUsuario2 = new ArrayList<>();
//        partidasUsuario2.add(crearPartida(20L, 2L, "FACIL", true, 100));
//
//        when(repositorioPartidaMock.obtenerPartidasPorUsuarioYNivel(1L, "FACIL"))
//                .thenReturn(partidas);
//        when(repositorioPartidaMock.obtenerPartidasPorUsuarioYNivel(2L, "FACIL"))
//                .thenReturn(partidasUsuario2);
//
//        Map<String, Object> estadisticasUsuario1 = servicioEstadistica.obtenerDeNivelFacil(1L);
//        Map<String, Object> estadisticasUsuario2 = servicioEstadistica.obtenerDeNivelFacil(2L);
//
//        assertThat(estadisticasUsuario1.get("Partidas Jugadas"), is(5));
//        assertThat(estadisticasUsuario2.get("Partidas Jugadas"), is(1));
//    }


    // ==================== METODO AUXILIAR ====================

    private Partida crearPartida(Long id, Usuario usuario, String nivel, boolean ganada, Integer puntos) {
        Partida partida = new Partida();
        partida.setId(id);
        partida.setUsuario(usuario);
        partida.setDificultad(nivel);
        partida.setGanada(ganada);
        partida.setPuntaje(puntos);
        return partida;
    }
}
