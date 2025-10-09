package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

public class ControladorPerfilTest {

    private ControladorPerfil controladorPerfil;
    private Usuario usuarioMock;

    private HttpServletRequest requestMock;
    private HttpSession sessionMock;
    private ServicioLogin servicioLoginMock;
    private ServicioEstadistica servicioEstadisticasMock;
    private ServicioAvatar servicioAvatarMock;


    private Map<String, Object> estadisticasFacilMock;
    private Map<String, Object> estadisticasMedioMock;
    private Map<String, Object> estadisticasDificilMock;


    // Inicializo los Mocks y el controlador para usar en cada test
    @BeforeEach
    public void init(){
        usuarioMock = new Usuario(1L,"test@test.com","jugador123",100,"img/avatar/test.jpg");
        usuarioMock.setPassword("1234");

        requestMock = mock(HttpServletRequest.class);
        sessionMock = mock(HttpSession.class);
        servicioLoginMock = mock(ServicioLogin.class);
        servicioEstadisticasMock = mock(ServicioEstadistica.class);
        servicioAvatarMock = mock(ServicioAvatar.class);

        when(requestMock.getSession()).thenReturn(sessionMock);
        controladorPerfil = new ControladorPerfil(servicioLoginMock,servicioEstadisticasMock,servicioAvatarMock);


        // Simulacion de Estadisticas por Nivel
        estadisticasFacilMock = new LinkedHashMap<>();
        estadisticasFacilMock.put("Partidas Jugadas", "5");
        estadisticasFacilMock.put("Partidas Ganadas", "4");

        estadisticasMedioMock = new LinkedHashMap<>();
        estadisticasMedioMock.put("Partidas Jugadas", "5");
        estadisticasMedioMock.put("Partidas Ganadas", "3");

        estadisticasDificilMock = new LinkedHashMap<>();
        estadisticasDificilMock.put("Partidas Jugadas", "5");
        estadisticasDificilMock.put("Partidas Ganadas", "2");
    }

    // ==================== TESTS DE /perfil ====================

    @Test
    public void queAlAccederAlPerfilSinSesionRedirijaAlLogin() {
        when(sessionMock.getAttribute("id_usuario")).thenReturn(null);

        ModelAndView mav = controladorPerfil.irAlPerfil(sessionMock);

        assertThat(mav.getViewName(), is("redirect:/login"));
    }

    @Test
    public void queAlAccederAlPerfilConSesionValidaMuestreLaVistaPerfil() {

        when(sessionMock.getAttribute("id_usuario")).thenReturn(usuarioMock.getId());

        ModelAndView mav = controladorPerfil.irAlPerfil(sessionMock);

        assertThat(mav.getViewName(), is("perfil"));
        assertThat(mav.getModel(), is(notNullValue()));
    }

    @Test
    public void queAlAccederAlPerfilSeCarguenLosDatosBasicosDelUsuario() {

        when(sessionMock.getAttribute("id_usuario")).thenReturn(usuarioMock.getId());
        when(servicioLoginMock.consultarUsuarioPorId(usuarioMock.getId())).thenReturn(usuarioMock);

        ModelAndView mav = controladorPerfil.irAlPerfil(sessionMock);

        assertThat(mav.getViewName(), is("perfil"));
        assertThat(mav.getModel().get("usuario"), is(usuarioMock));
    }


    @Test
    public void queAlAccederAlPerfilSeCarguenLasEstadisticasDeTodosLosNiveles() {


        when(sessionMock.getAttribute("id_usuario")).thenReturn(usuarioMock.getId());

        when(servicioEstadisticasMock.obtenerEstadisticas(usuarioMock.getId(),"FACIL")).thenReturn(estadisticasFacilMock);
        when(servicioEstadisticasMock.obtenerEstadisticas(usuarioMock.getId(),"MEDIO")).thenReturn(estadisticasMedioMock);
        when(servicioEstadisticasMock.obtenerEstadisticas(usuarioMock.getId(),"DIFICIL")).thenReturn(estadisticasDificilMock);

        ModelAndView mav = controladorPerfil.irAlPerfil(sessionMock);

        assertThat(mav.getModel().get("estadisticasNivelFacil"), is(estadisticasFacilMock));
        assertThat(mav.getModel().get("estadisticasNivelMedio"), is(estadisticasMedioMock));
        assertThat(mav.getModel().get("estadisticasNivelDificil"), is(estadisticasDificilMock));
    }


    @Test
    public void queAlAccederAlPerfilSeLlamenLosServiciosCorrectamente() {
        Long idUsuario = 1L;
        when(sessionMock.getAttribute("id_usuario")).thenReturn(idUsuario);

        controladorPerfil.irAlPerfil(sessionMock);

        verify(servicioLoginMock, times(1)).consultarUsuarioPorId(idUsuario);
        verify(servicioEstadisticasMock, times(1)).obtenerEstadisticas(idUsuario,"FACIL");
        verify(servicioEstadisticasMock, times(1)).obtenerEstadisticas(idUsuario,"MEDIO");
        verify(servicioEstadisticasMock, times(1)).obtenerEstadisticas(idUsuario,"DIFICIL");
    }

