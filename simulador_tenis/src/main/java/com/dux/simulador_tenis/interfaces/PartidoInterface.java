package com.dux.simulador_tenis.interfaces;

import com.dux.simulador_tenis.model.PartidoRequest;

import java.util.List;
import java.util.Map;

public interface PartidoInterface {
    List<Map<String, String>> simularPartido(PartidoRequest partidoRequest);
}
