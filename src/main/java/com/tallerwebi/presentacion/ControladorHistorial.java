package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Partida;
import com.tallerwebi.dominio.ServicioHistorial;
import com.tallerwebi.dominio.ServicioLogin;
import com.tallerwebi.dominio.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;

@Controller
public class ControladorHistorial {




    private ServicioHistorial serviciohistorial;

    @Autowired
    public ControladorHistorial(ServicioHistorial serviciohistorial){
        this.serviciohistorial = serviciohistorial;
    }

    @RequestMapping("historial")
    public ModelAndView historial() {

        ArrayList<Partida> listaPartidas = serviciohistorial.ultimasDiezPartidas();
        ModelMap model = new ModelMap();
        model.put("partidas", listaPartidas);

        return new ModelAndView("historial", model);
    }
}
