package com.tallerwebi.dominio;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioDTO {
    private Long id;
    private String email;
    private String nombreUsuario;
    private String password;
    private String rol;
    private Boolean activo;
    private Integer pistas;
    private Integer monedas;
    private Avatar avatar;

    public UsuarioDTO() {
    }
    public UsuarioDTO(Usuario usuario, Avatar avatar) {
        this.id = usuario.getId();
        this.email = usuario.getEmail();
        this.nombreUsuario = usuario.getNombreUsuario();
        this.password = usuario.getPassword();
        this.rol = usuario.getRol();
        this.activo = usuario.getActivo();
        this.pistas = usuario.getPistas();
        this.monedas = usuario.getMonedas();
        this.avatar = avatar;
    }

}
