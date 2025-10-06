package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.*;
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
    private List<Partida> partidasFaciles;
    private List<Partida> partidasMedias;
    private List<Partida> partidasDificiles;
    private Usuario usuarioMock;

    @BeforeEach
    public void init() {
        repositorioPartidaMock = mock(RepositorioPartida.class);
        servicioEstadistica = new ServicioEstadisticaImpl(repositorioPartidaMock);

        usuarioMock = new Usuario(1L, "usuario1",100);

        // Configurar partidas de nivel fácil
        partidasFaciles = new ArrayList<>();
        partidasFaciles.add(crearPartida(1L, usuarioMock, "FACIL", true, 100));
        partidasFaciles.add(crearPartida(2L, usuarioMock, "FACIL", true, 150));
        partidasFaciles.add(crearPartida(3L, usuarioMock, "FACIL", false, 50));
        partidasFaciles.add(crearPartida(4L, usuarioMock, "FACIL", true, 200));
        partidasFaciles.add(crearPartida(5L, usuarioMock, "FACIL", false, 75));

        // Configurar partidas de nivel medio
        partidasMedias = new ArrayList<>();
        partidasMedias.add(crearPartida(6L, usuarioMock, "MEDIO", true, 200));
        partidasMedias.add(crearPartida(7L, usuarioMock, "MEDIO", false, 100));
        partidasMedias.add(crearPartida(8L, usuarioMock, "MEDIO", true, 250));
        partidasMedias.add(crearPartida(9L, usuarioMock, "MEDIO", true, 300));
        partidasMedias.add(crearPartida(10L, usuarioMock, "MEDIO", false, 150));

    }

    // ==================== TESTS DE obtenerDeNivelFacil ====================

    @Test
    public void queAlObtenerEstadisticasDeNivelFacilRetorneMapaConDatosCorrectos() {
        when(repositorioPartidaMock.obtenerPartidasPorUsuarioYNivel(1L, "FACIL"))
                .thenReturn(partidasFaciles);

        Map<String, Object> estadisticas = servicioEstadistica.obtenerEstadisticas(1L, "FACIL");

        assertThat(estadisticas, is(notNullValue()));
        assertThat(estadisticas.get("Partidas Jugadas"), is(5));
        assertThat(estadisticas.get("Partidas Ganadas"), is(3));
        assertThat(estadisticas.get("Partidas Perdidas"), is(2));
        assertThat(estadisticas.get("Porcentaje de Victoria"), is("60,0%"));
    }


    @Test
    public void queAlObtenerEstadisticasFacilesSinPartidasRetorneMapaVacio() {
        when(repositorioPartidaMock.obtenerPartidasPorUsuarioYNivel(1L, "FACIL"))
                .thenReturn(new ArrayList<>());

        Map<String, Object> estadisticas = servicioEstadistica.obtenerEstadisticas(1L, "FACIL");

        assertThat(estadisticas, is(notNullValue()));
        assertThat(estadisticas.isEmpty(), is(true));
    }


//
//    @Test
//    public void queElMapaDeEstadisticasMantenga_ElOrdenDeInsercion() {
//        when(repositorioPartidaMock.obtenerPartidasPorUsuarioYNivel(1L, "FACIL"))
//                .thenReturn(partidasFaciles);
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
//                .thenReturn(partidasFaciles);
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
        partida.setNivel(nivel);
        partida.setGanada(ganada);
        partida.setPuntaje(puntos);
        return partida;
    }
}
