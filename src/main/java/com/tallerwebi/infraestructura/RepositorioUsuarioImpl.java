package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.*;
import com.tallerwebi.dominio.interfaces.RepositorioUsuario;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("repositorioUsuario")
public class RepositorioUsuarioImpl implements RepositorioUsuario {

    @Autowired
    SessionFactory sessionFactory;

    @Override
    public Usuario buscarUsuario(String email, String password) {

        final Session session = sessionFactory.getCurrentSession();
        return (Usuario) session.createCriteria(Usuario.class)
                .add(Restrictions.eq("email", email))
                .add(Restrictions.eq("password", password))
                .uniqueResult();
    }

    @Override
    public void guardar(Usuario usuario) {
        sessionFactory.getCurrentSession().save(usuario);
    }

    @Override
    public Usuario buscar(String email) {
        return (Usuario) sessionFactory.getCurrentSession().createCriteria(Usuario.class)
                .add(Restrictions.eq("email", email))
                .uniqueResult();
    }

    @Override
    public void modificar(Usuario usuario) {
        sessionFactory.getCurrentSession().update(usuario);
    }


    @Override
    public Usuario obtenerUsuarioPorId(Long id_usuario) {
        return sessionFactory.getCurrentSession().get(Usuario.class, id_usuario);
    }

    @Override
    public Integer obtenerMonedasUsuario(Long idUsuario) {
        Usuario usuario = obtenerUsuarioPorId(idUsuario);
        return usuario.getMonedas();
    }

    @Override
    public List<Usuario> obtenerUsuariosConMasPartidasGanadas() {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Usuario.class);
        criteria.addOrder(Order.desc("partidasGanadas"));


        return criteria.list();
    }


}
