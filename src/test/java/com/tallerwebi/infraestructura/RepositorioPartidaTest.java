package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Partida;
import com.tallerwebi.dominio.Usuario;
import com.tallerwebi.dominio.enums.NIVEL;
import com.tallerwebi.dominio.interfaces.RepositorioPartida;
import com.tallerwebi.integracion.config.HibernateTestConfig;
import com.tallerwebi.integracion.config.SpringWebTestConfig;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.transaction.Transactional;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = {SpringWebTestConfig.class, HibernateTestConfig.class})
public class RepositorioPartidaTest {


    @Autowired
    private RepositorioPartida repositorioPartida;

    @Autowired
    SessionFactory sessionFactory;


    @Test
    @Transactional
    @Rollback
    public void obtenerPartidasPorUsuarioYNivel() {
        Usuario usuario = givenExisteUsuario("usuario1", "password1");

        givenExistePartidasDelUsuario(usuario);

        List<Partida> partidaListOb = repositorioPartida.obtenerPartidasPorUsuarioYNivel(1L, NIVEL.MEDIO);

        thenLasPartidasSeObtienenCorrectamente(partidaListOb,2);
    }




    private void thenLasPartidasSeObtienenCorrectamente(List<Partida> partidaList, int cantidadEsperada) {
        assertThat(partidaList.size() , equalTo(cantidadEsperada));
    }

    private List<Partida> givenExistePartidasDelUsuario(Usuario usuario) {

        Partida partida1 = new Partida();
        partida1.setUsuario(usuario);
        partida1.setNivel(NIVEL.FACIL);
        sessionFactory.getCurrentSession().save(partida1);

        Partida partida2 = new Partida();
        partida2.setUsuario(usuario);
        partida2.setNivel(NIVEL.MEDIO);
        sessionFactory.getCurrentSession().save(partida2);

        Partida partida3 = new Partida();
        partida3.setUsuario(usuario);
        partida3.setNivel(NIVEL.DIFICIL);
        sessionFactory.getCurrentSession().save(partida3);

        Partida partida4 = new Partida();
        partida4.setUsuario(usuario);
        partida4.setNivel(NIVEL.MEDIO);
        sessionFactory.getCurrentSession().save(partida4);

        return sessionFactory.getCurrentSession().createCriteria(Partida.class)
                .add(Restrictions.eq("usuario", usuario))
                .list();

    }

    private Usuario givenExisteUsuario(String email, String password) {

        Usuario usuario = new Usuario();
        usuario.setEmail(email);
        usuario.setPassword(password);
        sessionFactory.getCurrentSession().save(usuario);

        return usuario;
    }


}
