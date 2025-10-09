package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.*;
import com.tallerwebi.dominio.excepcion.UsuarioInexistente;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class ServicioLoginTest {

    private ServicioLogin servicioLogin;
    private RepositorioUsuario repositorioUsuarioMock;
    private RepositorioAvatar repositorioAvatarMock;

    private Usuario usuarioMock;

    @BeforeEach
    public void init() {
        repositorioUsuarioMock = mock(RepositorioUsuario.class);
        repositorioAvatarMock = mock(RepositorioAvatar.class);
        servicioLogin = new ServicioLoginImpl(repositorioUsuarioMock,repositorioAvatarMock);

        usuarioMock = new Usuario(1L, "test@test.com", "jugador123", 100, "img/avatar/test.jpg");
        usuarioMock.setPassword("1234");
    }

    // ==================== TESTS DE obtenerUsuario ====================

    @Test
    public void queAlObtenerUnUsuarioPorId_retorneUnUsuario() {
        when(repositorioUsuarioMock.obtenerUsuarioPorId(usuarioMock.getId())).thenReturn(usuarioMock);

        Usuario usuario = servicioLogin.consultarUsuarioPorId(usuarioMock.getId());

        assertThat(usuario, is(usuarioMock));
        verify(repositorioUsuarioMock, times(1)).obtenerUsuarioPorId(usuario.getId());
    }

// ==================== TESTS DE actualizarPerfil ====================

    @Test
    public void queAlActualizarPerfilSeModifiquenTodosLosDatosCorrectamente() {
        String nuevoNombre = "nuevoNombre";
        Long idAvatar = 1L;
        String nuevaPassword = "nuevaPassword";

        when(repositorioUsuarioMock.obtenerUsuarioPorId(usuarioMock.getId())).thenReturn(usuarioMock);
        when(repositorioAvatarMock.buscarAvatarPorId(idAvatar)).thenReturn(new Avatar());

        doNothing().when(repositorioUsuarioMock).actualizarPerfil(usuarioMock.getId(), nuevoNombre, idAvatar, nuevaPassword);

        servicioLogin.actualizarPerfil(usuarioMock.getId(), nuevoNombre, idAvatar, nuevaPassword);

        verify(repositorioUsuarioMock, times(1)).actualizarPerfil(usuarioMock.getId(), nuevoNombre,idAvatar, nuevaPassword);
    }


    @Test
    public void queAlActualizarPerfilConUnaRutaDeLaNuevaImagenDePerfil_seGuardeComoRutaAEsaImagen() {
        String nuevoNombre = "nuevoNombre";
        Long idAvatar = 1L;
        String nuevaPassword = "nuevaPassword";

        when(repositorioUsuarioMock.obtenerUsuarioPorId(usuarioMock.getId())).thenReturn(usuarioMock);
        when(repositorioAvatarMock.buscarAvatarPorId(idAvatar)).thenReturn(new Avatar());

        doNothing().when(repositorioUsuarioMock).actualizarPerfil(usuarioMock.getId(), nuevoNombre, idAvatar,  nuevaPassword);

        servicioLogin.actualizarPerfil(usuarioMock.getId(), nuevoNombre, idAvatar, nuevaPassword);

        verify(repositorioUsuarioMock, times(1)).actualizarPerfil(usuarioMock.getId(), nuevoNombre, idAvatar,  nuevaPassword);
    }

}
