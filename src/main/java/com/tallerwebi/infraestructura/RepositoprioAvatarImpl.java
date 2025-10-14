package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Avatar;
import com.tallerwebi.dominio.RepositorioAvatar;
import com.tallerwebi.dominio.Usuario;
import com.tallerwebi.dominio.Usuario_Avatar;
import jdk.jfr.Registered;
import org.hibernate.Criteria;
import org.hibernate.Session;
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

    @Override
    public Avatar buscarAvatarPorId(Long idAvatar) {
        return sessionFactory.getCurrentSession().get(Avatar.class, idAvatar);
    }

    @Override
    public Avatar obtenerAvatarDelUsuario(Usuario usuario) {

        Session session = sessionFactory.getCurrentSession();
        Usuario_Avatar ua = (Usuario_Avatar) session.createCriteria(Usuario_Avatar.class)
                .add(Restrictions.eq("usuario", usuario))
                .add(Restrictions.eq("en_uso", true))
                .uniqueResult();

        // Si no se encuentra ningún avatar en uso, retornar null
        if (ua == null) {
            return null;
        }

        return ua.getAvatar();

    }

    //INSERT INTO Usuario_Avatar (usuario_id, avatar_id)
    //SELECT <usuarioId>, a.id
    //FROM Avatar a
    //WHERE a.precio = 0;

    @Override
    public void agregarAvataresGratuitosAlUsuario(Usuario usuario) {
        Session session = sessionFactory.getCurrentSession();

        // 1 Buscar los avatares gratuitos
        Criteria criteria = session.createCriteria(Avatar.class);
        criteria.add(Restrictions.eq("precio", 0));
        List<Avatar> avataresGratuitos = criteria.list();

        // 2 Crear la relación Usuario_Avatar
        for (Avatar avatar : avataresGratuitos) {
            Usuario_Avatar relacion = new Usuario_Avatar();
            relacion.setUsuario(usuario);
            relacion.setAvatar(avatar);
            session.save(relacion);
        }
    }




}
