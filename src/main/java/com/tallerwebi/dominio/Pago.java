package com.tallerwebi.dominio;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@Setter
public class Pago {




    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long usuarioId;
    private Integer paqueteId;
    private String collectionId;


    public Pago() {

    }
    public Pago(String collectionId, Long usuarioId, Integer paqueteId) {
        this.collectionId = collectionId;
        this.usuarioId = usuarioId;
        this.paqueteId = paqueteId;

    }



}
