package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Ranking;
import com.tallerwebi.dominio.Usuario;
import com.tallerwebi.dominio.UsuarioDTO;
import com.tallerwebi.dominio.interfaces.ServicioLogin;
import com.tallerwebi.dominio.interfaces.ServicioPartida;
import com.tallerwebi.dominio.interfaces.ServicioRanking;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

public class ControladorRankingTest {

    private ServicioLogin servicioLoginMock;
    private ServicioPartida servicioPartidaMock;
    private ServicioRanking servicioRankingMock;
    private ControladorRanking controladorRanking;
    private HttpSession sessionMock;
    private Usuario usuario;
    private UsuarioDTO usuarioDTO;

    @BeforeEach
    public void init() {
        // Crear mocks
        servicioLoginMock = mock(ServicioLogin.class);
        servicioPartidaMock = mock(ServicioPartida.class);
        servicioRankingMock = mock(ServicioRanking.class);
        sessionMock = mock(HttpSession.class);

        // Crear controlador
        controladorRanking = new ControladorRanking(servicioLoginMock, servicioPartidaMock, servicioRankingMock);


        usuario = new Usuario();
        usuario.setId(1L);
        usuario.setEmail("jugador@test.com");
        usuario.setNombreUsuario("jugador123");
        usuario.setMonedas(150);

        usuarioDTO = new UsuarioDTO();
        usuarioDTO.setId(1L);
        usuarioDTO.setNombreUsuario("jugador123");
        usuarioDTO.setEmail("jugador@test.com");
        usuarioDTO.setMonedas(150);
    }



    @Test
    public void dadoQueHayUnUsuarioLogeadoDeberiaRetornarVistaRanking() {
        // GIVEN
        when(sessionMock.getAttribute("id_usuario")).thenReturn(usuario.getId());
        when(servicioLoginMock.obtenerMonedas(usuario.getId())).thenReturn(150);
        when(servicioLoginMock.consultarUsuarioDTOPorId(usuario.getId())).thenReturn(usuarioDTO);
        when(servicioRankingMock.obtenerRanking("GENERAL", "PUNTAJE")).thenReturn(new ArrayList<>());

        // WHEN
        ModelAndView resultado = controladorRanking.irARanking("GENERAL", "PUNTAJE", sessionMock);

        // THEN
        assertThat(resultado.getViewName(), equalTo("ranking"));
        assertThat(resultado.getModel().get("monedas"), equalTo(150));
        assertThat(resultado.getModel().get("usuario"), notNullValue());
    }

    @Test
    public void dadoQueNoHayUsuarioLogeadoDeberiaRedirigirALogin() {
        // GIVEN
        when(sessionMock.getAttribute("id_usuario")).thenReturn(null);

        // WHEN
        ModelAndView resultado = controladorRanking.irARanking("GENERAL", "PUNTAJE", sessionMock);

        // THEN
        assertThat(resultado.getViewName(), equalTo("redirect:/login"));
    }


}
