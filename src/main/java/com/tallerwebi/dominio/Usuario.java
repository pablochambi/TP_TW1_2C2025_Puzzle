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
    private Integer pistas;
    private Integer monedas;
    private String urlAvatar;


    public Usuario() {
        this.monedas = 100;
        this.pistas = 5;
        this.nombreUsuario = "jugador123";
    }

    public Usuario(Long id, String email, Integer monedas) {
        this.id = id;
        this.email = email;
        this.monedas = monedas;
    }
    public Usuario(Long id, String email,String nombreUsuario, Integer monedas) {
        this.id = id;
        this.email = email;
        this.monedas = monedas;
        this.nombreUsuario = nombreUsuario;
    }
    public Usuario(Long id, String email,String nombreUsuario, Integer monedas, String urlAvatar) {
        this.id = id;
        this.email = email;
        this.nombreUsuario = nombreUsuario;
        this.monedas = monedas;
    }

    public void agregarMonedas(Integer cantidad) {
        this.monedas += cantidad;
    }



    public void activar() {
        activo = true;
        this.urlAvatar = urlAvatar;
    }

    public Integer getPistas() {
        return pistas;
    }

    public void setPistas(Integer pistas) {
        this.pistas = pistas;
    }
}
