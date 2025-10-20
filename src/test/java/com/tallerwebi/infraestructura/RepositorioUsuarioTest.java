package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.interfaces.RepositorioUsuario;
import com.tallerwebi.dominio.Usuario;
import com.tallerwebi.integracion.config.HibernateTestConfig;
import com.tallerwebi.integracion.config.SpringWebTestConfig;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.transaction.Transactional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = {SpringWebTestConfig.class, HibernateTestConfig.class})
public class RepositorioUsuarioTest {

    @Autowired
    RepositorioUsuario repositorioUsuario;

    @Autowired
    SessionFactory sessionFactory;

    @Test
    @Transactional
    @Rollback
    public void queSePuedaInsertarUnUsuarioParaTestear() {
        Usuario usuario = new Usuario();
        usuario.setEmail("test@test.com");
        usuario.setPassword("password123");

        repositorioUsuario.guardar(usuario);
    }

    @Test
    @Transactional
    @Rollback
    public void queSePuedaRegistrarUnUsuarioCon100MonedasY5PistasIniciales() {
        Usuario usuario = whenSeRegistraUnUsuario("test@test.com", "password123");
        thenElUsuarioSeRegistraCorrectamente(usuario);
    }

    @Test
    @Transactional
    @Rollback
    public void queSePuedaBuscarUnUsuarioPorEmailYPassword() {
        dadoQueExisteUnUsuarioEnLaBase("test@test.com", "password123");
        Usuario usuario = whenSeBuscaUnUsuario("test@test.com", "password123");
        thenSeEncuentraElUsuario(usuario, "test@test.com");
    }

    @Test
    @Transactional
    @Rollback
    public void queNoSeEncuentreUnUsuarioConPasswordIncorrecta() {
        dadoQueExisteUnUsuarioEnLaBase("test@test.com", "password123");
        Usuario usuario = whenSeBuscaUnUsuario("test@test.com", "passwordIncorrecta");
        thenNoSeEncuentraElUsuario(usuario);
    }

    @Test
    @Transactional
    @Rollback
    public void queNoSeEncuentreUnUsuarioConEmailIncorrecto() {
        dadoQueExisteUnUsuarioEnLaBase("test@test.com", "password123");
        Usuario usuario = whenSeBuscaUnUsuario("otro@test.com", "password123");
        thenNoSeEncuentraElUsuario(usuario);
    }

    @Test
    @Transactional
    @Rollback
    public void queSePuedaBuscarUnUsuarioPorEmail() {
        dadoQueExisteUnUsuarioEnLaBase("test@test.com", "password123");
        Usuario usuario = whenSeBuscaUnUsuarioPorEmail("test@test.com");
        thenSeEncuentraElUsuario(usuario, "test@test.com");
    }

    @Test
    @Transactional
    @Rollback
    public void queNoSeEncuentreUnUsuarioConEmailInexistente() {
        Usuario usuario = whenSeBuscaUnUsuarioPorEmail("inexistente@test.com");
        thenNoSeEncuentraElUsuario(usuario);
    }

    @Test
    @Transactional
    @Rollback
    public void queSePuedaModificarUnUsuario() {
        Usuario usuario = dadoQueExisteUnUsuarioEnLaBase("test@test.com", "password123");
        whenSeModificaElUsuario(usuario, "nuevoNombre", 200, 10);
        thenElUsuarioSeModificaCorrectamente(usuario.getId(), "nuevoNombre", 200, 10);
    }

    @Test
    @Transactional
    @Rollback
    public void queSePuedaObtenerUnUsuarioPorId() {
        Usuario usuarioGuardado = dadoQueExisteUnUsuarioEnLaBase("test@test.com", "password123");
        Usuario usuario = whenSeObtienePorId(usuarioGuardado.getId());
        thenSeObtieneElUsuarioCorrecto(usuario, usuarioGuardado.getId(), "test@test.com");
    }

    @Test
    @Transactional
    @Rollback
    public void queNoSeEncuentreUnUsuarioConIdInexistente() {
        Usuario usuario = whenSeObtienePorId(999L);
        thenNoSeEncuentraElUsuario(usuario);
    }

    @Test
    @Transactional
    @Rollback
    public void queSePuedaObtenerLasMonedasDeUnUsuario() {
        Usuario usuario = dadoQueExisteUnUsuarioConMonedas("test@test.com", "password123", 150);
        Integer monedas = whenSeObtienenLasMonedasDelUsuario(usuario.getId());
        thenLasMonedasSonCorrectas(monedas, 150);
    }

