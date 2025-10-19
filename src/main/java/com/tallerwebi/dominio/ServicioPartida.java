package com.tallerwebi.dominio;

import java.util.List;

public interface ServicioPartida {
    List<Partida> obtenerTodasLasPartidas(Long idUsuario);

    List<PartidaDTO> obtenerTodasLasPartidasDTO(Long idUsuario);
}
