package com.tallerwebi.dominio;
import lombok.Setter;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Getter;
@Getter
@Setter
@Entity
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String nombreUsuario;
    private String password;
    private String rol;
    private Boolean activo;
    private Integer pistas;
    private Integer monedas;
    private Integer partidasGanadas;

    public Usuario() {
        this.monedas = 100;
        this.pistas = 5;
        this.nombreUsuario = "jugador123";
        this.activo = true;
    }



}
