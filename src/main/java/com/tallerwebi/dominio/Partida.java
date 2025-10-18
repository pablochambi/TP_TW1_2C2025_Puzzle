package com.tallerwebi.dominio;



import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class Partida {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String temporalizador = "20:00";
    private String dificultad = "media";
    private String fecha = "20 de noviembre" ;
    private Integer pistasUsadas = 5;
    private Boolean completado = false;
    private Integer puntaje = 0;
    private Boolean ganada = false;
    private String nivel;

    @ManyToOne
    private Usuario usuario;



}
