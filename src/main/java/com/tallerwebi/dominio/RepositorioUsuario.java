package com.tallerwebi.dominio;

public interface RepositorioUsuario {

    Usuario buscarUsuario(String email, String password);
    void guardar(Usuario usuario);
    Usuario buscar(String email);
    void modificar(Usuario usuario);

    void actualizarPerfil(Long id, String nuevoNombre, String nuevaUrl, String nuevaPassword);
    Usuario obtenerUsuarioPorId(Long id_usuario);
    Integer obtenerMonedasUsuario(Long idUsuario);
}

