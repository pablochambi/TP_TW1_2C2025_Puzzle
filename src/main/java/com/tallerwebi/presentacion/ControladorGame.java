package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ServicioLogin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ControladorGame {

    private ServicioLogin servicioLogin;
    @Autowired
    public ControladorGame(ServicioLogin servicioLogin){
        this.servicioLogin = servicioLogin;
    }




    @RequestMapping(path = "/game", method = RequestMethod.GET)
    public ModelAndView game(HttpServletRequest request) {



        ModelMap model = new ModelMap();
        model.put("pistas",request.getSession().getAttribute("pistas") );        //
        return new ModelAndView("game", model);
    }

    @RequestMapping("/irAInicio")
    public ModelAndView irAInicio() {

        return new ModelAndView("home");
    }




}
