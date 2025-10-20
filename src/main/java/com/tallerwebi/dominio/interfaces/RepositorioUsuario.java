package com.tallerwebi.dominio.interfaces;

import com.tallerwebi.dominio.Usuario;

public interface RepositorioUsuario {

    Usuario buscarUsuario(String email, String password);
    void guardar(Usuario usuario);
    Usuario buscar(String email);
    void modificar(Usuario usuario);
    Usuario obtenerUsuarioPorId(Long id_usuario);
    Integer obtenerMonedasUsuario(Long idUsuario);

}

