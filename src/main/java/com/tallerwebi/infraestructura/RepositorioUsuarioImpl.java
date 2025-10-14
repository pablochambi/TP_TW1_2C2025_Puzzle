package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

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
    public UsuarioDTO actualizarPerfil(Long id_usuario, String nuevoNombre, Long id_avatar, String nuevaPassword) {
        Session session = sessionFactory.getCurrentSession();
        UsuarioDTO usuarioDTO = null;

        // 1. Buscar usuario
        Usuario usuario = session.get(Usuario.class, id_usuario);
        if (usuario == null) {
            throw new RuntimeException("Usuario no encontrado");
        }

        // 2. Desactivar avatar actual (si hay uno)
        Usuario_Avatar avatarActual = obtenerAvatarEnUso(usuario);
        if (avatarActual != null) {
            avatarActual.setEn_uso(false);
            session.update(avatarActual);
        }

        // 3. Activar el nuevo avatar, solo si se seleccionó uno válido (>0)


        if (id_avatar != null && id_avatar > 0) {
            Avatar nuevoAvatar = session.get(Avatar.class, id_avatar);

            if (nuevoAvatar == null) {
                throw new RuntimeException("Avatar no encontrado");
            }

            // Verificar que el usuario posea ese avatar
            Usuario_Avatar usuarioAvatar = buscarRelacionEntreUsuarioYAvatar(usuario, nuevoAvatar);
            if (usuarioAvatar == null) {
                throw new RuntimeException("El usuario no posee el avatar seleccionado");
            }

            usuarioAvatar.setEn_uso(true);
            session.update(usuarioAvatar);

            usuarioDTO = new UsuarioDTO(usuario, nuevoAvatar);
        }

        // 4. Actualizar datos del usuario
        usuario.setNombreUsuario(nuevoNombre);
        usuario.setPassword(nuevaPassword);
        session.update(usuario);

        return usuarioDTO;
    }


    private Usuario_Avatar obtenerAvatarEnUso(Usuario usuario) {
        return (Usuario_Avatar) sessionFactory.getCurrentSession()
                .createCriteria(Usuario_Avatar.class)
                .add(Restrictions.eq("usuario", usuario))
                .add(Restrictions.eq("en_uso", true))
                .uniqueResult();
    }


    private Usuario_Avatar buscarRelacionEntreUsuarioYAvatar(Usuario usuario, Avatar avatar) {
        return (Usuario_Avatar) sessionFactory.getCurrentSession()
                .createCriteria(Usuario_Avatar.class)
                .add(Restrictions.eq("usuario", usuario))
                .add(Restrictions.eq("avatar", avatar))
                .uniqueResult();
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
