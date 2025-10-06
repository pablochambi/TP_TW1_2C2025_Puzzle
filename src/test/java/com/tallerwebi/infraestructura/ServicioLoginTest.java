package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.RepositorioUsuario;
import com.tallerwebi.dominio.ServicioLogin;
import com.tallerwebi.dominio.ServicioLoginImpl;
import com.tallerwebi.dominio.Usuario;
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
    private Usuario usuarioMock;

    @BeforeEach
    public void init() {
        repositorioUsuarioMock = mock(RepositorioUsuario.class);
        servicioLogin = new ServicioLoginImpl(repositorioUsuarioMock);

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
        String nuevaUrl = "nuevaUrl.jpg";
        String nuevaPassword = "nuevaPassword";

        doNothing().when(repositorioUsuarioMock).actualizarPerfil(usuarioMock.getId(), nuevoNombre, nuevaUrl, nuevaPassword);

        servicioLogin.actualizarPerfil(usuarioMock.getId(), nuevoNombre, nuevaUrl, nuevaPassword);

        verify(repositorioUsuarioMock, times(1)).actualizarPerfil(usuarioMock.getId(), nuevoNombre, nuevaUrl, nuevaPassword);
    }

    @Test
    public void queAlActualizarPerfilConUsuarioInexistenteLanceUsuarioInexistente() {
        String nuevoNombre = "nuevoNombre";
        String nuevaUrl = "nuevaUrl.jpg";
        String nuevaPassword = "nuevaPassword";

        doThrow(UsuarioInexistente.class).when(repositorioUsuarioMock).actualizarPerfil(999L, nuevoNombre, nuevaUrl, nuevaPassword);

        assertThrows(UsuarioInexistente.class, () -> {
            servicioLogin.actualizarPerfil(999L, nuevoNombre, nuevaUrl, nuevaPassword);
        });
        verify(repositorioUsuarioMock, times(1)).actualizarPerfil(999L, nuevoNombre, nuevaUrl, nuevaPassword);
    }

}
