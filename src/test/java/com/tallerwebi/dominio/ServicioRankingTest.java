package com.tallerwebi.dominio;

import com.tallerwebi.dominio.PartidaDTO;
import com.tallerwebi.dominio.Ranking;
import com.tallerwebi.dominio.UsuarioDTO;
import com.tallerwebi.dominio.implementacion.ServicioRankingImpl;
import com.tallerwebi.dominio.interfaces.ServicioPartida;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

public class ServicioRankingTest {

    private ServicioPartida servicioPartidaMock;
    private ServicioRankingImpl servicioRanking;

    @BeforeEach
    public void init() {
        servicioPartidaMock = mock(ServicioPartida.class);
        servicioRanking = new ServicioRankingImpl(servicioPartidaMock);
    }

    // ============================================
    // TESTS PARA: obtenerRanking() - RANKING GENERAL
    // ============================================

    @Test
    public void dadoQueHayPartidasDeberiaRetornarRankingGeneral() {
        // GIVEN
        List<PartidaDTO> partidasMock = crearListaPartidasMock(5);
        when(servicioPartidaMock.obtenerPartidasPorCriterio("GENERAL", "PUNTAJE"))
                .thenReturn(partidasMock);

        // WHEN
        List<Ranking> resultado = servicioRanking.obtenerRanking("GENERAL", "PUNTAJE");

        // THEN
        assertThat(resultado, notNullValue());
        assertThat(resultado.size(), equalTo(5));
        verify(servicioPartidaMock, times(1)).obtenerPartidasPorCriterio("GENERAL", "PUNTAJE");
    }

    @Test
    public void deberiaAsignarPosicionesCorrectamenteEnRankingGeneral() {
        // GIVEN
        List<PartidaDTO> partidasMock = crearListaPartidasMock(3);
        when(servicioPartidaMock.obtenerPartidasPorCriterio("GENERAL", "PUNTAJE"))
                .thenReturn(partidasMock);

        // WHEN
        List<Ranking> resultado = servicioRanking.obtenerRanking("GENERAL", "PUNTAJE");

        // THEN
        assertThat(resultado.get(0).getPosicion(), equalTo(1));
        assertThat(resultado.get(1).getPosicion(), equalTo(2));
        assertThat(resultado.get(2).getPosicion(), equalTo(3));
    }

    // ============================================
    // TESTS PARA: obtenerRanking() - POR DIFICULTAD
    // ============================================

    @Test
    public void deberiaFiltrarRankingPorDificultadFacil() {
        // GIVEN
        List<PartidaDTO> partidasFaciles = crearListaPartidasMock(4);
        when(servicioPartidaMock.obtenerPartidasPorCriterio("FACIL", "PUNTAJE"))
                .thenReturn(partidasFaciles);

        // WHEN
        List<Ranking> resultado = servicioRanking.obtenerRanking("FACIL", "PUNTAJE");

        // THEN
        assertThat(resultado.size(), equalTo(4));
        verify(servicioPartidaMock).obtenerPartidasPorCriterio("FACIL", "PUNTAJE");
    }

    @Test
    public void deberiaFiltrarRankingPorDificultadMedia() {
        // GIVEN
        List<PartidaDTO> partidasMedias = crearListaPartidasMock(3);
        when(servicioPartidaMock.obtenerPartidasPorCriterio("MEDIA", "PUNTAJE"))
                .thenReturn(partidasMedias);

        // WHEN
        List<Ranking> resultado = servicioRanking.obtenerRanking("MEDIA", "PUNTAJE");

        // THEN
        assertThat(resultado.size(), equalTo(3));
        verify(servicioPartidaMock).obtenerPartidasPorCriterio("MEDIA", "PUNTAJE");
    }

    @Test
    public void deberiaFiltrarRankingPorDificultadDificil() {
        // GIVEN
        List<PartidaDTO> partidasDificiles = crearListaPartidasMock(5);
        when(servicioPartidaMock.obtenerPartidasPorCriterio("DIFICIL", "PUNTAJE"))
                .thenReturn(partidasDificiles);

        // WHEN
        List<Ranking> resultado = servicioRanking.obtenerRanking("DIFICIL", "PUNTAJE");

        // THEN
        assertThat(resultado.size(), equalTo(5));
        verify(servicioPartidaMock).obtenerPartidasPorCriterio("DIFICIL", "PUNTAJE");
    }

    // ============================================
    // TESTS PARA: obtenerRanking() - POR ORDEN
    // ============================================

    @Test
    public void deberiaOrdenarRankingPorPuntaje() {
        // GIVEN
        List<PartidaDTO> partidasOrdenadas = crearListaPartidasMock(5);
        when(servicioPartidaMock.obtenerPartidasPorCriterio("GENERAL", "PUNTAJE"))
                .thenReturn(partidasOrdenadas);

        // WHEN
        List<Ranking> resultado = servicioRanking.obtenerRanking("GENERAL", "PUNTAJE");

        // THEN
        assertThat(resultado, notNullValue());
        verify(servicioPartidaMock).obtenerPartidasPorCriterio("GENERAL", "PUNTAJE");
    }

    @Test
    public void deberiaOrdenarRankingPorTiempo() {
        // GIVEN
        List<PartidaDTO> partidasOrdenadas = crearListaPartidasMock(5);
        when(servicioPartidaMock.obtenerPartidasPorCriterio("GENERAL", "TIEMPO"))
                .thenReturn(partidasOrdenadas);

        // WHEN
        List<Ranking> resultado = servicioRanking.obtenerRanking("GENERAL", "TIEMPO");

        // THEN
        assertThat(resultado, notNullValue());
        verify(servicioPartidaMock).obtenerPartidasPorCriterio("GENERAL", "TIEMPO");
    }