    @Test
    @Transactional
    @Rollback
    public void queSePuedaObtenerLasMonedasInicialesDeUnUsuarioRecienRegistrado() {
        Usuario usuario = dadoQueExisteUnUsuarioEnLaBase("test@test.com", "password123");
        Integer monedas = whenSeObtienenLasMonedasDelUsuario(usuario.getId());
        thenLasMonedasSonCorrectas(monedas, 100);
    }

//    @Test
//    @Transactional
//    @Rollback
//    public void queSePuedaAgregarMonedasAlUsuario() {
//        Usuario usuario = dadoQueExisteUnUsuarioEnLaBase("test@test.com", "password123");
//
//        usuario.agregarMonedas(100);
//
//        Integer monedas = sessionFactory.getCurrentSession().get(Usuario.class, usuario.getId()).getMonedas();
//
//        assertThat(monedas, equalTo(201));
//    }



    // GIVEN
    private Usuario dadoQueExisteUnUsuarioEnLaBase(String email, String password) {
        Usuario usuario = new Usuario();
        usuario.setEmail(email);
        usuario.setPassword(password);
        repositorioUsuario.guardar(usuario);
        sessionFactory.getCurrentSession().flush();
        return usuario;
    }

    private Usuario dadoQueExisteUnUsuarioConMonedas(String email, String password, Integer monedas) {
        Usuario usuario = new Usuario();
        usuario.setEmail(email);
        usuario.setPassword(password);
        usuario.setMonedas(monedas);
        repositorioUsuario.guardar(usuario);
        sessionFactory.getCurrentSession().flush();
        return usuario;
    }

    // WHEN
    private Usuario whenSeRegistraUnUsuario(String email, String password) {
        Usuario usuario = new Usuario();
        usuario.setEmail(email);
        usuario.setPassword(password);
        repositorioUsuario.guardar(usuario);
        return usuario;
    }

    private Usuario whenSeBuscaUnUsuario(String email, String password) {
        return repositorioUsuario.buscarUsuario(email, password);
    }

    private Usuario whenSeBuscaUnUsuarioPorEmail(String email) {
        return repositorioUsuario.buscar(email);
    }

    private void whenSeModificaElUsuario(Usuario usuario, String nuevoNombre, Integer monedas, Integer pistas) {
        usuario.setNombreUsuario(nuevoNombre);
        usuario.setMonedas(monedas);
        usuario.setPistas(pistas);
        repositorioUsuario.modificar(usuario);
        sessionFactory.getCurrentSession().flush();
    }

    private Usuario whenSeObtienePorId(Long id) {
        return repositorioUsuario.obtenerUsuarioPorId(id);
    }

    private Integer whenSeObtienenLasMonedasDelUsuario(Long idUsuario) {
        return repositorioUsuario.obtenerMonedasUsuario(idUsuario);
    }

    // THEN
    private void thenElUsuarioSeRegistraCorrectamente(Usuario usuario) {
        Usuario usuarioEnSesion = sessionFactory.getCurrentSession().get(Usuario.class, usuario.getId());

        assertThat(usuario.getId(), notNullValue());

        assertThat(usuarioEnSesion.getNombreUsuario(), equalTo("jugador123"));
        assertThat(usuarioEnSesion.getMonedas(),       equalTo(100));
        assertThat(usuarioEnSesion.getPistas(),        equalTo(5));
    }

    private void thenSeEncuentraElUsuario(Usuario usuario, String emailEsperado) {
        assertThat(usuario, notNullValue());
        assertThat(usuario.getEmail(), equalTo(emailEsperado));
    }

    private void thenNoSeEncuentraElUsuario(Usuario usuario) {
        assertThat(usuario, nullValue());
    }

    private void thenElUsuarioSeModificaCorrectamente(Long id, String nombreEsperado, Integer monedasEsperadas, Integer pistasEsperadas) {
        Usuario usuarioModificado = sessionFactory.getCurrentSession().get(Usuario.class, id);

        assertThat(usuarioModificado, notNullValue());
        assertThat(usuarioModificado.getNombreUsuario(), equalTo(nombreEsperado));
        assertThat(usuarioModificado.getMonedas(), equalTo(monedasEsperadas));
        assertThat(usuarioModificado.getPistas(), equalTo(pistasEsperadas));
    }

    private void thenSeObtieneElUsuarioCorrecto(Usuario usuario, Long idEsperado, String emailEsperado) {
        assertThat(usuario, notNullValue());
        assertThat(usuario.getId(), equalTo(idEsperado));
        assertThat(usuario.getEmail(), equalTo(emailEsperado));
    }

    private void thenLasMonedasSonCorrectas(Integer monedas, Integer monedasEsperadas) {
        assertThat(monedas, notNullValue());
        assertThat(monedas, equalTo(monedasEsperadas));
    }
}