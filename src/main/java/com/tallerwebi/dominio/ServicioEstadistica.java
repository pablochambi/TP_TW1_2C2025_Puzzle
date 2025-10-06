package com.tallerwebi.dominio;

import java.util.List;
import java.util.Map;

public interface ServicioEstadistica {

    Map<String, Object> obtenerEstadisticas(Long id_usuario, String nivel);
}
