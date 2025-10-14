package com.tallerwebi.presentacion;


import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.preference.PreferenceBackUrlsRequest;
import com.mercadopago.client.preference.PreferenceClient;
import com.mercadopago.client.preference.PreferenceItemRequest;
import com.mercadopago.client.preference.PreferenceRequest;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.preference.Preference;
import com.tallerwebi.dominio.enums.PaqueteMonedas;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ControladorMercadoPago {

    @PostMapping("/mercado-pago")
    public String crearPreferencia(@RequestParam("paqueteId") Integer paqueteId) throws MPException, MPApiException {

        MercadoPagoConfig.setAccessToken("XXXX");


        PaqueteMonedas paquete = PaqueteMonedas.getPorId(paqueteId);
        if (paquete == null) {
            return "redirect:/tienda-monedas";
        }

        PreferenceItemRequest item = PreferenceItemRequest
                .builder()
                .id(paquete.getId().toString())
                .title("Paquete")
                .description("Paquete - " + paquete.getCantidadMonedas())
                .currencyId("ARS")
                .unitPrice(new BigDecimal(1))
                .quantity(1)
                .build();




        List<PreferenceItemRequest> items = new ArrayList<>();

        items.add(item);

        PreferenceBackUrlsRequest backUrls =

                PreferenceBackUrlsRequest.builder()
                        .success("https://nonmobile-monica-ecclesiastical.ngrok-free.dev/spring/pago-exitoso?paqueteId=" + paqueteId)
                        .pending("https://www.tu-sitio/pending")
                        .failure("https://nonmobile-monica-ecclesiastical.ngrok-free.dev/spring/pago-fallido?paqueteId=" + paqueteId)
                        .build();

        PreferenceRequest preferenceRequest = PreferenceRequest.builder().autoReturn("approved").backUrls(backUrls).items(items).externalReference("PAQUETE" + paqueteId).build();

        PreferenceClient client = new PreferenceClient();
        Preference preference = client.create(preferenceRequest);


        return "redirect:" + preference.getInitPoint();
    }

}
