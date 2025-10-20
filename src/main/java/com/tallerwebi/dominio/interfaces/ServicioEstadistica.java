package com.tallerwebi.dominio.interfaces;

import java.util.Map;

public interface ServicioEstadistica {

    Map<String, Object> obtenerEstadisticas(Long id_usuario, String nivel);
}
