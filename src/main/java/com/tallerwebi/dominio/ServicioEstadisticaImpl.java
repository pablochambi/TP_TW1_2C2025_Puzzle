package com.tallerwebi.dominio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class ServicioEstadisticaImpl implements ServicioEstadistica {


    private RepositorioPartida repositorioPartida;

    @Autowired
    public ServicioEstadisticaImpl(RepositorioPartida repositorioPartida) {
        this.repositorioPartida = repositorioPartida;
    }

    @Override
    public Map<String, Object> obtenerEstadisticas(Long id_usuario, String nivel) {
        List<Partida> partidas = repositorioPartida.obtenerPartidasPorUsuarioYNivel(id_usuario, nivel);

        if (partidas.isEmpty()) {
            Map<String, Object> estadisticas = new LinkedHashMap<>();
            estadisticas.put("Mejor tiempo", "--:--");
            estadisticas.put("Partidas jugadas", 0);
            estadisticas.put("Partidas ganadas", 0);
            estadisticas.put("Partidas perdidas", 0);
            estadisticas.put("Porcentaje de victorias", "0%");
            estadisticas.put("Mejor racha de victorias", 0);

            return estadisticas;
        }

        return calcularEstadisticas(partidas);
    }

    private Map<String, Object> calcularEstadisticas(List<Partida> partidas) {
        Map<String, Object> estadisticas = new LinkedHashMap<>();

        int partidasJugadas = partidas.size();
        int partidasGanadas = (int) partidas.stream().filter(Partida::getGanada).count();
        int partidasPerdidas = partidasJugadas - partidasGanadas;
        double porcentajeVictoria = (partidasGanadas * 100.0) / partidasJugadas;
        int mejorRachaDeVictorias = obtenerMejorRachaDeVictorias(partidas);


        estadisticas.put("Mejor tiempo", "--:--");
        estadisticas.put("Partidas jugadas", partidasJugadas);
        estadisticas.put("Partidas ganadas", partidasGanadas);
        estadisticas.put("Partidas perdidas", partidasPerdidas);
        estadisticas.put("Porcentaje de Victoria", String.format("%.1f%%", porcentajeVictoria));
        estadisticas.put("Mejor racha de victorias", mejorRachaDeVictorias);

        return estadisticas;
    }

    private int obtenerMejorRachaDeVictorias(List<Partida> partidas) {

        int mejorRacha = 0;
        int rachaActual = 0;

        for (Partida partida : partidas) {
            if (partida.getGanada()) {
                rachaActual++;
                if (rachaActual > mejorRacha) {
                    mejorRacha = rachaActual;
                }
            } else {
                rachaActual = 0;
            }
        }
        return mejorRacha;
    }


}
