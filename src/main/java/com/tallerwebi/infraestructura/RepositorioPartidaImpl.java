package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Partida;
import com.tallerwebi.dominio.RepositorioPartida;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RepositorioPartidaImpl implements RepositorioPartida {
    @Override
    public List<Partida> obtenerPartidasPorUsuarioYNivel(Long id_usuario, String nivel) {
        return List.of();
    }
}
