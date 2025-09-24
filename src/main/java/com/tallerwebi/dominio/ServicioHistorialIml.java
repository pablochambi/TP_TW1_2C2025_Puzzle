package com.tallerwebi.dominio;

import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;

@Service
@Transactional
public class ServicioHistorialIml implements ServicioHistorial {


    @Override
    public ArrayList<Partida> ultimasDiezPartidas() {
        ArrayList<Partida> partidas = new ArrayList<>();
        for (int i =0; i<10; i++) {
            partidas.add(new Partida());
        }
        return partidas;
    }
}
