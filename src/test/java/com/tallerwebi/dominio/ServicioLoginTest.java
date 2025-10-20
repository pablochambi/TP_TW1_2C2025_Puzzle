package com.tallerwebi.dominio;

import com.tallerwebi.dominio.*;
import com.tallerwebi.dominio.excepcion.UsuarioExistente;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.any;

public class ServicioLoginTest {

    private ServicioLogin servicioLogin;
    private RepositorioUsuario repositorioUsuarioMock;
    private RepositorioAvatar repositorioAvatarMock;
    private Repositorio_usuarioAvatar repositorio_usuarioAvatarMock;

    private Usuario usuarioMock;
    private Avatar avatarMock;
    private Usuario_Avatar usuarioAvatarMock;

    @BeforeEach
    public void init() {
        repositorioUsuarioMock = mock(RepositorioUsuario.class);
        repositorioAvatarMock = mock(RepositorioAvatar.class);
        repositorio_usuarioAvatarMock = mock(Repositorio_usuarioAvatar.class);

        servicioLogin = new ServicioLoginImpl(
                repositorioUsuarioMock,
                repositorioAvatarMock,
                repositorio_usuarioAvatarMock
        );

        usuarioMock = new Usuario();
        usuarioMock.setId(1L);
        usuarioMock.setEmail("test@test.com");
        usuarioMock.setNombreUsuario("jugador123");
        usuarioMock.setMonedas(100);
        usuarioMock.setPassword("1234");

        avatarMock = new Avatar();
        avatarMock.setId(5L);
        avatarMock.setPrecio(100);

        usuarioAvatarMock = new Usuario_Avatar();
        usuarioAvatarMock.setUsuario(usuarioMock);
        usuarioAvatarMock.setAvatar(avatarMock);
        usuarioAvatarMock.setEn_uso(false);
    }

    // ==================== consultarUsuario ====================

    @Test
    void quePuedaConsultarUsuarioExistente() {
        when(repositorioUsuarioMock.buscarUsuario("test@test.com", "1234")).thenReturn(usuarioMock);

        Usuario usuarioEncontrado = servicioLogin.consultarUsuario("test@test.com", "1234");

        assertThat(usuarioEncontrado, is(notNullValue()));
        assertThat(usuarioEncontrado.getEmail(), equalTo("test@test.com"));
        verify(repositorioUsuarioMock).buscarUsuario("test@test.com", "1234");
    }

    // ==================== registrar ====================

    @Test
    void quePuedaRegistrarUsuarioNuevo() throws UsuarioExistente {
        when(repositorioUsuarioMock.buscarUsuario(usuarioMock.getEmail(), usuarioMock.getPassword()))
                .thenReturn(null);

        servicioLogin.registrar(usuarioMock);

        verify(repositorioAvatarMock).agregarAvataresGratuitosAlUsuario(usuarioMock);
        verify(repositorioUsuarioMock).guardar(usuarioMock);
    }

    @Test
    void queNoPuedaRegistrarUsuarioExistente() {
        when(repositorioUsuarioMock.buscarUsuario(usuarioMock.getEmail(), usuarioMock.getPassword()))
                .thenReturn(usuarioMock);

        assertThrows(UsuarioExistente.class, () -> servicioLogin.registrar(usuarioMock));

        verify(repositorioUsuarioMock, never()).guardar(any());
    }


    // ==================== TEST: No hay avatar en uso y se selecciona uno nuevo ====================
    @Test
    void queSiNoHayAvatarEnUsoYSeSeleccionAvatarNuevoParaUsar_SoloSeActivaElNuevoAvatar() {
        // given
        when(repositorioUsuarioMock.obtenerUsuarioPorId(usuarioMock.getId())).thenReturn(usuarioMock);
        when(repositorio_usuarioAvatarMock.obtenerAvatarEnUsoDelUsuario(usuarioMock))
                .thenReturn(null); // no hay avatar en uso
        when(repositorioAvatarMock.buscarAvatarPorId(5L)).thenReturn(avatarMock);

        // la relación existe (usuario posee el avatar)
        Usuario_Avatar relacion = new Usuario_Avatar();
        relacion.setUsuario(usuarioMock);
        relacion.setAvatar(avatarMock);
        relacion.setEn_uso(false);
        when(repositorio_usuarioAvatarMock.obtenerRelacionUsuarioAvatar(usuarioMock, avatarMock))
                .thenReturn(relacion);

        // when
        UsuarioDTO dto = servicioLogin.actualizarPerfil(1L, "nombreSinAvatarPrevio", 5L, "passNueva");

        // then
        assertThat(dto, is(notNullValue()));
        assertThat(dto.getNombreUsuario(), equalTo("nombreSinAvatarPrevio"));
        assertThat(dto.getAvatar(), equalTo(avatarMock));

        verify(repositorioUsuarioMock).modificar(usuarioMock);
        // solo una llamada a actualizar: la que activa el nuevo avatar (no hubo desactivación previa)
        verify(repositorio_usuarioAvatarMock, times(1)).actualizar(any(Usuario_Avatar.class));
    }

