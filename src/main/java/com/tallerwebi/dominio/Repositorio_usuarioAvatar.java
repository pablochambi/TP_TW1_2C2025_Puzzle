package com.tallerwebi.dominio;

public interface Repositorio_usuarioAvatar {
    Boolean comprobarSiElAvatarEstaCompradoPorElUsuario(Long id_avatar, Long id_usuario);

    Boolean comprobarSiElAvatarEstaEnUsoPorElUsuario(Long id, Long id_usuario);

    void relacionarUsuarioConAvatar(Long idUsuario, Long avatarId);
}
