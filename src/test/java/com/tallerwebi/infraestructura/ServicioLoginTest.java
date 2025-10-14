package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.*;
import com.tallerwebi.dominio.excepcion.UsuarioExistente;
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
    private UsuarioDTO usuarioDTOMock;

    @BeforeEach
    public void init() {
        repositorioUsuarioMock = mock(RepositorioUsuario.class);
        repositorioAvatarMock = mock(RepositorioAvatar.class);
        servicioLogin = new ServicioLoginImpl(repositorioUsuarioMock, repositorioAvatarMock);

        usuarioMock = new Usuario(1L, "test@test.com", "jugador123", 100, "img/avatar/test.jpg");
        usuarioMock.setPassword("1234");
        usuarioDTOMock = new UsuarioDTO(usuarioMock, new Avatar());
    }

    // ==================== TESTS DE consultarUsuario ====================

    @Test
    void quePuedaConsultarUsuarioExistente() {
        // given
        when(repositorioUsuarioMock.buscarUsuario("test@test.com", "1234")).thenReturn(usuarioMock);

        // when
        Usuario usuarioEncontrado = servicioLogin.consultarUsuario("test@test.com", "1234");

        // then
        assertThat(usuarioEncontrado, is(notNullValue()));
        assertThat(usuarioEncontrado.getEmail(), equalTo("test@test.com"));
        verify(repositorioUsuarioMock).buscarUsuario("test@test.com", "1234");
    }

    // ==================== TESTS DE registrar ====================

    @Test
    void queSePuedaRegistrarUnNuevoUsuarioConAvataresGratuitosSiNoExiste() throws UsuarioExistente {
        // given
        when(repositorioUsuarioMock.buscarUsuario(usuarioMock.getEmail(), usuarioMock.getPassword()))
                .thenReturn(null);

        // when
        servicioLogin.registrar(usuarioMock);

        // then
        verify(repositorioUsuarioMock).guardar(usuarioMock);
        verify(repositorioAvatarMock).agregarAvataresGratuitosAlUsuario(usuarioMock);
    }

    @Test
    void queLanceExcepcionSiElUsuarioYaExiste() {
        // given
        when(repositorioUsuarioMock.buscarUsuario(usuarioMock.getEmail(), usuarioMock.getPassword()))
                .thenReturn(usuarioMock);

        // when / then
        assertThrows(UsuarioExistente.class, () -> servicioLogin.registrar(usuarioMock));

        verify(repositorioUsuarioMock, never()).guardar(any());
        verify(repositorioAvatarMock, never()).agregarAvataresGratuitosAlUsuario(any());
    }

    // ==================== TESTS DE actualizarPerfil ====================

    @Test
    void queActualiceElPerfilCorrectamente() {
        // given
        when(repositorioUsuarioMock.actualizarPerfil(1L, "NuevoNombre", 2L, "nuevaPass"))
                .thenReturn(usuarioDTOMock);

        // when
        UsuarioDTO UsuarioResultado = servicioLogin.actualizarPerfil(1L, "NuevoNombre", 2L, "nuevaPass");

        // then
        assertThat(UsuarioResultado, is(notNullValue()));
        verify(repositorioUsuarioMock).actualizarPerfil(1L, "NuevoNombre", 2L, "nuevaPass");
    }

    // ==================== TESTS DE consultarUsuarioPorId ====================

    @Test
    void queSePuedaObtenerUnUsuarioPorId() {
        // given
        when(repositorioUsuarioMock.obtenerUsuarioPorId(usuarioMock.getId()))
                .thenReturn(usuarioMock);

        // when
        Usuario usuarioEncontrado = servicioLogin.consultarUsuarioPorId(usuarioMock.getId());

        // then
        assertThat(usuarioEncontrado.getId(), equalTo(usuarioMock.getId()));
        verify(repositorioUsuarioMock).obtenerUsuarioPorId(usuarioMock.getId());
    }

    // ==================== TESTS DE obtenerMonedas ====================

    @Test
    void queObtengaCorrectamenteLasMonedasDelUsuario() {
        // given
        when(repositorioUsuarioMock.obtenerMonedasUsuario(1L)).thenReturn(250);

        // when
        Integer monedas = servicioLogin.obtenerMonedas(1L);

        // then
        assertThat(monedas, equalTo(250));
        verify(repositorioUsuarioMock).obtenerMonedasUsuario(1L);
    }

    // ==================== TESTS DE consultarUsuarioDTOPorId ====================

    @Test
    void queConstruyaUsuarioDTOConAvatarCorrectamente() {
        // given
        Avatar avatar = new Avatar();
        avatar.setNombre("AvatarGratis");

        when(repositorioUsuarioMock.obtenerUsuarioPorId(1L)).thenReturn(usuarioMock);
        when(repositorioAvatarMock.obtenerAvatarDelUsuario(usuarioMock)).thenReturn(avatar);

        // when
        UsuarioDTO usuarioDTOResultado = servicioLogin.consultarUsuarioDTOPorId(1L);

        // then
        assertThat(usuarioDTOResultado, is(notNullValue()));
        assertThat(usuarioDTOResultado.getNombreUsuario(), equalTo(usuarioMock.getNombreUsuario()));
        assertThat(usuarioDTOResultado.getAvatar().getNombre(), equalTo(avatar.getNombre()));
        verify(repositorioUsuarioMock).obtenerUsuarioPorId(1L);
        verify(repositorioAvatarMock).obtenerAvatarDelUsuario(usuarioMock);
    }
}
