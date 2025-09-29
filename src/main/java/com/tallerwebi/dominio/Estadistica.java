package com.tallerwebi.dominio;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Setter
@Entity
public class Estadistica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String tamanio_sudoku;
    private Integer partidas_ganadas;
    private Integer partidas_jugadas;
    private Integer partidas_perdidas;
    private Integer racha_victorias;
    private String mejor_tiempo;

    public Estadistica(Long id,String tamanio_sudoku, String mejor_tiempo,Integer partidas_ganadas,
                       Integer partidas_jugadas, Integer partidas_perdidas, Integer racha_victorias) {
        this.id = id;
        this.tamanio_sudoku = tamanio_sudoku;
        this.partidas_ganadas = partidas_ganadas;
        this.partidas_jugadas = partidas_jugadas;
        this.partidas_perdidas = partidas_perdidas;
        this.racha_victorias = racha_victorias;
        this.mejor_tiempo = mejor_tiempo;
    }


    public Estadistica() {

    }
}
