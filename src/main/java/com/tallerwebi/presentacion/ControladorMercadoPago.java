package com.tallerwebi.presentacion;


import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.preference.PreferenceBackUrlsRequest;
import com.mercadopago.client.preference.PreferenceClient;
import com.mercadopago.client.preference.PreferenceItemRequest;
import com.mercadopago.client.preference.PreferenceRequest;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.preference.Preference;
import com.tallerwebi.dominio.ServicioMercadoPago;
import com.tallerwebi.dominio.enums.PaqueteMonedas;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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
