package com.tallerwebi.dominio.interfaces;


import com.tallerwebi.dominio.Ranking;

import java.util.List;

public interface ServicioRanking {


    List<Ranking> obtenerRanking(String dificultad, String orden);
}
