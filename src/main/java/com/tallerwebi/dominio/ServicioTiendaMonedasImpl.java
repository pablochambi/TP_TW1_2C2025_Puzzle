package com.tallerwebi.dominio;

import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service("ServicioTiendaMonedas")
@Transactional
public class ServicioTiendaMonedasImpl implements ServicioTiendaMonedas {


    @Override
    public void efectuarCompra() {

    }
}
