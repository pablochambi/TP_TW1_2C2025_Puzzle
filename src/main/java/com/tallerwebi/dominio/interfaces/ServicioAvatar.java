package com.tallerwebi.dominio.interfaces;

import com.tallerwebi.dominio.Avatar;
import com.tallerwebi.presentacion.AvatarDTO;

import java.util.List;

public interface ServicioAvatar {
    List<Avatar> obtenerAvataresDisponibles();
    List<AvatarDTO> obtenerAvataresDTO(Long idUsuario);

    void comprarAvatar(Long idUsuario, Long avatarId);
}
