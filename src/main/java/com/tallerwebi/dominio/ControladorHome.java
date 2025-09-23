package com.tallerwebi.dominio;


import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ControladorHome {


    @RequestMapping(path = "/tienda-monedas", method = RequestMethod.GET)
    public ModelAndView irATiendaMonedas(@ModelAttribute("usuario") Usuario usuario, HttpServletRequest request) {

        if (request.getSession().getAttribute("id_usuario") == null) {
            return new ModelAndView("redirect:/login");
        }

        ModelMap model = new ModelMap();
        model.put("monedas", request.getSession().getAttribute("monedas"));

        return new ModelAndView("tienda-monedas", model);


    }

//    @RequestMapping(path = "/home", method = RequestMethod.GET)
//    public ModelAndView irAHome(HttpServletRequest request) {
//
//        if (request.getSession().getAttribute("email") == null) {
//            return new ModelAndView("redirect:/login");
//        }
//
//        ModelMap model = new ModelMap();
//        model.put("email", request.getSession().getAttribute("email"));          //
//        model.put("monedas", request.getSession().getAttribute("monedas"));    //
//        return new ModelAndView("home", model);
//    }


}
