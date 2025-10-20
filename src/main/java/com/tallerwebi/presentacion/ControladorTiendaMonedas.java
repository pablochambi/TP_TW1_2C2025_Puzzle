package com.tallerwebi.presentacion;


import com.tallerwebi.dominio.interfaces.ServicioTiendaMonedas;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ControladorTiendaMonedas {

    private ServicioTiendaMonedas servicioTiendaMonedas;

    @Autowired
    public ControladorTiendaMonedas(ServicioTiendaMonedas servicioTiendaMonedas) {
        this.servicioTiendaMonedas = servicioTiendaMonedas;
    }



    @GetMapping("/pago-exitoso")
    public ModelAndView pagoExitoso(@RequestParam Integer paqueteId, @RequestParam String collection_id, HttpServletRequest request) {
        Long usuarioId = (Long) request.getSession().getAttribute("id_usuario");
        ModelMap model  = new ModelMap();

        if (servicioTiendaMonedas.obtenerPago(collection_id) !=null) {
            model.put("monedas", servicioTiendaMonedas.obtenerMonedasUsuario(usuarioId));
            return new ModelAndView("tienda-monedas", model);
        }

        servicioTiendaMonedas.comprarPaquete(usuarioId, paqueteId, collection_id);





        model.put("exitoPago" ,"✅Compra exitosa. Tus monedas se han actualizado");
        model.put("monedas" , servicioTiendaMonedas.obtenerMonedasUsuario(usuarioId));



        return new ModelAndView("tienda-monedas", model);
    }

    @GetMapping("/pago-fallido")
    public ModelAndView pagoFallido(@RequestParam Integer paqueteId,HttpServletRequest request) {
        Long usuarioId = (Long) request.getSession().getAttribute("id_usuario");
        ModelMap model  = new ModelMap();
        model.put("monedas", servicioTiendaMonedas.obtenerMonedasUsuario(usuarioId));
        model.put("errorPago" , "❌ El pago no se completó. Intenta nuevamente.");

        return new ModelAndView("tienda-monedas", model);


    }












}
