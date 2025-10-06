package com.tallerwebi.presentacion;


import com.tallerwebi.dominio.ServicioTiendaMonedas;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class ControladorTiendaMonedas {

    private ServicioTiendaMonedas servicioTiendaMonedas;

    @Autowired
    public ControladorTiendaMonedas(ServicioTiendaMonedas servicioTiendaMonedas) {
        this.servicioTiendaMonedas = servicioTiendaMonedas;
    }



}
