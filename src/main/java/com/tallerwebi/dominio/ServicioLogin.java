package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.UsuarioExistente;

public interface ServicioLogin {

    Usuario consultarUsuario(String email, String password);
    void registrar(Usuario usuario) throws UsuarioExistente;
    String obtenerEmail(Long id_usuario);
    Integer obtenerMonedas(Long id_usuario);

    String obtenerNombreDeUsuario(Long id_usuario);
}
