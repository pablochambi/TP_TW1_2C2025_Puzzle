package com.tallerwebi.presentacion;


import com.tallerwebi.dominio.ServicioEstadistica;
import com.tallerwebi.dominio.ServicioLogin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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

        ModelMap model = cargarDatosBasicosUsuario(idUsuario);
        model.put("estadisticasNivelFacil", servicioEstadistica.obtenerDeNivelFacil(idUsuario));
        model.put("estadisticasNivelMedio", servicioEstadistica.obtenerDeNivelMedio(idUsuario));
        model.put("estadisticasNivelDificil", servicioEstadistica.obtenerDeNivelDificil(idUsuario));

        return new ModelAndView("perfil", model);
    }

    @RequestMapping(path = "/perfil/editar", method = RequestMethod.GET)
    public ModelAndView irAEditarPerfil(HttpSession session) {
        Long idUsuario = obtenerIdUsuarioDeSesion(session);
        if (idUsuario == null) {
            return new ModelAndView("redirect:/login");
        }

        ModelMap model = cargarDatosBasicosUsuario(idUsuario);
        model.put("password", servicioLogin.obtenerPassword(idUsuario));
        return new ModelAndView("editar_perfil2", model);
    }

    // --- Métodos privados reutilizables ---
    private Long obtenerIdUsuarioDeSesion(HttpSession session) {
        return (Long) session.getAttribute("id_usuario");
    }

    private ModelMap cargarDatosBasicosUsuario(Long idUsuario) {
        ModelMap model = new ModelMap();
        model.put("email", servicioLogin.obtenerEmail(idUsuario));
        model.put("monedas", servicioLogin.obtenerMonedas(idUsuario));
        model.put("nombreUsuario", servicioLogin.obtenerNombreDeUsuario(idUsuario));
        return model;
    }
}
