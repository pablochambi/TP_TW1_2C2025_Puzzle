package com.tallerwebi.dominio;

import com.tallerwebi.infraestructura.RepositorioUsuarioImpl;
import com.tallerwebi.presentacion.AvatarDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ServicioAvatarImpl implements ServicioAvatar {

    private final RepositorioAvatar repositorioAvatar;
    private final Repositorio_usuarioAvatar repositorio_usuarioAvatar;
    private final RepositorioUsuario repositorioUsuario;

    @Autowired
    public ServicioAvatarImpl(RepositorioAvatar repositorioAvatar,
                              Repositorio_usuarioAvatar repositorio_usuarioAvatar,
                              RepositorioUsuario repositorioUsuario) {

        this.repositorioAvatar = repositorioAvatar;
        this.repositorio_usuarioAvatar = repositorio_usuarioAvatar;
        this.repositorioUsuario = repositorioUsuario;
    }

    @Override
    public List<Avatar> obtenerAvataresDisponibles() {
        return repositorioAvatar.obtenerAvataresDisponibles();
    }

    @Override
    public List<AvatarDTO> obtenerAvataresDTO(Long idUsuario) {

        Usuario usuario = repositorioUsuario.obtenerUsuarioPorId(idUsuario);
        if (usuario == null) {
            throw new IllegalArgumentException("Usuario no encontrado.");
        }

        List<AvatarDTO> avatarDTOS = new ArrayList<>();
        List<Avatar> avatars = obtenerAvataresDisponibles();

        for (Avatar avatar : avatars) {

            AvatarDTO avatarDTO = new AvatarDTO();

            Usuario_Avatar relacion = repositorio_usuarioAvatar
                                    .obtenerRelacionUsuarioAvatar(usuario, avatar);

            boolean comprado = relacion != null;
            boolean enUso = (relacion != null && relacion.getEn_uso());

            avatarDTO.setId(avatar.getId());
            avatarDTO.setNombre(avatar.getNombre());
            avatarDTO.setUrlImagen(avatar.getUrlImagen());
            avatarDTO.setComprado(comprado);
            avatarDTO.setEnUso(enUso);
            avatarDTO.setPrecio(avatar.getPrecio());
            avatarDTO.setIconoHexadecimal(avatar.getIconoHexadecimal());

            avatarDTOS.add(avatarDTO);
        }

        return avatarDTOS;
    }


    @Override
    public void comprarAvatar(Long idUsuario, Long avatarId) {

        Usuario usuario = repositorioUsuario.obtenerUsuarioPorId(idUsuario);
        Avatar avatar = repositorioAvatar.buscarAvatarPorId(avatarId);

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

        repositorio_usuarioAvatar.relacionarUsuarioConAvatar(usuario, avatar);
    }


}
