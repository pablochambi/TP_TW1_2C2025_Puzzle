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

    //SELECT *
    //FROM usuario_avatar
    //WHERE id_avatar = ? AND id_usuario = ?
    //LIMIT 1;

    @Override
    public Boolean comprobarSiElAvatarEstaCompradoPorElUsuario(Long id_avatar, Long id_usuario) {

        Boolean comprado = sessionFactory.getCurrentSession()
                .createCriteria(Usuario_Avatar.class)
                .add(Restrictions.eq("avatar.id", id_avatar))
                .add(Restrictions.eq("usuario.id", id_usuario))
                .uniqueResult() != null;

        return comprado;
    }

    @Override
    public Boolean comprobarSiElAvatarEstaEnUsoPorElUsuario(Long id_avatar, Long id_usuario) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Usuario_Avatar.class)
                .add(Restrictions.eq("avatar.id", id_avatar))
                .add(Restrictions.eq("usuario.id", id_usuario))
                .add(Restrictions.eq("en_uso", true));

        @SuppressWarnings("unchecked")
        List<Usuario_Avatar> resultados = criteria.list();

        if (resultados.isEmpty()) {
            return false; // No está en uso
        } else if (resultados.size() == 1) {
            return true; // Está en uso
        } else {
            // ⚠️ Hay más de un resultado, lo cual no debería pasar
            throw new IllegalStateException(
                    "Error de integridad: el usuario con id " + id_usuario +
                            " tiene múltiples avatares en uso con id " + id_avatar
            );
        }
    }

    public void relacionarUsuarioConAvatar(Long idUsuario, Long avatarId) {
        Session session = sessionFactory.getCurrentSession();

        Usuario usuario = session.get(Usuario.class, idUsuario);
        Avatar avatar = session.get(Avatar.class, avatarId);

        if (usuario == null || avatar == null) {
            throw new IllegalArgumentException("Usuario o avatar no encontrado.");
        }

        if (usuario.getMonedas() < avatar.getPrecio()) {
            throw new IllegalStateException(
                    "No tienes suficientes monedas. Necesitas: " + avatar.getPrecio() +
                            ", tienes: " + usuario.getMonedas());
        }

        Integer nuevasMonedas = usuario.getMonedas() - avatar.getPrecio();
        usuario.setMonedas(nuevasMonedas);

        Usuario_Avatar relacion = new Usuario_Avatar(usuario, avatar, false, LocalDateTime.now());
        session.save(relacion);

    }

}
