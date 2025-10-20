package com.tallerwebi.dominio.implementacion;

import com.tallerwebi.dominio.Partida;
import com.tallerwebi.dominio.interfaces.ServicioHistorial;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ServicioHistorialIml implements ServicioHistorial {


    @Override
    public List<Partida> ultimasDiezPartidas() {
        ArrayList<Partida> partidas = new ArrayList<>();
        for (int i =0; i<10; i++) {
            partidas.add(new Partida());
        }
        return partidas;
    }


}
