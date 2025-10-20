package com.tallerwebi.dominio.interfaces;

import com.tallerwebi.dominio.Partida;
import com.tallerwebi.dominio.Usuario;
import com.tallerwebi.dominio.enums.NIVEL;

import java.util.List;

public interface RepositorioPartida {
    List<Partida> obtenerPartidasPorUsuarioYNivel(Long id_usuario, NIVEL nivel);

    List<Partida> obtenerTodasLasPartidasDelUsuario(Usuario usuario);
}
