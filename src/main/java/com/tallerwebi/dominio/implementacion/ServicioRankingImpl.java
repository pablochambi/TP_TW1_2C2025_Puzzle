package com.tallerwebi.dominio.implementacion;

import com.tallerwebi.dominio.PartidaDTO;
import com.tallerwebi.dominio.Ranking;
import com.tallerwebi.dominio.interfaces.ServicioPartida;
import com.tallerwebi.dominio.interfaces.ServicioRanking;
import org.apache.maven.shared.invoker.SystemOutHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;



@Service
@Transactional
public class ServicioRankingImpl implements ServicioRanking {
    private static final int LIMITE_RANKING = 10;
    private final ServicioPartida servicioPartida;


    @Autowired
    public ServicioRankingImpl(ServicioPartida servicioPartida) {
        this.servicioPartida = servicioPartida;
    }


    @Override
    public List<Ranking> obtenerRanking(String dificultad, String orden) {
        List<Ranking> ranking;


        //RANKING GENERAL, PARAMETROS DEFAULT

//        if (dificultad.equalsIgnoreCase("GENERAL") && orden.equalsIgnoreCase("PUNTAJE")) {
//            List<PartidaDTO> partidasDTOOrdenadasPorPuntaje = servicioPartida.obtenerPartidasDTOOrdenadasPorPuntaje();
//
//            ranking = generarRanking(partidasDTOOrdenadasPorPuntaje);
//
//            return ranking;
//        }

        List<PartidaDTO> partidasDTO = servicioPartida.obtenerPartidasPorCriterio(dificultad, orden);
        ranking = generarRanking(partidasDTO);



        return ranking;
    }




    //METODO PARA GENERAR RANKING REUTILIZABLE

    private List<Ranking> generarRanking(List<PartidaDTO> partidasDTO) {
        List<Ranking> ranking = new ArrayList<>();
        int posicionesRanking = partidasDTO.size();

        if (posicionesRanking > LIMITE_RANKING) {
            posicionesRanking = LIMITE_RANKING;
        }

        for (int i = 0; i < posicionesRanking; i++) {
            ranking.add(new Ranking());
            ranking.get(i).setPosicion(i + 1);
            ranking.get(i).setNombreJugador(partidasDTO.get(i).getUsuarioDTO().getNombreUsuario());
            ranking.get(i).setMejorPuntaje(partidasDTO.get(i).getPuntaje());
            ranking.get(i).setMejorTiempo(partidasDTO.get(i).getTiempoFormateado());
            ranking.get(i).setPartidasGanadas(partidasDTO.get(i).getUsuarioDTO().getPartidasGanadas());
            ranking.get(i).setDificultad(partidasDTO.get(i).getDificultad());
        }


        return ranking;
    }




}
