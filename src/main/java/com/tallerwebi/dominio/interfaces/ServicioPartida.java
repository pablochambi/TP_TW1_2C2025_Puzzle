package com.tallerwebi.dominio.interfaces;

import com.tallerwebi.dominio.Partida;
import com.tallerwebi.dominio.PartidaDTO;

import java.util.List;

public interface ServicioPartida {
    List<Partida> obtenerTodasLasPartidas(Long idUsuario);

    List<PartidaDTO> obtenerTodasLasPartidasDTO(Long idUsuario);

    List<PartidaDTO> obtenerTodasLasPartidasDTODelMasRecienteAlMasAntiguo(Long idUsuario);
}
