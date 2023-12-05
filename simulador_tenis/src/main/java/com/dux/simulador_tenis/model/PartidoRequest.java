package com.dux.simulador_tenis.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class PartidoRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String jugador1;
    private String jugador2;
    private String nombreTorneo;
    private int sets;
    private int probabilidadJugador1;
    private int probabilidadJugador2;
}