    @Test
    public void queAlAccederAlPerfilConEstadisticasVaciasNoFalle() {
        Long idUsuario = 1L;
        when(sessionMock.getAttribute("id_usuario")).thenReturn(idUsuario);

        when(servicioEstadisticasMock.obtenerEstadisticas(idUsuario,"FACIL")).thenReturn(new HashMap<>());
        when(servicioEstadisticasMock.obtenerEstadisticas(idUsuario,"MEDIO")).thenReturn(new HashMap<>());
        when(servicioEstadisticasMock.obtenerEstadisticas(idUsuario,"DIFICIL")).thenReturn(new HashMap<>());

        ModelAndView mav = controladorPerfil.irAlPerfil(sessionMock);

        assertThat(mav.getViewName(), is("perfil"));

        assertThat(mav.getModel().get("estadisticasNivelFacil"), is(notNullValue()));
        assertThat(mav.getModel().get("estadisticasNivelMedio"),  is(notNullValue()));
        assertThat(mav.getModel().get("estadisticasNivelDificil"),  is(notNullValue()));
    }


    // ==================== TESTS DE /perfil/editar GET (Ir a Editar) ====================

    @Test
    public void queAlIrAEditarPerfil_SeMuestreLosDatosBasicosDelUsuarioYSuPassword() {
        // preparacion
        when(sessionMock.getAttribute("id_usuario")).thenReturn(usuarioMock.getId());
        when(servicioLoginMock.consultarUsuarioPorId(usuarioMock.getId())).thenReturn(usuarioMock);

        // ejecucion
        ModelAndView mav = controladorPerfil.irAEditarPerfil(sessionMock);

        // verificacion
        assertThat(mav.getViewName(), is("editar_perfil"));
        assertThat(mav.getModel().get("usuario"), is(usuarioMock));
        assertThat(((Usuario) mav.getModel().get("usuario")).getPassword(), is("1234"));
    }


    @Test
    public void queAlGuardarCambiosDelPerfilSinSesionRedirijaAlLogin() {
        when(sessionMock.getAttribute("id_usuario")).thenReturn(null);

        ModelAndView mav = controladorPerfil.guardarPerfil("nuevoNombre", "nuevoAvatar", "nuevaPassword", sessionMock);

        assertThat(mav.getViewName(), is("redirect:/login"));
        verify(servicioLoginMock, never()).actualizarPerfil(any(), any(), any(), any());
    }

    @Test
    public void queAlIrAEditarPerfil_SeMuestreLasImagenesDeLosAvatares() {
        // preparacion
        when(sessionMock.getAttribute("id_usuario")).thenReturn(usuarioMock.getId());
        when(servicioAvatarMock.obtenerAvataresDisponibles()).thenReturn( (List.of(new Avatar())) );

        // ejecucion
        ModelAndView mav = controladorPerfil.irAEditarPerfil(sessionMock);

        // verificacion
        assertThat(mav.getViewName(), is("editar_perfil"));
        assertThat(mav.getModel().get("lista_avatares"),  instanceOf(List.class));
        assertThat(((List<?>)mav.getModel().get("lista_avatares")).isEmpty(), is(false));

        verify(servicioAvatarMock, times(1)).obtenerAvataresDisponibles();
    }

    @Test
    public void queAlGuardarCambiosDelPerfilSeActualicenLosDatosYRedirijaAlPerfil() {
        Long idUsuario = 1L;
        String nuevoNombre = "nuevoUsuario";
        String nuevoAvatar = "img/avatar/test.jpg";
        String nuevaPassword = "nuevaPassword123";

        Usuario usuarioActualizado = new Usuario();
        usuarioActualizado.setId(idUsuario);
        usuarioActualizado.setNombreUsuario(nuevoNombre);
        usuarioActualizado.setUrlAvatar(nuevoAvatar);
        usuarioActualizado.setPassword(nuevaPassword);

        when(sessionMock.getAttribute("id_usuario")).thenReturn(idUsuario);
        when(servicioLoginMock.consultarUsuarioPorId(idUsuario)).thenReturn(usuarioActualizado);

        doNothing().when(servicioLoginMock).actualizarPerfil(idUsuario, nuevoNombre, nuevoAvatar, nuevaPassword);

        ModelAndView mav = controladorPerfil.guardarPerfil(nuevoNombre, nuevoAvatar, nuevaPassword, sessionMock);

        assertThat(mav.getViewName(), is("redirect:/perfil"));
        assertThat(((Usuario)mav.getModel().get("usuario")).getNombreUsuario(), is(usuarioActualizado.getNombreUsuario()));
        verify(servicioLoginMock, times(1)).actualizarPerfil(idUsuario, nuevoNombre, nuevoAvatar, nuevaPassword);
    }



    // ==================== TESTS DE /perfil/volver (Volver sin guardar) ====================

    @Test
    public void queAlVolverAlPerfilDesdeEditarSinSesionRedirijaAlLogin() {
        when(sessionMock.getAttribute("id_usuario")).thenReturn(null);

        ModelAndView mav = controladorPerfil.volverAlPerfil(sessionMock);

        assertThat(mav.getViewName(),is("redirect:/login") );
    }

    @Test
    public void queAlVolverAlPerfilDesdeEditarRedirijaAlPerfilSinGuardarCambios() {
        Long idUsuario = 1L;
        when(sessionMock.getAttribute("id_usuario")).thenReturn(idUsuario);

        ModelAndView mav = controladorPerfil.volverAlPerfil(sessionMock);

        assertThat(mav.getViewName(), is("redirect:/perfil"));
        verify(servicioLoginMock, never()).actualizarPerfil(any(), any(), any(), any());
    }


}