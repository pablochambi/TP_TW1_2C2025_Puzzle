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
    private Long id_usuario;
    private Integer partidas_ganadas_nivel_facil;
    private Integer partidas_jugadas_nivel_facil;
    private Integer partidas_perdidas;
    private Integer racha_victorias_nivel_facil;
    private String mejor_tiempo_nivel_facil;


}
