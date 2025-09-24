package com.tallerwebi.dominio;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class ServicioEstadisticaImpl implements ServicioEstadistica {

   @Override
   public Map<String, String> obtenerDeNivelFacil(Long idUsuario) {

       Map<String, String> listaDeNivelFacil = new LinkedHashMap<>();
       listaDeNivelFacil.put("Mejor tiempo", "00:45");
       listaDeNivelFacil.put("Partidas ganadas", "3");
       listaDeNivelFacil.put("Partidas jugadas", "4");
       listaDeNivelFacil.put("Partidas perdidas", "1");
       listaDeNivelFacil.put("Porcentaje de victorias", "75%");
       listaDeNivelFacil.put("Mejor racha de victorias", "3");

       return listaDeNivelFacil;
   }

   public Map<String, String> obtenerDeNivelMedio(Long idUsuario) {
       Map<String, String> listaDeNivelMedio = new LinkedHashMap<>();
       listaDeNivelMedio.put("Mejor tiempo", "01:50");
       listaDeNivelMedio.put("Partidas ganadas", "2");
       listaDeNivelMedio.put("Partidas jugadas", "3");
       listaDeNivelMedio.put("Partidas perdidas", "1");
       listaDeNivelMedio.put("Porcentaje de victorias", "66%");
       listaDeNivelMedio.put("Mejor racha de victorias", "2");
       return listaDeNivelMedio;
   }

   public Map<String, String> obtenerDeNivelDificil(Long idUsuario) {
       Map<String, String> listaDeNivelDificil = new LinkedHashMap<>();
       listaDeNivelDificil.put("Mejor tiempo", "--:--");
       listaDeNivelDificil.put("Partidas ganadas", "0");
       listaDeNivelDificil.put("Partidas jugadas", "0");
       listaDeNivelDificil.put("Partidas perdidas", "0");
       listaDeNivelDificil.put("Porcentaje de victorias", "--");
       listaDeNivelDificil.put("Mejor racha de victorias", "0");
       return listaDeNivelDificil;
   }
}
