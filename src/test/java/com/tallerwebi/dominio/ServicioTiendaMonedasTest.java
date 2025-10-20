package com.tallerwebi.dominio;

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
    String collection_Id;

    @BeforeEach
    public void init() {
        repositorioPago = mock(RepositorioPago.class);
        repositorioUsuario = mock(RepositorioUsuario.class);
        servicioMonedas = new ServicioTiendaMonedasImpl(repositorioUsuario, repositorioPago);

        usuario = new Usuario();
        usuario.setId(1L);
        usuario.setEmail("dami@unlam.com");
        usuario.setNombreUsuario("jugador123");
        usuario.setMonedas(100);

        collection_Id = "1234";

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
        servicioMonedas.comprarPaquete(usuario.getId(), 1, collection_Id);


        //THEN
        assertThat(usuario.getMonedas(), equalTo(1100));



    }

    @Test
    public void dadoQueTengoUnUsuarioYHagoUnaCompraDelPrimerPaqueteObtengoElPagoEsteQuedaRegistrado(){
       //given
        when(repositorioUsuario.obtenerUsuarioPorId(usuario.getId())).thenReturn(usuario);
        servicioMonedas.comprarPaquete(usuario.getId(), 1, collection_Id);
        when(repositorioPago.obtenerPagoPorCollectionId(collection_Id)).thenReturn("1234");

        //when
        String resultado = servicioMonedas.obtenerPago("1234");

        //then
        assertThat(resultado, notNullValue());



    }




}
