package com.tallerwebi.presentacion;


import com.tallerwebi.dominio.ServicioEstadistica;
import com.tallerwebi.dominio.ServicioLogin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class ControladorPerfil {

    ServicioEstadistica servicioEstadistica;
    ServicioLogin servicioLogin;

    @Autowired
    public ControladorPerfil(ServicioLogin servicioLogin, ServicioEstadistica servicioEstadistica) {
        this.servicioLogin = servicioLogin;
        this.servicioEstadistica = servicioEstadistica;
    }

    @RequestMapping(path = "/perfil", method = RequestMethod.GET)
    public ModelAndView irAlPerfil(HttpSession session) {

        if(!existeUsuarioEnSesion(session)){
            return new ModelAndView("redirect:/login");
        }

        Long idUsuario = (Long) session.getAttribute("id_usuario");

        ModelMap model = new ModelMap();
        model.put("email", servicioLogin.obtenerEmail(idUsuario));
        model.put("monedas", servicioLogin.obtenerMonedas(idUsuario));
        model.put("nombreUsuario", servicioLogin.obtenerNombreDeUsuario(idUsuario));

        model.put("estadisticasNivelFacil", servicioEstadistica.obtenerDeNivelFacil(idUsuario));
        model.put("estadisticasNivelMedio", servicioEstadistica.obtenerDeNivelMedio(idUsuario));
        model.put("estadisticasNivelDificil", servicioEstadistica.obtenerDeNivelDificil(idUsuario));

        return new ModelAndView("perfil", model);
    }

    private boolean existeUsuarioEnSesion(HttpSession session) {
        return session.getAttribute("id_usuario") != null;
    }


    @RequestMapping(path = "/perfil/editar", method = RequestMethod.GET)
    public ModelAndView irAEditarPerfil() {
        return new ModelAndView("editar-perfil");
    }


}
