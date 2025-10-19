package com.tallerwebi.dominio;

import com.tallerwebi.infraestructura.RepositorioUsuarioImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ServicioPartidaImpl implements ServicioPartida{

    private final RepositorioUsuario repositorioUsuario;
    private final RepositorioPartida repositorioPartida;

    @Autowired
    public ServicioPartidaImpl(RepositorioPartida repositorioPartida, RepositorioUsuario repositorioUsuario) {
        this.repositorioPartida = repositorioPartida;
        this.repositorioUsuario = repositorioUsuario;
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

        // Convertir a DTO
        List<PartidaDTO> dtoList = new ArrayList<>();
        for (Partida p : partidas) {
            PartidaDTO dto = new PartidaDTO(
                    p.getDificultad(),
                    formatearTiempo(p.getTiempoSegundos()),
                    p.getPuntaje(),
                    p.getGanada(),
                    p.getPistasUsadas(),
                    formatearFecha(p.getFechaHoraInicio())
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

        if (horas > 0) {
            return String.format("%d:%02d:%02d", horas, minutos, segundos);
        } else {
            return String.format("%d:%02d", minutos, segundos);
        }
    }


}
