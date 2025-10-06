package com.tallerwebi.presentacion;


import com.tallerwebi.dominio.ServicioLogin;
import com.tallerwebi.dominio.Usuario;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ControladorHome {

    private ServicioLogin servicioLogin;

    public ControladorHome(ServicioLogin servicioLogin) {
        this.servicioLogin = servicioLogin;
    }


    @RequestMapping(path = "/tienda-monedas", method = RequestMethod.GET)
    public ModelAndView irATiendaMonedas(Usuario usuario, HttpServletRequest request) {


        if (request.getSession().getAttribute("id_usuario") == null) {
            return new ModelAndView("redirect:/login");
        }

        ModelMap model = new ModelMap();
        Integer monedas = servicioLogin.obtenerMonedas(usuario.getId());
        model.put("monedas", monedas);

        return new ModelAndView("tienda-monedas", model);


    }




}
