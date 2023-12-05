package com.dux.simulador_tenis.controller;

import com.dux.simulador_tenis.model.PartidoRequest;
import com.dux.simulador_tenis.service.PartidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/apiDux/tenis")
public class PartidoController {

    @Autowired
    private PartidoService partidoService;

    @PostMapping("/simular")
    public List<Map<String, String>> simularPartido(@RequestBody PartidoRequest partidoRequest) {
        return partidoService.simularPartido(partidoRequest);
    }
    /* Ejemplo de Json esperado por el post
        {
                "jugador1":"Federer",
                "jugador2":"Nadal",
                "sets":"5",
                "probabilidadJugador1":50,
                "probabilidadJugador2":50,
                "nombreTorneo":"Wimbledon"
        }

     */


}

