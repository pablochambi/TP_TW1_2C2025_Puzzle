package com.tallerwebi.dominio;

import com.tallerwebi.dominio.enums.PaqueteMonedas;
import com.tallerwebi.dominio.excepcion.SaldoInsuficienteException;
import com.tallerwebi.dominio.excepcion.UsuarioInexistente;
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
    public void comprarPaquete(Long idUsuario, Integer paqueteId) throws SaldoInsuficienteException, UsuarioInexistente{

        Usuario usuario = repositorioUsuario.obtenerUsuarioPorId(idUsuario);
        System.out.println(paqueteId);
        PaqueteMonedas paqueteMonedas = obtenerPaquetePorId(paqueteId);
        System.out.println(paqueteMonedas);

        if (usuario == null) {
            throw new UsuarioInexistente();

        }

        if (usuario.getSaldo() < paqueteMonedas.getPrecioARS()){
            throw new SaldoInsuficienteException();
        }

        usuario.agregarMonedas(paqueteMonedas.getCantidadMonedas());
        usuario.restarSaldo(paqueteMonedas.getPrecioARS());



    }

    @Override
    public Integer obtenerMonedasUsuario(Long idUsuario) throws UsuarioInexistente {
        return repositorioUsuario.obtenerMonedasUsuario(idUsuario);
    }

    @Override
    public String obtenerPago(String collectionId) {

        return repositorioPago.obtenerPagoPorCollectionId(collectionId);
    }

    @Override
    public void registrarPago(String collectionId, Long usuarioId, Integer paqueteId) {

        repositorioPago.registrarPago(collectionId, usuarioId, paqueteId);

    }


    private PaqueteMonedas obtenerPaquetePorId(Integer id) {
        return PaqueteMonedas.getPorId(id);
    }




}
