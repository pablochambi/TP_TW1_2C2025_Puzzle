package com.tallerwebi.dominio;



import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;



public interface ServicioMercadoPago {
    String crearYObtenerInitPoint(Integer paqueteId) throws MPException, MPApiException;
}
