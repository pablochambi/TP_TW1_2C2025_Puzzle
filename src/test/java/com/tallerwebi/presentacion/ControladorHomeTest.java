package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ServicioLogin;
import com.tallerwebi.dominio.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ControladorHomeTest {

    private ControladorHome controladorHome;
    private HttpServletRequest requestMock;
    private HttpSession sessionMock;
    private ServicioLogin servicioLoginMock;
    private Usuario usuario;

        @BeforeEach
        public void init(){
            servicioLoginMock = mock(ServicioLogin.class);
            controladorHome = new ControladorHome(servicioLoginMock);
            requestMock = mock(HttpServletRequest.class);
            sessionMock = mock(HttpSession.class);

            usuario = new Usuario();
            usuario.setId(1L);
            usuario.setEmail("dami@unlam.com");
            usuario.setNombreUsuario("jugador123");
            usuario.setMonedas(100);

        }

        @Test
        public void dadoQueHayUnUsuarioLogeadoCuandoSeIngresaATiendaMonedasDeberiaIrATiendaMonedas(){

            // prep

            when(requestMock.getSession()).thenReturn(sessionMock);
            when(sessionMock.getAttribute("id_usuario")).thenReturn(usuario.getId());

            //ejec

            ModelAndView resultado = controladorHome.irATiendaMonedas(requestMock);

            //assert

            assertThat(resultado.getViewName(), equalTo("tienda-monedas"));
        }

    @Test
    public void dadoQueHayUnUsuarioNoLogeadoCuandoSeIngresaATiendaMonedasDeberiaIrALogin(){

            when(requestMock.getSession()).thenReturn(sessionMock);
            when(sessionMock.getAttribute("id_usuario")).thenReturn(null);

            ModelAndView resultado = controladorHome.irATiendaMonedas(requestMock);

            assertThat(resultado.getViewName(), equalTo("redirect:/login"));

        }
        @Test
        public void dadoQueHayUnUsuarioLogeadoCon100MonedasCuandoSeDirigeATiendaMonedasSeMuestranSusMonedas(){

            when(requestMock.getSession()).thenReturn(sessionMock);
            when(sessionMock.getAttribute("id_usuario")).thenReturn(usuario.getId());
            when(servicioLoginMock.consultarUsuarioPorId(usuario.getId())).thenReturn(usuario);

            ModelAndView resultado = controladorHome.irATiendaMonedas(requestMock);


            assertThat(resultado.getViewName(), equalTo("tienda-monedas"));
            assertThat(((Usuario)resultado.getModel().get("usuario")).getMonedas(), equalTo(usuario.getMonedas()));


    }











}












