package com.tallerwebi.dominio;



import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class Partida {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String dificultad;
    private Integer tiempoSegundos;
    private Integer puntaje;
    private Integer pistasUsadas;
    private Boolean ganada;
    private LocalDateTime fechaHoraInicio;

    @ManyToOne
    private Usuario usuario;


}
