package com.tallerwebi.dominio;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PartidaDTO {

    private UsuarioDTO usuarioDTO;
    private String dificultad;
    private String tiempoFormateado;      // Ej: "0:02:27"
    private int puntaje;
    private boolean ganada;
    private int pistasUsadas;
    private String fechaHoraFormateada;   // Ej: "29/05/2025 11:17 AM"

    // Constructor
    public PartidaDTO(String dificultad, String tiempoFormateado, int puntaje,
                      boolean ganada, int pistasUsadas, String fechaHoraFormateada, UsuarioDTO usuarioDTO) {
        this.dificultad = dificultad;
        this.tiempoFormateado = tiempoFormateado;
        this.puntaje = puntaje;
        this.ganada = ganada;
        this.pistasUsadas = pistasUsadas;
        this.fechaHoraFormateada = fechaHoraFormateada;
        this.usuarioDTO = usuarioDTO;
    }

}
