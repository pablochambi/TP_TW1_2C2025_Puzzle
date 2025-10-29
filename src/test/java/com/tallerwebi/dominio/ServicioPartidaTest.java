package com.tallerwebi.dominio;

import com.tallerwebi.dominio.implementacion.ServicioAvatarImpl;
import com.tallerwebi.dominio.implementacion.ServicioPartidaImpl;
import com.tallerwebi.dominio.interfaces.*;
import org.junit.jupiter.api.BeforeEach;

import static org.mockito.Mockito.mock;

public class ServicioPartidaTest {

    private ServicioPartida servicioPartida;
    private RepositorioPartida repositorioPartidaMock;
    private RepositorioUsuario repositorioUsuarioMock;

    private Usuario usuarioMock;

    @BeforeEach
    public void init() {

        repositorioPartidaMock = mock(RepositorioPartida.class);
        repositorioUsuarioMock = mock(RepositorioUsuario.class);
        servicioPartida = new ServicioPartidaImpl(repositorioPartidaMock,repositorioUsuarioMock);

        usuarioMock = new Usuario();
        usuarioMock.setId(1L);
        usuarioMock.setEmail("test@test.com");
        usuarioMock.setNombreUsuario("jugador123");
        usuarioMock.setMonedas(100);
        usuarioMock.setPassword("1234");
    }








}
