package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ServicioTiendaMonedas;
import com.tallerwebi.dominio.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

/*
.Agregar monedas correctamente
.Monedas no se duplican al actualizar
 */

public class ControladorTiendaMonedasTest {


    ServicioTiendaMonedas servicioTiendaMonedas = mock(ServicioTiendaMonedas.class);
    ControladorTiendaMonedas controladorTiendaMonedas = new ControladorTiendaMonedas(servicioTiendaMonedas);
    HttpServletRequest requestMock =  mock(HttpServletRequest.class);
    HttpSession sessionMock =  mock(HttpSession.class);
    String collection_Id = "COLL_1234";
    Usuario usuario;


    @BeforeEach
    public void init() {
        usuario = new Usuario(1L, "dami@unlam.com", 100);

    }

    @Test
    public void dadoQueHayUnUsuarioYHaceUnPagoExitosoEntoncesMuestraMensajeCorrecto() {
     //GIVEN
        when(requestMock.getSession()).thenReturn(sessionMock);
        when(sessionMock.getAttribute("id_usuario")).thenReturn(usuario.getId());



        //WHEN
        ModelAndView resultado = controladorTiendaMonedas.pagoExitoso(1, collection_Id, requestMock);

        //THEN
        assertThat(resultado.getViewName(), equalTo("tienda-monedas"));
        assertThat(resultado.getModel().get("exitoPago"), notNullValue());
        assertThat((String) resultado.getModel().get("exitoPago"), containsString("exitosa"));




    }


    @Test
    public void dadoQueHayUnUsuarioYHaceUnPagoExitosoEntoncesMuestraLasMonedasCorrectas(){
        //GIVEN
        when(requestMock.getSession()).thenReturn(sessionMock);
        when(sessionMock.getAttribute("id_usuario")).thenReturn(usuario.getId());
        doNothing().when(servicioTiendaMonedas).comprarPaquete(usuario.getId(), 1, "1234");
        when(servicioTiendaMonedas.obtenerMonedasUsuario(usuario.getId())).thenReturn(1100);

        //WHEN
        ModelAndView resultado = controladorTiendaMonedas.pagoExitoso(1, collection_Id, requestMock);

        //THEN
        assertThat(resultado.getViewName(), equalTo("tienda-monedas"));
        assertThat(resultado.getModel().get("exitoPago"), notNullValue());
        assertThat((String) resultado.getModel().get("exitoPago"), containsString("✅"));
        assertThat(resultado.getModel().get("monedas"), equalTo(1100));





    }



    @Test
    public void dadoUnPagoFallidoMuestraMensajeDeErrorYMonedas(){
        // GIVEN
        when(requestMock.getSession()).thenReturn(sessionMock);
        when(sessionMock.getAttribute("id_usuario")).thenReturn(usuario.getId());
        when(servicioTiendaMonedas.obtenerMonedasUsuario(usuario.getId())).thenReturn(100);

        // WHEN
        ModelAndView resultado = controladorTiendaMonedas.pagoFallido(1, requestMock);

        // THEN
        assertThat(resultado.getViewName(), equalTo("tienda-monedas"));
        assertThat(resultado.getModel().get("errorPago"), notNullValue());
        assertThat((String) resultado.getModel().get("errorPago"), containsString("❌"));
        assertThat(resultado.getModel().get("monedas"), equalTo(100));
    }











}
