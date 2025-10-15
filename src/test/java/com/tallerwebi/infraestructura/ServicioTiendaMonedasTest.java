package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.ArgumentMatchers.isNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ServicioTiendaMonedasTest {

    HttpServletRequest requestMock =  mock(HttpServletRequest.class);
    HttpSession sessionMock =  mock(HttpSession.class);
    ServicioTiendaMonedas servicioMonedas;
    RepositorioUsuario repositorioUsuario;
    RepositorioPago repositorioPago;
    Usuario usuario;

    @BeforeEach
    public void init() {
        repositorioPago = mock(RepositorioPago.class);
        repositorioUsuario = mock(RepositorioUsuario.class);
        servicioMonedas = new ServicioTiendaMonedasImpl(repositorioUsuario, repositorioPago);
        usuario = new Usuario(1L, "dami@unlam.com", 100);

    }

    /*
    *Comprar paquete
    * ObtenerMonedasUsuario
    * RegistrarPago
    * ObtenerPago
    * ObtenerPaqueteId
     */

    @Test
    public void dadoQueTengoUnUsuarioYHagoUnaCompraDelPrimerPaqueteLasMonedasDelUsuarioSeActualizan() {
        //GIVEN
        when(repositorioUsuario.obtenerUsuarioPorId(usuario.getId())).thenReturn(usuario);


        //WHEN
        servicioMonedas.comprarPaquete(usuario.getId(), 1);


        //THEN
        assertThat(usuario.getMonedas(), equalTo(1100));



    }

    @Test
    public void dadoQueTengoUnUsuarioYHaceUnPagoCuandoObtengoElPagoEsteQuedaRegistrado(){
       //given
        servicioMonedas.registrarPago("COLL_1234",usuario.getId(), 1);
        when(repositorioPago.obtenerPagoPorCollectionId("COLL_1234")).thenReturn("COLL_1234");

        //when
        String resultado = servicioMonedas.obtenerPago("COLL_1234");

        //then
        assertThat(resultado, notNullValue());



    }




}
