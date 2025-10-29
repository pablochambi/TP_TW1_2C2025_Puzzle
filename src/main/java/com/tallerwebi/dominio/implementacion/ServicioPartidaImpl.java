package com.tallerwebi.dominio.implementacion;

import com.tallerwebi.dominio.*;
import com.tallerwebi.dominio.enums.NIVEL;
import com.tallerwebi.dominio.interfaces.RepositorioAvatar;
import com.tallerwebi.dominio.interfaces.RepositorioPartida;
import com.tallerwebi.dominio.interfaces.RepositorioUsuario;
import com.tallerwebi.dominio.interfaces.ServicioPartida;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@Transactional
public class ServicioPartidaImpl implements ServicioPartida {

    private final RepositorioUsuario repositorioUsuario;
    private final RepositorioPartida repositorioPartida;
    private final RepositorioAvatar repositorioAvatar;

    @Autowired
    public ServicioPartidaImpl(RepositorioPartida repositorioPartida, RepositorioUsuario repositorioUsuario, RepositorioAvatar repositorioAvatar) {
        this.repositorioPartida = repositorioPartida;
        this.repositorioUsuario = repositorioUsuario;
        this.repositorioAvatar = repositorioAvatar;
    }

    @Override
    public List<Partida> obtenerTodasLasPartidas(Long idUsuario) {
        Usuario usuario = repositorioUsuario.obtenerUsuarioPorId(idUsuario);
        if (usuario == null) {
            throw new IllegalArgumentException("Usuario no encontrado.");
        }
        return repositorioPartida.obtenerTodasLasPartidasDelUsuario(usuario);
    }

    @Override
    public List<PartidaDTO> obtenerTodasLasPartidasDTO(Long idUsuario) {
        // Obtener todas las partidas
        List<Partida> partidas = obtenerTodasLasPartidas(idUsuario);

        // Ordenar del más antiguo al más reciente
        partidas.sort(Comparator.comparing(Partida::getFechaHoraInicio));

        List<PartidaDTO> dtoList = formatearDePartidasAPartidasDTO(partidas);

        return dtoList;
    }

    @Override
    public List<PartidaDTO> obtenerTodasLasPartidasDTODelMasRecienteAlMasAntiguo(Long idUsuario) {

        List<Partida> partidas = obtenerTodasLasPartidas(idUsuario);
        
        //Ordena Del Mas Reciente Al Mas Antiguo
        partidas.sort(Comparator.comparing(Partida::getFechaHoraInicio).reversed());

        List<PartidaDTO> dtoList = formatearDePartidasAPartidasDTO(partidas);

        return dtoList;
    }
    @Override
    public List<PartidaDTO> obtenerPartidasPorCriterio(String dificultad, String orden) {
        List<Partida> partidas = new ArrayList<>();
        List<PartidaDTO> dtoList;

        if (dificultad.equalsIgnoreCase("general") && orden.equalsIgnoreCase("tiempo")) {
            dtoList = obtenerPartidasDTOOrdenadasPorTiempo();
            return dtoList;
        }else if (dificultad.equalsIgnoreCase("general") && orden.equalsIgnoreCase("puntaje")) {
            dtoList = obtenerPartidasDTOOrdenadasPorPuntaje();
            return dtoList;
        }

        if (orden.equalsIgnoreCase("partidas")) {
            List<Usuario> usuariosOrdenadosPorPartidasGanadas= repositorioUsuario.obtenerUsuariosConMasPartidasGanadas();
            for (Usuario usuario : usuariosOrdenadosPorPartidasGanadas) {
                partidas.add(obtenerMejorPartidaUsuario(usuario));


            }
            return formatearDePartidasAPartidasDTO(partidas);
        }

        if (orden.equalsIgnoreCase("tiempo")) {
            orden = "tiempoSegundos";
        }



        NIVEL nivel = NIVEL.valueOf(dificultad.toUpperCase());
        partidas = repositorioPartida.obtenerPartidasPorCriterio(nivel, orden);

        return formatearDePartidasAPartidasDTO(partidas);
    }

    private Partida obtenerMejorPartidaUsuario(Usuario usuario) {
        Partida partida = repositorioPartida.obtenerMejorPartidaUsuario(usuario);

        return partida;
    }

    @Override
    public List<PartidaDTO> obtenerPartidasDTOOrdenadasPorPuntaje() {
        List<Partida> partidas;
        List<PartidaDTO> dtoList;
        partidas = repositorioPartida.obtenerPartidasPorPuntajeDesc();
        dtoList = formatearDePartidasAPartidasDTO(partidas);



        return dtoList;
    }



    @Override
    public List<PartidaDTO> obtenerPartidasDTOOrdenadasPorTiempo() {
        List<Partida> partidas;
        List<PartidaDTO> dtoList;
        partidas = repositorioPartida.obtenerPartidasPorTiempoAsc();
        dtoList = formatearDePartidasAPartidasDTO(partidas);



        return dtoList;
    }

    private List<PartidaDTO> formatearDePartidasAPartidasDTO(List<Partida> partidas) {
        // Convertir a DTO
        List<PartidaDTO> dtoList = new ArrayList<>();
        for (Partida p : partidas) {
            PartidaDTO dto = new PartidaDTO(
                    p.getNivel().name(),
                    formatearTiempo(p.getTiempoSegundos()),
                    p.getPuntaje(),
                    p.getGanada(),
                    p.getPistasUsadas(),
                    formatearFecha(p.getFechaHoraInicio()),
                    new UsuarioDTO(p.getUsuario(), repositorioAvatar.obtenerAvatarDelUsuario(p.getUsuario()))
            );
            dtoList.add(dto);
        }
        return dtoList;
    }


    private String formatearFecha(LocalDateTime fechaHora) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm a");
        return fechaHora.format(formatter);
    }

    private String formatearTiempo(int totalSegundos) {
        long horas = totalSegundos / 3600;
        long minutos = (totalSegundos % 3600) / 60;
        long segundos = totalSegundos % 60;

        return String.format("%d:%02d:%02d", horas, minutos, segundos);

    }



}
