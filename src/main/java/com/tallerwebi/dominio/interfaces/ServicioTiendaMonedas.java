package com.tallerwebi.dominio.interfaces;

import com.tallerwebi.dominio.excepcion.UsuarioInexistente;

public interface ServicioTiendaMonedas {


    void comprarPaquete(Long idUsuario, Integer paqueteId, String collection_Id) throws UsuarioInexistente;
    Integer obtenerMonedasUsuario(Long idUsuario) throws UsuarioInexistente;

    String obtenerPago(String collectionId);
}


