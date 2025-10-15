package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.UsuarioInexistente;

public interface ServicioTiendaMonedas {


    void comprarPaquete(Long idUsuario, Integer paqueteId) throws UsuarioInexistente;
    Integer obtenerMonedasUsuario(Long idUsuario) throws UsuarioInexistente;

    String obtenerPago(String collectionId);
    void registrarPago(String collectionId, Long usuarioId, Integer paqueteId);
}


