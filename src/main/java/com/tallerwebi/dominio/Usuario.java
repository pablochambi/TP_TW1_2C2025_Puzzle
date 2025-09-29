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
    private Boolean activo = false;

    private Integer monedas = 100 ;

    public Usuario() {
    }

    public Usuario(Long id, String email, Integer monedas) {
        this.id = id;
        this.email = email;
        this.monedas = monedas;
    }
    public Usuario(Long id, String email,String nombreUsuario, Integer monedas) {
        this.id = id;
        this.email = email;
        this.nombreUsuario = nombreUsuario;
        this.monedas = monedas;
    }



}
