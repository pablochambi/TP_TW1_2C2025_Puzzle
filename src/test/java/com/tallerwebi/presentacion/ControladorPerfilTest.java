package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Estadistica;
import com.tallerwebi.dominio.ServicioEstadistica;
import com.tallerwebi.dominio.ServicioLogin;
import com.tallerwebi.dominio.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;

public class ControladorPerfilTest {

    private ControladorPerfil controladorPerfil;
    private Usuario usuarioMock;
    private Map<String, String> estadisticasMock;

    private Map<String, String> listaEstadisticasMock;

    private HttpServletRequest requestMock;
    private HttpSession sessionMock;
    private ServicioLogin servicioLoginMock;
    private ServicioEstadistica servicioEstadisticasMock;


    // Inicializo los Mocks y el controlador para usar en cada test
    @BeforeEach
    public void init(){
        usuarioMock = new Usuario(1L,"test@test.com","jugador123",100);

        estadisticasMock = new LinkedHashMap<>();
        estadisticasMock.put("Tamanio Sudoku","4x4");
        estadisticasMock.put("Mejor tiempo","00:50");

        requestMock = mock(HttpServletRequest.class);
        sessionMock = mock(HttpSession.class);
        servicioLoginMock = mock(ServicioLogin.class);

        servicioEstadisticasMock = mock(ServicioEstadistica.class);
        controladorPerfil = new ControladorPerfil(servicioLoginMock,servicioEstadisticasMock);
    }

    @Test
    public void queAlIrAPerfil_SeMuestreLosDatosDelUsuarioYSusEstadisticas() {
        // preparacion
        when(sessionMock.getAttribute("id_usuario")).thenReturn(usuarioMock.getId());

        when(servicioLoginMock.obtenerEmail(usuarioMock.getId())).thenReturn(usuarioMock.getEmail());
        when(servicioLoginMock.obtenerMonedas(usuarioMock.getId())).thenReturn(usuarioMock.getMonedas());
        when(servicioLoginMock.obtenerNombreDeUsuario(usuarioMock.getId())).thenReturn(usuarioMock.getNombreUsuario());

        when(servicioEstadisticasMock.obtenerDeNivelFacil(usuarioMock.getId())).thenReturn(estadisticasMock);
        when(servicioEstadisticasMock.obtenerDeNivelMedio(usuarioMock.getId())).thenReturn(estadisticasMock);
        when(servicioEstadisticasMock.obtenerDeNivelDificil(usuarioMock.getId())).thenReturn(estadisticasMock);

        // ejecucion
        ModelAndView mav = controladorPerfil.irAlPerfil(sessionMock);

        // verificacion
        assertThat(mav.getViewName(), is("perfil"));
        assertThat(mav.getModel().get("email"), is("test@test.com"));
        assertThat(mav.getModel().get("nombreUsuario"), is("jugador123"));
        assertThat(mav.getModel().get("monedas"), is(100));

        assertThat(((Map<?, ?>) mav.getModel().get("estadisticasNivelFacil")).isEmpty(), is(false));
        assertThat(((Map<?, ?>) mav.getModel().get("estadisticasNivelMedio")).isEmpty(), is(false));
        assertThat(((Map<?, ?>) mav.getModel().get("estadisticasNivelDificil")).isEmpty(), is(false));

    }




}