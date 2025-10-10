package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.FormatoDeAvatarInvalido;
import com.tallerwebi.dominio.excepcion.UsuarioExistente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service("servicioLogin")
@Transactional
public class ServicioLoginImpl implements ServicioLogin {

    private RepositorioUsuario repositorioUsuario;
    private RepositorioAvatar repositorioAvatar;

    @Autowired
    public ServicioLoginImpl(RepositorioUsuario repositorioUsuario,RepositorioAvatar repositorioAvatar) {
        this.repositorioUsuario = repositorioUsuario;
        this.repositorioAvatar = repositorioAvatar;
    }

    @Override
    public Usuario consultarUsuario (String email, String password) {
        return repositorioUsuario.buscarUsuario(email, password);
    }

    @Override
    public void registrar(Usuario usuario) throws UsuarioExistente {
        Usuario usuarioEncontrado = repositorioUsuario.buscarUsuario(usuario.getEmail(), usuario.getPassword());
        if(usuarioEncontrado != null){
            throw new UsuarioExistente();
        }
        repositorioUsuario.guardar(usuario);
    }

    @Override
    public void actualizarPerfil(Long idUsuario, String nuevoNombre, Long id_avatar, String nuevaPassword) {

        repositorioUsuario.actualizarPerfil(idUsuario, nuevoNombre, id_avatar, nuevaPassword);

    }

    @Override
    public Usuario consultarUsuarioPorId(Long id_usuario) {
        return repositorioUsuario.obtenerUsuarioPorId(id_usuario);
    }

    @Override
    public Integer obtenerMonedas(Long idUsuario) {
        return repositorioUsuario.obtenerMonedasUsuario(idUsuario);
    }

}

