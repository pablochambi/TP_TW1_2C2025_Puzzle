package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Avatar;
import com.tallerwebi.dominio.RepositorioUsuario;
import com.tallerwebi.dominio.Usuario;
import com.tallerwebi.dominio.Usuario_Avatar;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("repositorioUsuario")
public class RepositorioUsuarioImpl implements RepositorioUsuario {

    private SessionFactory sessionFactory;

    @Autowired
    public RepositorioUsuarioImpl(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }

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
    public void actualizarPerfil(Long id_usuario, String nuevoNombre, String nuevaUrlImagen, String nuevoIconoHexadecimal, String nuevaPassword) {

        Usuario usuario = obtenerUsuarioPorId(id_usuario);

        boolean esIcono = nuevoIconoHexadecimal != null;
        boolean tieneImagen = nuevaUrlImagen != null;

        if (!esIcono && !tieneImagen) {
            throw new RuntimeException("No se puede modificar la imagen del usuario");
        }

        usuario.setNombreUsuario(nuevoNombre);
        usuario.setPassword(nuevaPassword);

        sessionFactory.getCurrentSession().update(usuario);

    }

    @Override
    public void actualizarNombreYPassword(Long idUsuario, String nuevoNombre, String nuevaPassword) {
        Session session = sessionFactory.getCurrentSession();

        Usuario usuario = session.get(Usuario.class, idUsuario);

        usuario.setNombreUsuario(nuevoNombre);
        usuario.setPassword(nuevaPassword);

        session.update(usuario);
    }

    @Override
    public Integer obtenerMonedasUsuario(Long idUsuario) {
        Usuario usuario = obtenerUsuarioPorId(idUsuario);
        return usuario.getMonedas();
    }

    /*
    SELECT a.*
    FROM usuario_avatar ua
    JOIN avatar a ON ua.id_avatar = a.id
        WHERE ua.usuario_id = [:id_usuario]
        AND ua.en_uso = TRUE;
    * */

    private Avatar buscarAvatarDeUsuario(Long id_usuario) {
        return (Avatar) sessionFactory.getCurrentSession()
                .createCriteria(Usuario_Avatar.class)
                .createAlias("usuario", "u")
                .createAlias("avatar", "a")
                .add(Restrictions.eq("u.id", id_usuario))
                .add(Restrictions.eq("en_uso", true))
                .setProjection(Projections.property("a"))
                .uniqueResult();
    }


}
