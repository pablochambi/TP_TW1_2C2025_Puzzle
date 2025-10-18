package com.tallerwebi.dominio;

public interface Repositorio_usuarioAvatar {

    void relacionarUsuarioConAvatar(Usuario usuario, Avatar avatar);
    Usuario_Avatar obtenerRelacionUsuarioAvatar(Usuario usuario, Avatar avatar);

    Usuario_Avatar obtenerAvatarEnUsoDelUsuario(Usuario usuario);

    void actualizar(Usuario_Avatar avatarActual);
}
