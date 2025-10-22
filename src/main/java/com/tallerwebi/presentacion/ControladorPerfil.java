package com.tallerwebi.presentacion;
import com.tallerwebi.dominio.*;
import com.tallerwebi.dominio.enums.NIVEL;
import com.tallerwebi.dominio.excepcion.FormatoDeAvatarInvalido;
import com.tallerwebi.dominio.interfaces.ServicioAvatar;
import com.tallerwebi.dominio.interfaces.ServicioEstadistica;
import com.tallerwebi.dominio.interfaces.ServicioLogin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
public class ControladorPerfil {

    private final ServicioEstadistica servicioEstadistica;
    private final ServicioLogin servicioLogin;
    private final ServicioAvatar servicioAvatar;

    @Autowired
    public ControladorPerfil(ServicioLogin servicioLogin, ServicioEstadistica servicioEstadistica, ServicioAvatar servicioAvatar) {
        this.servicioLogin = servicioLogin;
        this.servicioEstadistica = servicioEstadistica;
        this.servicioAvatar = servicioAvatar;
    }

    @RequestMapping(path = "/perfil", method = RequestMethod.GET)
    public ModelAndView irAlPerfil(HttpSession session) {
        Long idUsuario = obtenerIdUsuarioDeSesion(session);
        if (idUsuario == null) {
            return new ModelAndView("redirect:/login");
        }
        ModelMap model = new ModelMap();
        model.put("usuario",servicioLogin.consultarUsuarioDTOPorId(idUsuario));
        model.put("estadisticasNivelFacil", servicioEstadistica.obtenerEstadisticas(idUsuario, NIVEL.FACIL));
        model.put("estadisticasNivelMedio", servicioEstadistica.obtenerEstadisticas(idUsuario, NIVEL.MEDIA));
        model.put("estadisticasNivelDificil", servicioEstadistica.obtenerEstadisticas(idUsuario, NIVEL.DIFICIL));

        return new ModelAndView("perfil", model);
    }

    @RequestMapping(path = "/perfil/editar", method = RequestMethod.GET)
    public ModelAndView irAEditarPerfil(HttpSession session) {
        Long idUsuario = obtenerIdUsuarioDeSesion(session);
        if (idUsuario == null) {
            return new ModelAndView("redirect:/login");
        }
        ModelMap model = new ModelMap();

        model.put("usuario",servicioLogin.consultarUsuarioDTOPorId(idUsuario));
        model.put("lista_avatares", servicioAvatar.obtenerAvataresDTO(idUsuario));

        return new ModelAndView("editar_perfil", model);
    }

    @GetMapping("/perfil/avatar/comprar/{avatarId}")
    public ModelAndView comprarAvatar(@PathVariable("avatarId") Long avatarId, HttpSession session){

        Long idUsuario = obtenerIdUsuarioDeSesion(session);
        if (idUsuario == null) {
            return new ModelAndView("redirect:/login");
        }
        ModelMap model = new ModelMap();
        try {
            servicioAvatar.comprarAvatar(idUsuario, avatarId);
            return new ModelAndView("redirect:/perfil/editar");
        } catch (Exception e) {
            model.put("error", "Error al comprar el avatar: " + e.getMessage());
            return new ModelAndView("error", model);
        }
    }

    @RequestMapping(path = "/perfil/guardar", method = RequestMethod.POST)
    public ModelAndView guardarPerfil(@RequestParam("nombreUsuario") String nuevoNombre,
                                      @RequestParam("id_avatar") Long id_avatar,
                                      @RequestParam("password") String nuevaPassword,
                                      HttpSession sessionMock) {

        Long idUsuario = obtenerIdUsuarioDeSesion(sessionMock);
        if (idUsuario == null) {
            return new ModelAndView("redirect:/login");
        }

        ModelMap model = new ModelMap();

        try {
            UsuarioDTO usuarioDTO = servicioLogin.actualizarPerfil(idUsuario, nuevoNombre, id_avatar, nuevaPassword);
            model.put("usuario",servicioLogin.consultarUsuarioDTOPorId(idUsuario));

        } catch (FormatoDeAvatarInvalido e) {
            model.put("error", "Formato de avatar inválido");
            return new ModelAndView("error", model);
        }catch (Exception e){
            model.put("error", "Error al actualizar el perfil: " + e.getMessage());
            return new ModelAndView("error", model);
        }

        return new ModelAndView("redirect:/perfil");
    }

    // --- Métodos privados reutilizables ---
    private Long obtenerIdUsuarioDeSesion(HttpSession session) {
        return (Long) session.getAttribute("id_usuario");
    }

    @RequestMapping(path = "/perfil/volver", method = RequestMethod.GET)
    public ModelAndView volverAlPerfil(HttpSession session) {
        if(obtenerIdUsuarioDeSesion(session) == null) {
            return new ModelAndView("redirect:/login");
        }

        return new ModelAndView("redirect:/perfil");
    }

}