    @Test
    public void deberiaOrdenarRankingPorPartidas() {
        // GIVEN
        List<PartidaDTO> partidasOrdenadas = crearListaPartidasMock(5);
        when(servicioPartidaMock.obtenerPartidasPorCriterio("GENERAL", "PARTIDAS"))
                .thenReturn(partidasOrdenadas);

        // WHEN
        List<Ranking> resultado = servicioRanking.obtenerRanking("GENERAL", "PARTIDAS");

        // THEN
        assertThat(resultado, notNullValue());
        verify(servicioPartidaMock).obtenerPartidasPorCriterio("GENERAL", "PARTIDAS");
    }


    @Test
    public void deberiaLimitarRankingA10PosicionesCuandoHayMasDe10Partidas() {
        // GIVEN
        List<PartidaDTO> muchasPartidas = crearListaPartidasMock(15);
        when(servicioPartidaMock.obtenerPartidasPorCriterio("GENERAL", "PUNTAJE"))
                .thenReturn(muchasPartidas);

        // WHEN
        List<Ranking> resultado = servicioRanking.obtenerRanking("GENERAL", "PUNTAJE");

        // THEN
        assertThat(resultado.size(), equalTo(10)); // Máximo 10 posiciones
    }

    @Test
    public void deberiaRetornarMenosDe10SiHayMenosPartidas() {
        // GIVEN
        List<PartidaDTO> pocasPartidas = crearListaPartidasMock(7);
        when(servicioPartidaMock.obtenerPartidasPorCriterio("GENERAL", "PUNTAJE"))
                .thenReturn(pocasPartidas);

        // WHEN
        List<Ranking> resultado = servicioRanking.obtenerRanking("GENERAL", "PUNTAJE");

        // THEN
        assertThat(resultado.size(), equalTo(7));
    }


    @Test
    public void deberiaMapearCorrectamenteLosDatosDePartidaARanking() {
        // GIVEN
        List<PartidaDTO> partidas = new ArrayList<>();
        PartidaDTO partidaDTO = crearPartidaDTO("ProPlayer", 1000, "02:30", 15, "MEDIA");
        partidas.add(partidaDTO);

        when(servicioPartidaMock.obtenerPartidasPorCriterio("GENERAL", "PUNTAJE"))
                .thenReturn(partidas);

        // WHEN
        List<Ranking> resultado = servicioRanking.obtenerRanking("GENERAL", "PUNTAJE");

        // THEN
        Ranking primerPuesto = resultado.get(0);
        assertThat(primerPuesto.getNombreJugador(), equalTo("ProPlayer"));
        assertThat(primerPuesto.getMejorPuntaje(), equalTo(1000));
        assertThat(primerPuesto.getMejorTiempo(), equalTo("02:30"));
        assertThat(primerPuesto.getPartidasGanadas(), equalTo(15));
        assertThat(primerPuesto.getDificultad(), equalTo("MEDIA"));
    }

    @Test
    public void deberiaAsignarPosicionCorrectaACadaJugador() {
        // GIVEN
        List<PartidaDTO> partidas = new ArrayList<>();
        partidas.add(crearPartidaDTO("Jugador1", 1000, "02:00", 20, "MEDIA"));
        partidas.add(crearPartidaDTO("Jugador2", 900, "02:30", 18, "MEDIA"));
        partidas.add(crearPartidaDTO("Jugador3", 800, "03:00", 15, "MEDIA"));

        when(servicioPartidaMock.obtenerPartidasPorCriterio("GENERAL", "PUNTAJE"))
                .thenReturn(partidas);

        // WHEN
        List<Ranking> resultado = servicioRanking.obtenerRanking("GENERAL", "PUNTAJE");

        // THEN
        assertThat(resultado.get(0).getPosicion(), equalTo(1));
        assertThat(resultado.get(0).getNombreJugador(), equalTo("Jugador1"));

        assertThat(resultado.get(1).getPosicion(), equalTo(2));
        assertThat(resultado.get(1).getNombreJugador(), equalTo("Jugador2"));

        assertThat(resultado.get(2).getPosicion(), equalTo(3));
        assertThat(resultado.get(2).getNombreJugador(), equalTo("Jugador3"));
    }


    private List<PartidaDTO> crearListaPartidasMock(int cantidad) {
        List<PartidaDTO> partidas = new ArrayList<>();

        for (int i = 0; i < cantidad; i++) {
            String nombreJugador = "Jugador" + (i + 1);
            Integer puntaje = 1000 - (i * 100);
            String tiempo = "0" + (i + 2) + ":00";
            Integer partidasGanadas = 20 - i;

            partidas.add(crearPartidaDTO(nombreJugador, puntaje, tiempo, partidasGanadas, "media"));
        }

        return partidas;
    }

    private PartidaDTO crearPartidaDTO(String nombreJugador, Integer puntaje,
                                       String tiempo, Integer partidasGanadas,
                                       String dificultad) {
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setNombreUsuario(nombreJugador);
        usuarioDTO.setPartidasGanadas(partidasGanadas);
        PartidaDTO partidaDTO = new PartidaDTO(dificultad, tiempo, puntaje, true, 0,"2025r",usuarioDTO);


        partidaDTO.setUsuarioDTO(usuarioDTO);
        partidaDTO.setPuntaje(puntaje);
        partidaDTO.setTiempoFormateado(tiempo);
        partidaDTO.setDificultad(dificultad);

        return partidaDTO;
    }
}
