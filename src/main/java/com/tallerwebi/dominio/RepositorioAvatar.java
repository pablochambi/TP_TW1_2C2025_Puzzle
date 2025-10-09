package com.tallerwebi.dominio;

import java.util.List;

public interface RepositorioAvatar {
    List<Avatar> obtenerAvataresDisponibles();
    Avatar buscarAvatarPorUrlImagen(String nuevoAvatar);
}
