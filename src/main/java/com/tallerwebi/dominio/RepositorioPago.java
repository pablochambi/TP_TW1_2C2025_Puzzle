package com.tallerwebi.dominio;

public interface RepositorioPago {


    String obtenerPagoPorCollectionId(String collectionId);
    void registrarPago(String collectionId, Long usuarioId, Integer paqueteId);

}
