package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Avatar;
import com.tallerwebi.dominio.Repositorio_usuarioAvatar;
import com.tallerwebi.dominio.Usuario;
import com.tallerwebi.dominio.Usuario_Avatar;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class Repositorio_usuarioAvatarImpl implements Repositorio_usuarioAvatar {


    private final SessionFactory sessionFactory;

    @Autowired
    public Repositorio_usuarioAvatarImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }




    @Override
    public Usuario_Avatar obtenerAvatarEnUsoDelUsuario(Usuario usuario) {
        return (Usuario_Avatar) sessionFactory.getCurrentSession().createCriteria(Usuario_Avatar.class)
                .add(Restrictions.eq("usuario", usuario))
                .add(Restrictions.eq("en_uso", true))
                .uniqueResult();
    }

    @Override
    public void actualizar(Usuario_Avatar avatarActual) {
        sessionFactory.getCurrentSession().update(avatarActual);
    }


    @Override
    public void relacionarUsuarioConAvatar(Usuario usuario, Avatar avatar) {

        Usuario_Avatar relacion = new Usuario_Avatar();
        relacion.setUsuario(usuario);
        relacion.setAvatar(avatar);
        relacion.setEn_uso(false);
        relacion.setFecha_comprada(LocalDateTime.now());

        sessionFactory.getCurrentSession().save(relacion);
    }
    //SELECT *
    //FROM usuario_avatar
    //WHERE id_avatar = ? AND id_usuario = ?
    //LIMIT 1;
    @Override
    public Usuario_Avatar obtenerRelacionUsuarioAvatar(Usuario usuario, Avatar avatar) {
        return (Usuario_Avatar) sessionFactory.getCurrentSession()
                .createCriteria(Usuario_Avatar.class)
                .add(Restrictions.eq("usuario", usuario))
                .add(Restrictions.eq("avatar", avatar))
                .uniqueResult();
    }

}
