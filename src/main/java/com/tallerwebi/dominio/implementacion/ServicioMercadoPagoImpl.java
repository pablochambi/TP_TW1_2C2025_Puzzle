package com.tallerwebi.dominio.implementacion;


import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.preference.PreferenceBackUrlsRequest;
import com.mercadopago.client.preference.PreferenceClient;
import com.mercadopago.client.preference.PreferenceItemRequest;
import com.mercadopago.client.preference.PreferenceRequest;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.preference.Preference;
import com.tallerwebi.dominio.interfaces.ServicioMercadoPago;
import com.tallerwebi.dominio.enums.PaqueteMonedas;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;

@Service("ServicioMercadoPago")
@Transactional
public class ServicioMercadoPagoImpl implements ServicioMercadoPago {

    @Override
    public String crearYObtenerInitPoint(Integer paqueteId) throws MPException, MPApiException {
        MercadoPagoConfig.setAccessToken("APP_USR-4494164065159566-101116-668009d69bb83392a0ccf25823aa9ed7-2919985285");
        PaqueteMonedas paqueteMonedas = PaqueteMonedas.getPorId(paqueteId);
        if (paqueteMonedas == null) {
            return null;
        }

        PreferenceItemRequest item = crearItemPreference(paqueteId);
        PreferenceBackUrlsRequest backUrls = generarBackUrls(paqueteId);
        PreferenceRequest request = crearPreferenceRequest(List.of(item), backUrls, paqueteId);

        PreferenceClient client = new PreferenceClient();
        Preference preference = client.create(request);


        return preference.getSandboxInitPoint();
    }

    //creacion del item para la preferencia

    private PreferenceItemRequest crearItemPreference(Integer paqueteId) {
        PaqueteMonedas paquete = PaqueteMonedas.getPorId(paqueteId);


        PreferenceItemRequest item = PreferenceItemRequest
                .builder()
                .id(paquete.getId().toString())
                .title("Paquete")
                .description("Paquete - " + paquete.getCantidadMonedas())
                .currencyId("ARS")
                .unitPrice(new BigDecimal(1))
                .quantity(1)
                .build();

        return item;
    }

    //creacion URLS Retorno

    private PreferenceBackUrlsRequest generarBackUrls(Integer paqueteId) {

        PaqueteMonedas paquete = PaqueteMonedas.getPorId(paqueteId);
        if(paquete == null){
            return null;
        }

        PreferenceBackUrlsRequest preferenceBackUrlsRequest = PreferenceBackUrlsRequest.builder()
                .success("https://nonmobile-monica-ecclesiastical.ngrok-free.dev/spring/pago-exitoso?paqueteId=" + paqueteId)
                .pending("https://www.tu-sitio/pending")
                .failure("https://nonmobile-monica-ecclesiastical.ngrok-free.dev/spring/pago-fallido?paqueteId=" + paqueteId)
                .build();


        return preferenceBackUrlsRequest;
    }

    //Creacion pedido

    private PreferenceRequest crearPreferenceRequest(List<PreferenceItemRequest> items, PreferenceBackUrlsRequest preferenceBackUrlsRequest, Integer paqueteId) {
        PreferenceRequest preferenceRequest =
                PreferenceRequest.builder()
                        .autoReturn("approved")
                        .backUrls(preferenceBackUrlsRequest)
                        .items(items)
                        .externalReference("PAQUETE" + paqueteId).build();
        return preferenceRequest;
    }


}
