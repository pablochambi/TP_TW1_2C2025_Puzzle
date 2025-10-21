package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Partida;
import com.tallerwebi.dominio.enums.NIVEL;
import com.tallerwebi.dominio.interfaces.RepositorioPartida;
import com.tallerwebi.dominio.Usuario;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RepositorioPartidaImpl implements RepositorioPartida {

    @Autowired
    SessionFactory sessionFactory;

    @Override
    public List<Partida> obtenerPartidasPorUsuarioYNivel(Long id_usuario, NIVEL nivel) {
        return (List<Partida>) sessionFactory.getCurrentSession()
                .createCriteria(Partida.class)
                .createAlias("usuario", "u")
                .add(Restrictions.eq("u.id", id_usuario))
                .add(Restrictions.eq("nivel", nivel))
                .list();
    }

    @Override
    public List<Partida> obtenerTodasLasPartidasDelUsuario(Usuario usuario) {
        return (List<Partida>) sessionFactory.getCurrentSession()
                .createCriteria(Partida.class)
                .add(Restrictions.eq("usuario", usuario))
                .list();
    }

    @Override
    public List<Partida> obtenerPartidasPorPuntajeDesc() {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Partida.class);
        criteria.addOrder(Order.desc("puntaje"));


        return criteria.list();
    }

    @Override
    public List<Partida> obtenerPartidasPorCriterio(String dificultad, String orden) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Partida.class);
        criteria.add(Restrictions.eq("dificultad", dificultad));

        if (orden.equals("tiempoSegundos")) {
            criteria.addOrder(Order.asc(orden));
        }
        criteria.addOrder(Order.desc("puntaje"));
        return criteria.list();
    }

    @Override
    public List<Partida> obtenerPartidasPorTiempoAsc() {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Partida.class);
        criteria.addOrder(Order.asc("tiempoSegundos"));


        return criteria.list();
    }
}
