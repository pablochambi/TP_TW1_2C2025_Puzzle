package com.tallerwebi.dominio.interfaces;

import com.tallerwebi.dominio.Partida;
import com.tallerwebi.dominio.Usuario;

import java.util.List;

public interface RepositorioPartida {
    List<Partida> obtenerPartidasPorUsuarioYNivel(Long id_usuario, String nivel);

    List<Partida> obtenerTodasLasPartidasDelUsuario(Usuario usuario);

    List<Partida> obtenerPartidasPorPuntajeDesc();

    List<Partida> obtenerPartidasPorCriterio(String dificultad, String orden);

    List<Partida> obtenerPartidasPorTiempoAsc();
}
