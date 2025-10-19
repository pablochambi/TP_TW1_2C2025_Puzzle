package com.tallerwebi.presentacion;

import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.tallerwebi.dominio.ServicioMercadoPago;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class ControladorMercadoPago {

    private ServicioMercadoPago servicioMercadoPago;

    @Autowired
    public ControladorMercadoPago(ServicioMercadoPago servicioMercadoPago) {
        this.servicioMercadoPago = servicioMercadoPago;
    }


    @PostMapping("/mercado-pago")
    public String crearPreferencia(@RequestParam("paqueteId") Integer paqueteId) throws MPException, MPApiException {
       String URLPago = servicioMercadoPago.crearYObtenerInitPoint(paqueteId);

        return "redirect:" + URLPago;
    }

}
