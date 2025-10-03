package com.tallerwebi.dominio;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ControladorTiendaMonedas {

    private ServicioTiendaMonedas servicioTiendaMonedas;

    @Autowired
    public ControladorTiendaMonedas(ServicioTiendaMonedas servicioTiendaMonedas) {
        this.servicioTiendaMonedas = servicioTiendaMonedas;
    }



}
