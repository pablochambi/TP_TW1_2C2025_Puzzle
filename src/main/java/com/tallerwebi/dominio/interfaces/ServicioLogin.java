package com.tallerwebi.dominio.interfaces;

import com.tallerwebi.dominio.Usuario;
import com.tallerwebi.dominio.UsuarioDTO;
import com.tallerwebi.dominio.excepcion.UsuarioExistente;

public interface ServicioLogin {

    Usuario consultarUsuario(String email, String password);
    void registrar(Usuario usuario) throws UsuarioExistente;

    UsuarioDTO actualizarPerfil(Long idUsuario, String nuevoNombre,
                                Long id_avatar, String nuevaPassword);

    Usuario consultarUsuarioPorId(Long id_usuario);
    Integer obtenerMonedas(Long idUsuario);

    UsuarioDTO consultarUsuarioDTOPorId(Long idUsuario);
}
