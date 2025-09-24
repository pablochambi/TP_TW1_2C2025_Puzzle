package com.tallerwebi.dominio;

import java.util.List;
import java.util.Map;

public interface ServicioEstadistica {

    Map<String, String> obtenerDeNivelFacil(Long idUsuario);

    Map<String, String>  obtenerDeNivelMedio(Long idUsuario);

    Map<String, String>  obtenerDeNivelDificil(Long idUsuario);
}
