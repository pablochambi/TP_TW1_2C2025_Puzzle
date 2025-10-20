package com.tallerwebi.dominio.interfaces;

import com.tallerwebi.dominio.Avatar;
import com.tallerwebi.dominio.Usuario;

import java.util.List;

public interface RepositorioAvatar {
    List<Avatar> obtenerAvataresDisponibles();
    Avatar buscarAvatarPorUrlImagen(String nuevoAvatar);

    Avatar buscarAvatarPorId(Long idAvatar);

    Avatar obtenerAvatarDelUsuario(Usuario usuario);

    void agregarAvataresGratuitosAlUsuario(Usuario usuario);
}