    // ==================== TEST: Hay avatar en uso y NO se selecciona uno nuevo ====================
    @Test
    void queSiHayAvatarEnUsoYNoSeSeleccionaNuevoSoloSeDesactiveElActual() {
        // given
        when(repositorioUsuarioMock.obtenerUsuarioPorId(1L)).thenReturn(usuarioMock);
        // existe un avatar en uso
        usuarioAvatarMock.setEn_uso(true);
        when(repositorio_usuarioAvatarMock.obtenerAvatarEnUsoDelUsuario(usuarioMock))
                .thenReturn(usuarioAvatarMock);

        // when
        UsuarioDTO dto = servicioLogin.actualizarPerfil(1L, "nombreSoloCambioDatos", null, "otraPass");

        // then
        assertThat(dto, is(notNullValue()));
        assertThat(dto.getNombreUsuario(), equalTo("nombreSoloCambioDatos"));
        // como no se seleccionó nuevo avatar, el DTO no debe traer avatar
        assertThat(dto.getAvatar(), is(nullValue()));

        verify(repositorioUsuarioMock).modificar(usuarioMock);
        // solo una llamada a actualizar: la desactivación del avatar actual
        verify(repositorio_usuarioAvatarMock, times(1)).actualizar(usuarioAvatarMock);
    }

    // ==================== actualizarPerfil ====================

    @Test
    void queActualicePerfilYAvatarCorrectamente() {
        when(repositorioUsuarioMock.obtenerUsuarioPorId(1L)).thenReturn(usuarioMock);
        when(repositorio_usuarioAvatarMock.obtenerAvatarEnUsoDelUsuario(usuarioMock))
                .thenReturn(usuarioAvatarMock);
        when(repositorioAvatarMock.buscarAvatarPorId(5L)).thenReturn(avatarMock);
        when(repositorio_usuarioAvatarMock.obtenerRelacionUsuarioAvatar(usuarioMock, avatarMock))
                .thenReturn(usuarioAvatarMock);

        UsuarioDTO dto = servicioLogin.actualizarPerfil(1L, "nuevoNombre", 5L, "passNueva");

        assertThat(dto, is(notNullValue()));
        assertThat(dto.getNombreUsuario(), equalTo("nuevoNombre"));
        assertThat(dto.getAvatar(), equalTo(avatarMock));

        verify(repositorioUsuarioMock).modificar(usuarioMock);
        verify(repositorio_usuarioAvatarMock,times(2)).actualizar(usuarioAvatarMock);
    }

    @Test
    void queLanceExcepcionSiUsuarioNoExisteAlActualizarPerfil() {
        when(repositorioUsuarioMock.obtenerUsuarioPorId(99L)).thenReturn(null);

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> servicioLogin.actualizarPerfil(99L, "nuevo", 1L, "pass"));

        assertThat(ex.getMessage(), containsString("Usuario no encontrado"));
    }

    @Test
    void queLanceExcepcionSiAvatarNoExisteAlActualizarPerfil() {
        when(repositorioUsuarioMock.obtenerUsuarioPorId(1L)).thenReturn(usuarioMock);
        when(repositorioAvatarMock.buscarAvatarPorId(5L)).thenReturn(null);

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> servicioLogin.actualizarPerfil(1L, "nuevo", 5L, "pass"));

        assertThat(ex.getMessage(), containsString("Avatar no encontrado"));
    }

    @Test
    void queLanceExcepcionSiUsuarioNoPoseeElAvatarSeleccionado() {
        when(repositorioUsuarioMock.obtenerUsuarioPorId(1L)).thenReturn(usuarioMock);
        when(repositorioAvatarMock.buscarAvatarPorId(5L)).thenReturn(avatarMock);
        when(repositorio_usuarioAvatarMock.obtenerRelacionUsuarioAvatar(usuarioMock, avatarMock))
                .thenReturn(null);

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> servicioLogin.actualizarPerfil(1L, "nuevo", 5L, "pass"));

        assertThat(ex.getMessage(), containsString("no posee el avatar"));
    }

    // ==================== consultarUsuarioPorId ====================

    @Test
    void quePuedaConsultarUsuarioPorId() {
        when(repositorioUsuarioMock.obtenerUsuarioPorId(1L)).thenReturn(usuarioMock);

        Usuario usuario = servicioLogin.consultarUsuarioPorId(1L);

        assertThat(usuario, equalTo(usuarioMock));
        verify(repositorioUsuarioMock).obtenerUsuarioPorId(1L);
    }

    // ==================== obtenerMonedas ====================

    @Test
    void queObtengaCorrectamenteLasMonedasDelUsuario() {
        when(repositorioUsuarioMock.obtenerMonedasUsuario(1L)).thenReturn(150);

        Integer monedas = servicioLogin.obtenerMonedas(1L);

        assertThat(monedas, equalTo(150));
        verify(repositorioUsuarioMock).obtenerMonedasUsuario(1L);
    }

    // ==================== consultarUsuarioDTOPorId ====================

    @Test
    void queConsulteUsuarioDTOPorIdCorrectamente() {
        when(repositorioUsuarioMock.obtenerUsuarioPorId(1L)).thenReturn(usuarioMock);
        when(repositorioAvatarMock.obtenerAvatarDelUsuario(usuarioMock)).thenReturn(avatarMock);

        UsuarioDTO dto = servicioLogin.consultarUsuarioDTOPorId(1L);

        assertThat(dto, is(notNullValue()));
        assertThat(dto.getNombreUsuario(), equalTo(usuarioMock.getNombreUsuario()));
        assertThat(dto.getAvatar(), equalTo(avatarMock));

        verify(repositorioUsuarioMock).obtenerUsuarioPorId(1L);
        verify(repositorioAvatarMock).obtenerAvatarDelUsuario(usuarioMock);
    }
}
