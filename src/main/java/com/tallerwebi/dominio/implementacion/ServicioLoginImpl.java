package com.tallerwebi.dominio.implementacion;
import com.tallerwebi.dominio.*;
import com.tallerwebi.dominio.excepcion.UsuarioExistente;
import com.tallerwebi.dominio.interfaces.RepositorioAvatar;
import com.tallerwebi.dominio.interfaces.RepositorioUsuario;
import com.tallerwebi.dominio.interfaces.Repositorio_usuarioAvatar;
import com.tallerwebi.dominio.interfaces.ServicioLogin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service("servicioLogin")
@Transactional
public class ServicioLoginImpl implements ServicioLogin {

    private RepositorioUsuario repositorioUsuario;
    private RepositorioAvatar repositorioAvatar;
    private Repositorio_usuarioAvatar repositorio_usuarioAvatar;

    @Autowired
    public ServicioLoginImpl(RepositorioUsuario repositorioUsuario,RepositorioAvatar repositorioAvatar, Repositorio_usuarioAvatar repositorio_usuarioAvatar) {
        this.repositorioUsuario = repositorioUsuario;
        this.repositorioAvatar = repositorioAvatar;
        this.repositorio_usuarioAvatar = repositorio_usuarioAvatar;
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
        repositorioAvatar.agregarAvataresGratuitosAlUsuario(usuario);

    }
    @Override
    public UsuarioDTO actualizarPerfil(Long idUsuario, String nuevoNombre, Long id_avatar, String nuevaPassword) {

        Usuario usuario = obtenerUsuarioPorId(idUsuario);

        actualizarDatosBasicos(usuario, nuevoNombre, nuevaPassword);

        desactivarAvatarActual(usuario);

        Avatar avatarNuevo = activarNuevoAvatarSiCorresponde(usuario, id_avatar);

        return new UsuarioDTO(usuario, avatarNuevo);
    }


    @Override
    public Usuario consultarUsuarioPorId(Long id_usuario) {
        return repositorioUsuario.obtenerUsuarioPorId(id_usuario);
    }

    @Override
    public Integer obtenerMonedas(Long idUsuario) {
        return repositorioUsuario.obtenerMonedasUsuario(idUsuario);
    }

    @Override
    public UsuarioDTO consultarUsuarioDTOPorId(Long idUsuario) {

        Usuario usuario = repositorioUsuario.obtenerUsuarioPorId(idUsuario);
        Avatar avatar = repositorioAvatar.obtenerAvatarDelUsuario(usuario);

        return new UsuarioDTO(usuario,avatar);
    }


// --- Métodos privados auxiliares ---

    private Usuario obtenerUsuarioPorId(Long idUsuario) {
        Usuario usuario = repositorioUsuario.obtenerUsuarioPorId(idUsuario);
        if (usuario == null) {
            throw new RuntimeException("Usuario no encontrado");
        }
        return usuario;
    }

    private void actualizarDatosBasicos(Usuario usuario, String nuevoNombre, String nuevaPassword) {
        usuario.setNombreUsuario(nuevoNombre);
        usuario.setPassword(nuevaPassword);
        repositorioUsuario.modificar(usuario);
    }

    private void desactivarAvatarActual(Usuario usuario) {
        Usuario_Avatar avatarActual = repositorio_usuarioAvatar.obtenerAvatarEnUsoDelUsuario(usuario);
        if (avatarActual != null) {
            avatarActual.setEn_uso(false);
            repositorio_usuarioAvatar.actualizar(avatarActual);
        }
    }

    private Avatar activarNuevoAvatarSiCorresponde(Usuario usuario, Long id_avatar) {
        if (id_avatar == null || id_avatar <= 0) {
            return null;
        }

        Avatar nuevoAvatar = repositorioAvatar.buscarAvatarPorId(id_avatar);
        if (nuevoAvatar == null) {
            throw new RuntimeException("Avatar no encontrado");
        }

        Usuario_Avatar usuarioAvatar = repositorio_usuarioAvatar.obtenerRelacionUsuarioAvatar(usuario, nuevoAvatar);
        if (usuarioAvatar == null) {
            throw new RuntimeException("El usuario no posee el avatar seleccionado");
        }

        usuarioAvatar.setEn_uso(true);
        repositorio_usuarioAvatar.actualizar(usuarioAvatar);

        return nuevoAvatar;
    }

}

