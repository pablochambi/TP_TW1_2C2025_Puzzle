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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;

public class ControladorPerfilTest {

    private ControladorPerfil controladorPerfil;
    private Usuario usuarioMock;
    private Estadistica estadisticaMock;
    private HttpServletRequest requestMock;
    private HttpSession sessionMock;
    private ServicioLogin servicioLoginMock;
    private ServicioEstadistica servicioEstadisticasMock;

    @BeforeEach
    public void init(){
        usuarioMock = new Usuario(1L,"test@test.com",100);
        estadisticaMock = new Estadistica();
        requestMock = mock(HttpServletRequest.class);
        sessionMock = mock(HttpSession.class);
        servicioLoginMock = mock(ServicioLogin.class);

        servicioEstadisticasMock = mock(ServicioEstadistica.class);
        controladorPerfil = new ControladorPerfil(servicioLoginMock,servicioEstadisticasMock);
    }

    @Test
    public void queIrAlPerfil_SeMuestreLosDatosDelUsuarioYSusEstadisticas() {
        // preparacion
        when(sessionMock.getAttribute("id_usuario")).thenReturn(usuarioMock.getId());

        when(servicioLoginMock.obtenerEmail(usuarioMock.getId())).thenReturn(usuarioMock.getEmail());
        when(servicioLoginMock.obtenerMonedas(usuarioMock.getId())).thenReturn(usuarioMock.getMonedas());
        when(servicioLoginMock.obtenerNombreDeUsuario(usuarioMock.getId())).thenReturn(usuarioMock.getNombreUsuario());

//        when(servicioEstadisticasMock.obtenerMejorTiempoDelNivelFacil(usuarioMock.getId())).thenReturn("00:50");
//        when(servicioEstadisticasMock.obtenerPartidasGanadasDelNivelFacil(usuarioMock.getId())).thenReturn(5);
//        when(servicioEstadisticasMock.obtenerPartidasJugadasDelNivelFacil(usuarioMock.getId())).thenReturn(7);
//        when(servicioEstadisticasMock.obtenerPartidasPerdidas(usuarioMock.getId())).thenReturn(2);
//        when(servicioEstadisticasMock.obtenerRachaDeVictoriasDelNivelFacil(usuarioMock.getId())).thenReturn(5);

        // ejecucion
        ModelAndView mav = controladorPerfil.irAlPerfil(sessionMock);

        // validacion
        assertThat(mav.getViewName(), is("home"));
        assertThat(mav.getModel().get("email"), is("test@test.com"));
        assertThat(mav.getModel().get("nombre_usuario"), is("SudokuMaster24"));
        assertThat(mav.getModel().get("monedas"), is(100));

    }




}