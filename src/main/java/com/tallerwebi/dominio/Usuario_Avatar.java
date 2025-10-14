package com.tallerwebi.dominio;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class Usuario_Avatar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Avatar avatar;

    @Column(nullable = false)
    private Boolean en_uso = false;

    private LocalDateTime fecha_comprada;

    public Usuario_Avatar() {}

    public Usuario_Avatar(Usuario usuario, Avatar avatar, Boolean en_uso, LocalDateTime fecha_comprada) {
        this.usuario = usuario;
        this.avatar = avatar;
        this.en_uso = en_uso;
        this.fecha_comprada = fecha_comprada;
    }
}
