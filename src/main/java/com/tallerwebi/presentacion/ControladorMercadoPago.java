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

        //TOKEN DE COMUNICACION

        MercadoPagoConfig.setAccessToken("APP_USR-4494164065159566-101116-668009d69bb83392a0ccf25823aa9ed7-2919985285");

        //se crea el paquete de la compra
        PaqueteMonedas paquete = PaqueteMonedas.getPorId(paqueteId);
        if (paquete == null) {
            return "redirect:/tienda-monedas";
        }

        //creacion del item para la preferencia

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


        //se generan los urls de retorno posibles tras la compra
        PreferenceBackUrlsRequest backUrls =

                PreferenceBackUrlsRequest.builder()
                        .success("https://nonmobile-monica-ecclesiastical.ngrok-free.dev/spring/pago-exitoso?paqueteId=" + paqueteId)
                        .pending("https://www.tu-sitio/pending")
                        .failure("https://nonmobile-monica-ecclesiastical.ngrok-free.dev/spring/pago-fallido?paqueteId=" + paqueteId)
                        .build();

        //Se crea finalmente al preferencia(pedido)con lo creado previamente

        PreferenceRequest preferenceRequest = PreferenceRequest.builder().autoReturn("approved").backUrls(backUrls).items(items).externalReference("PAQUETE" + paqueteId).build();

        //Se instancia el cliente de mercado pago y se crea la conexion con la API pasandole todos los datos
        PreferenceClient client = new PreferenceClient();
        Preference preference = client.create(preferenceRequest); //PREFERENCE = Respuesta de MP


        //Se retorna el URL/VISTA para proceder con el pago
        return "redirect:" + preference.getSandboxInitPoint();
    }

}
