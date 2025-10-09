package com.tallerwebi.presentacion;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AvatarDTO {
    private Long id;
    private String nombre;
    private String urlImagen;
    private Boolean comprado;
    private Boolean enUso;
    private Integer precio;
    private String iconoHexadecimal;
    public AvatarDTO() {
    }

}
