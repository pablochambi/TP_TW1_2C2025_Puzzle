package com.tallerwebi.presentacion;

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

    private ControladorLogin controladorLogin;
    private Usuario usuarioMock;
    private DatosLogin datosLoginMock;
    private HttpServletRequest requestMock;
    private HttpSession sessionMock;
    private ServicioLogin servicioLoginMock;


    @BeforeEach
    public void init(){
        datosLoginMock = new DatosLogin("dami@unlam.com", "123");
        usuarioMock = mock(Usuario.class);
        requestMock = mock(HttpServletRequest.class);
        sessionMock = mock(HttpSession.class);
        servicioLoginMock = mock(ServicioLogin.class);
        controladorLogin = new ControladorLogin(servicioLoginMock);

        usuarioMock = new Usuario(1L,"test@test.com",100);
    }

    @Test
    public void queIrAlPerfilSeMuestreLosDatosDelUsuarioYSusEstadisticas() {
        // preparacion
        when(requestMock.getSession()).thenReturn(sessionMock);
        when(sessionMock.getAttribute("email")).thenReturn("test@test.com");
        when(sessionMock.getAttribute("monedas")).thenReturn(100);

        when(usuarioMock.getEmail()).thenReturn("dami@unlam.com");
        when(usuarioMock.getMonedas()).thenReturn(100);

        // ejecucion

        ModelAndView mav = controladorLogin.irAHome(sessionMock);

        //Validacion
        assertThat(mav.getViewName(), is("home"));
        assertThat(mav.getModel().get("email"), is("test@test.com"));
        assertThat(mav.getModel().get("monedas"), is(100));


    }




}