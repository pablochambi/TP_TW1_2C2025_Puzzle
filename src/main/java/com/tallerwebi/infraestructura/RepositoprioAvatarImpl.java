package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Avatar;
import com.tallerwebi.dominio.RepositorioAvatar;
import jdk.jfr.Registered;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RepositoprioAvatarImpl implements RepositorioAvatar {


    SessionFactory sessionFactory;

    public RepositoprioAvatarImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Avatar> obtenerAvataresDisponibles() {
        return sessionFactory.getCurrentSession().createCriteria(Avatar.class).list();
    }


    //FROM Avatar WHERE urlImagen = :urlImagen
    @Override
    public Avatar buscarAvatarPorUrlImagen(String urlImagen) {
        return (Avatar) sessionFactory.getCurrentSession()
                .createCriteria(Avatar.class)
                .add(Restrictions.eq("urlImagen", urlImagen))
                .uniqueResult();
    }

}
