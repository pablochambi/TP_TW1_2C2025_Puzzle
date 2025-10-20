package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Partida;
import com.tallerwebi.dominio.interfaces.RepositorioPartida;
import com.tallerwebi.dominio.Usuario;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RepositorioPartidaImpl implements RepositorioPartida {

    @Autowired
    SessionFactory sessionFactory;

    @Override
    public List<Partida> obtenerPartidasPorUsuarioYNivel(Long id_usuario, String nivel) {
        return List.of();
    }

    @Override
    public List<Partida> obtenerTodasLasPartidasDelUsuario(Usuario usuario) {
        return (List<Partida>) sessionFactory.getCurrentSession()
                .createCriteria(Partida.class)
                .add(Restrictions.eq("usuario", usuario))
                .list();
    }
}
