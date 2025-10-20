package com.tallerwebi.dominio.interfaces;

import com.tallerwebi.dominio.Avatar;
import com.tallerwebi.dominio.Usuario;
import com.tallerwebi.dominio.Usuario_Avatar;

public interface Repositorio_usuarioAvatar {

    void relacionarUsuarioConAvatar(Usuario usuario, Avatar avatar);
    Usuario_Avatar obtenerRelacionUsuarioAvatar(Usuario usuario, Avatar avatar);

    Usuario_Avatar obtenerAvatarEnUsoDelUsuario(Usuario usuario);

    void actualizar(Usuario_Avatar avatarActual);
}
