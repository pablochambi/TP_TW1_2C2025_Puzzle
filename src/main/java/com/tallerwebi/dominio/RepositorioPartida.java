package com.tallerwebi.dominio;

import java.util.List;

public interface RepositorioPartida {
    List<Partida> obtenerPartidasPorUsuarioYNivel(Long id_usuario, String nivel);
}
