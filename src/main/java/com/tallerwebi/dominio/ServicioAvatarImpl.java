package com.tallerwebi.dominio;

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

    @Autowired
    public ServicioAvatarImpl(RepositorioAvatar repositorioAvatar,Repositorio_usuarioAvatar repositorio_usuarioAvatar) {
        this.repositorioAvatar = repositorioAvatar;
        this.repositorio_usuarioAvatar = repositorio_usuarioAvatar;
    }

    @Override
    public List<Avatar> obtenerAvataresDisponibles() {
        return repositorioAvatar.obtenerAvataresDisponibles();
    }

    @Override
    public List<AvatarDTO> obtenerAvataresDTO(Long idUsuario) {
        return pasarAAvataresDTOconLosDatosDeLaTablaUsuarioAvatar(obtenerAvataresDisponibles(), idUsuario);
    }

    @Override
    public void comprarAvatar(Long idUsuario, Long avatarId) {
        repositorio_usuarioAvatar.relacionarUsuarioConAvatar(idUsuario, avatarId);
    }

    //Hacer Tests

    private List<AvatarDTO> pasarAAvataresDTOconLosDatosDeLaTablaUsuarioAvatar(List<Avatar> avatars, Long idUsuario) {
        List<AvatarDTO> avatarDTOS = new ArrayList<>();

        for (Avatar avatar : avatars) {

            AvatarDTO avatarDTO = new AvatarDTO();

            Boolean comprado = repositorio_usuarioAvatar.comprobarSiElAvatarEstaCompradoPorElUsuario(avatar.getId(),idUsuario);
            Boolean enUso = repositorio_usuarioAvatar.comprobarSiElAvatarEstaEnUsoPorElUsuario(avatar.getId(),idUsuario);

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

}
