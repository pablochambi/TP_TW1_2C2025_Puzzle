package com.tallerwebi.dominio;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Ranking {

    private String nombreJugador;
    private Integer mejorPuntaje;
    private String dificultad;
    private String mejorTiempo;
    private Integer partidasGanadas;
    private Integer posicion;


    public Ranking() {

    }

    public Ranking(String nombreUsuario, Integer mejorPuntaje, String mejorTiempo, Integer partidasGanadas, String dificultad) {
        this.nombreJugador = nombreUsuario;
        this.mejorPuntaje = mejorPuntaje;
        this.mejorTiempo = mejorTiempo;
        this.partidasGanadas = partidasGanadas;
        this.dificultad = dificultad;
    }






}
