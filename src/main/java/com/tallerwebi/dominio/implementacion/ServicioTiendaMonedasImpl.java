package com.tallerwebi.dominio.implementacion;


import com.tallerwebi.dominio.Pago;
import com.tallerwebi.dominio.Usuario;
import com.tallerwebi.dominio.enums.PaqueteMonedas;
import com.tallerwebi.dominio.excepcion.UsuarioInexistente;
import com.tallerwebi.dominio.interfaces.RepositorioPago;
import com.tallerwebi.dominio.interfaces.RepositorioUsuario;
import com.tallerwebi.dominio.interfaces.ServicioTiendaMonedas;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service("ServicioTiendaMonedas")
@Transactional
public class ServicioTiendaMonedasImpl implements ServicioTiendaMonedas {

    private final RepositorioUsuario repositorioUsuario;
    private final RepositorioPago repositorioPago;


    public ServicioTiendaMonedasImpl(RepositorioUsuario repositorioUsuario, RepositorioPago repositorioPago) {
        this.repositorioPago = repositorioPago;
        this.repositorioUsuario = repositorioUsuario;

    }



    @Override
    public void comprarPaquete(Long idUsuario, Integer paqueteId, String collection_id) throws  UsuarioInexistente{

        Usuario usuario = repositorioUsuario.obtenerUsuarioPorId(idUsuario);
        PaqueteMonedas paqueteMonedas = obtenerPaquetePorId(paqueteId);

        if (usuario == null) {
            throw new UsuarioInexistente();

        }

        registrarPago(collection_id, idUsuario, paqueteId);
        usuario.setMonedas(usuario.getMonedas() + paqueteMonedas.getCantidadMonedas());
        //usuario.agregarMonedas(paqueteMonedas.getCantidadMonedas());


    }

    @Override
    public Integer obtenerMonedasUsuario(Long idUsuario) throws UsuarioInexistente {
        return repositorioUsuario.obtenerMonedasUsuario(idUsuario);
    }

    @Override
    public String obtenerPago(String collectionId) {

        return repositorioPago.obtenerPagoPorCollectionId(collectionId);
    }


    private void registrarPago(String collectionId, Long usuarioId, Integer paqueteId) {

        Usuario usuario  = repositorioUsuario.obtenerUsuarioPorId(usuarioId);
        PaqueteMonedas paquete = PaqueteMonedas.getPorId(paqueteId);



        Pago pago = new Pago(collectionId, usuario, paquete);

        repositorioPago.registrarPago(pago);

    }


    private PaqueteMonedas obtenerPaquetePorId(Integer id) {

        return PaqueteMonedas.getPorId(id);
    }




}
