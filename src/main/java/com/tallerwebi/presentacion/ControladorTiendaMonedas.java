package com.tallerwebi.presentacion;


import com.tallerwebi.dominio.ServicioTiendaMonedas;
import com.tallerwebi.dominio.excepcion.UsuarioInexistente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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

    @RequestMapping(path = "/procesar-pago", method = RequestMethod.POST)
    public ModelAndView procesarPago (@RequestParam Integer paqueteId, HttpServletRequest request) throws UsuarioInexistente {
        ModelAndView mv = new ModelAndView();
        ModelMap model  = new ModelMap();
        Long usuarioId = (Long) request.getSession().getAttribute("id_usuario");
        Integer monedas = servicioTiendaMonedas.obtenerMonedasUsuario(usuarioId);

        try {
            servicioTiendaMonedas.comprarPaquete(usuarioId, paqueteId);
            model.put("monedas", servicioTiendaMonedas.obtenerMonedasUsuario(usuarioId));
            model.put("exitoPago" ,"Tus monedas se han actualizado");
            return new ModelAndView("tienda-monedas", model);


        } catch (UsuarioInexistente e) {
            mv.setViewName("redirect:/login");
        }


        return mv;


    }

    @GetMapping("/pago-exitoso")
    public ModelAndView pagoExitoso(@RequestParam Integer paqueteId, @RequestParam String collection_id, HttpServletRequest request) {
        Long usuarioId = (Long) request.getSession().getAttribute("id_usuario");
        ModelMap model  = new ModelMap();

        if (servicioTiendaMonedas.obtenerPago(collection_id) !=null) {
            model.put("monedas", servicioTiendaMonedas.obtenerMonedasUsuario(usuarioId));
            return new ModelAndView("tienda-monedas", model);
        }



        servicioTiendaMonedas.comprarPaquete(usuarioId, paqueteId);
        servicioTiendaMonedas.registrarPago(collection_id, usuarioId, paqueteId);




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
