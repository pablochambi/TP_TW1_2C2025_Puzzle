package com.tallerwebi.dominio;



import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Partida {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String temporalizador = "20:00";
    private String dificultad = "media";
    private String fecha = "20 de noviembre" ;
    private Boolean completado = false;

    public Boolean getCompletado() {
        return completado;
    }

    public void setCompletado(Boolean completado) {
        this.completado = completado;
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getPistasUsadas() {
        return pistasUsadas;
    }

    public void setPistasUsadas(Integer pistasUsadas) {
        this.pistasUsadas = pistasUsadas;
    }

    private Integer pistasUsadas = 5;



    public String getTemporalizador() {
        return temporalizador;
    }

    public void setTemporalizador(String temporalizador) {
        this.temporalizador = temporalizador;
    }

    public String getDificultad() {
        return dificultad;
    }

    public void setDificultad(String dificultad) {
        this.dificultad = dificultad;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String cronometro) {
        this.fecha = cronometro;
    }




}
