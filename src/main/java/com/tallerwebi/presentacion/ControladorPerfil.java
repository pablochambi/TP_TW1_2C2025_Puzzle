package com.tallerwebi.presentacion;


import com.tallerwebi.dominio.ServicioEstadistica;
import com.tallerwebi.dominio.ServicioLogin;
import com.tallerwebi.dominio.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
public class ControladorPerfil {

    private final ServicioEstadistica servicioEstadistica;
    private final ServicioLogin servicioLogin;

    @Autowired
    public ControladorPerfil(ServicioLogin servicioLogin, ServicioEstadistica servicioEstadistica) {
        this.servicioLogin = servicioLogin;
        this.servicioEstadistica = servicioEstadistica;
    }

    @RequestMapping(path = "/perfil", method = RequestMethod.GET)
    public ModelAndView irAlPerfil(HttpSession session) {
        Long idUsuario = obtenerIdUsuarioDeSesion(session);
        if (idUsuario == null) {
            return new ModelAndView("redirect:/login");
        }
        ModelMap model = new ModelMap();
        model.put("usuario",servicioLogin.consultarUsuarioPorId(idUsuario));
        model.put("estadisticasNivelFacil", servicioEstadistica.obtenerEstadisticas(idUsuario, "FACIL"));
        model.put("estadisticasNivelMedio", servicioEstadistica.obtenerEstadisticas(idUsuario, "MEDIO"));
        model.put("estadisticasNivelDificil", servicioEstadistica.obtenerEstadisticas(idUsuario, "DIFICIL"));

        return new ModelAndView("perfil", model);
    }


    @RequestMapping(path = "/perfil/editar", method = RequestMethod.GET)
    public ModelAndView irAEditarPerfil(HttpSession session) {
        Long idUsuario = obtenerIdUsuarioDeSesion(session);
        if (idUsuario == null) {
            return new ModelAndView("redirect:/login");
        }
        ModelMap model = new ModelMap();
        model.put("usuario",servicioLogin.consultarUsuarioPorId(idUsuario));
        return new ModelAndView("editar_perfil", model);
    }

    @RequestMapping(path = "/perfil/guardar", method = RequestMethod.POST)
    public ModelAndView guardarPerfil(@RequestParam("nombreUsuario") String nuevoNombre,
                                      @RequestParam("avatarSeleccionado") String nuevoAvatar,
                                      @RequestParam("password") String nuevaPassword,
                                      HttpSession sessionMock) {

        Long idUsuario = obtenerIdUsuarioDeSesion(sessionMock);
        if (idUsuario == null) {
            return new ModelAndView("redirect:/login");
        }

        ModelMap model = new ModelMap();

        try {
            servicioLogin.actualizarPerfil(idUsuario, nuevoNombre, nuevoAvatar, nuevaPassword);
            model.put("usuario",servicioLogin.consultarUsuarioPorId(idUsuario));

        } catch (Exception e) {

            model.put("error", "Error al actualizar el perfil");
            return new ModelAndView("error", model);
        }

        return new ModelAndView("redirect:/perfil",model);
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
