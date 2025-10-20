package com.tallerwebi.dominio.interfaces;

import com.tallerwebi.dominio.Pago;

public interface RepositorioPago {


    String obtenerPagoPorCollectionId(String collectionId);
    void registrarPago(Pago pago);

}
