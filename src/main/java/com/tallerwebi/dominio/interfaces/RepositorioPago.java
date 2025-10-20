package com.tallerwebi.dominio.interfaces;

public interface RepositorioPago {


    String obtenerPagoPorCollectionId(String collectionId);
    void registrarPago(String collectionId, Long usuarioId, Integer paqueteId);

}
