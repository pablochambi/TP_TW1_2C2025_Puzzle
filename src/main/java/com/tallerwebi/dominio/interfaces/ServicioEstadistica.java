package com.tallerwebi.dominio.interfaces;

import com.tallerwebi.dominio.enums.NIVEL;

import java.util.Map;

public interface ServicioEstadistica {

    Map<String, Object> obtenerEstadisticas(Long id_usuario, NIVEL nivel);
}
