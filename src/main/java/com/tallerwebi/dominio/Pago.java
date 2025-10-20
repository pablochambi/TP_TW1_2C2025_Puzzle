package com.tallerwebi.dominio;

import com.tallerwebi.dominio.enums.PaqueteMonedas;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Pago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String collectionId;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @Enumerated(EnumType.STRING)
    private PaqueteMonedas paquete;

    public Pago() {}

    public Pago(String collectionId, Usuario usuario, PaqueteMonedas paquete) {
        this.collectionId = collectionId;
        this.usuario = usuario;
        this.paquete = paquete;
    }
}
