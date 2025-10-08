package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.SaldoInsuficienteException;
import com.tallerwebi.dominio.excepcion.UsuarioInexistente;

public interface ServicioTiendaMonedas {


    void comprarPaquete(Long idUsuario, Integer paqueteId) throws SaldoInsuficienteException, UsuarioInexistente;
    Integer obtenerMonedasUsuario(Long idUsuario) throws UsuarioInexistente;

}


