package com.tallerwebi.dominio;

public interface RepositorioUsuario {

    Usuario buscarUsuario(String email, String password);
    void guardar(Usuario usuario);
    Usuario buscar(String email);
    void modificar(Usuario usuario);
    Usuario obtenerUsuarioPorId(Long id_usuario);
    Integer obtenerMonedasUsuario(Long idUsuario);
    void actualizarPerfil(Long idUsuario, String nuevoNombre,
                          Long id_avatar, String nuevaPassword);
    void actualizarNombreYPassword(Long idUsuario, String nuevoNombre, String nuevaPassword);
}

