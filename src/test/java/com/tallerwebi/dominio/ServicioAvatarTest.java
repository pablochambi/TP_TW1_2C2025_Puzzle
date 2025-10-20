package com.tallerwebi.dominio;

import com.tallerwebi.dominio.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

public class ServicioAvatarTest {

    private ServicioAvatar servicioAvatar;
    private RepositorioAvatar repositorioAvatarMock;
    private RepositorioUsuario repositorioUsuario;
    private Repositorio_usuarioAvatar repositorio_usuarioAvatarMock;
    private Usuario usuarioMock;

    @BeforeEach
    public void init() {

        repositorioAvatarMock = mock(RepositorioAvatar.class);
        repositorio_usuarioAvatarMock = mock(Repositorio_usuarioAvatar.class);
        servicioAvatar = new ServicioAvatarImpl(repositorioAvatarMock,repositorio_usuarioAvatarMock,repositorioUsuario);

        usuarioMock = new Usuario();
        usuarioMock.setId(1L);
        usuarioMock.setEmail("test@test.com");
        usuarioMock.setNombreUsuario("jugador123");
        usuarioMock.setMonedas(100);
        usuarioMock.setPassword("1234");
    }

    @Test
    public void queSePuedaVerLosAvataresDisponibles() {
        when(repositorioAvatarMock.obtenerAvataresDisponibles()).thenReturn(
            List.of(
                new Avatar(1L, "Avatar1", "/img/avatar/avatar1.png"),
                new Avatar(2L, "Avatar2", "/img/avatar/avatar2.png"),
                new Avatar(3L, "Avatar3", "/img/avatar/avatar3.png")
            )
        );

        List<Avatar> listaAvatares = servicioAvatar.obtenerAvataresDisponibles();

        assertThat(listaAvatares.isEmpty(), is(false));
        assertThat(listaAvatares.size(), is(3));
    }


}
