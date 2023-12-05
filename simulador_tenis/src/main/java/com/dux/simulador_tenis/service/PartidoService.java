package com.dux.simulador_tenis.service;

import com.dux.simulador_tenis.interfaces.PartidoInterface;
import com.dux.simulador_tenis.model.PartidoRequest;
import org.springframework.stereotype.Service;

import java.util.*;
@Service
public class PartidoService implements PartidoInterface {

    //Función principal que sirve para simular el partido
    @Override
    public List<Map<String, String>> simularPartido(PartidoRequest partidoRequest) {
        List<Map<String, String>> resultado = new ArrayList<>();

        // Obtener datos del PartidoRequest
        String jugador1 = partidoRequest.getJugador1();
        String jugador2 = partidoRequest.getJugador2();
        int sets = partidoRequest.getSets();
        int probabilidadJugador1 = partidoRequest.getProbabilidadJugador1();
        int probabilidadJugador2 = partidoRequest.getProbabilidadJugador2();
        String nombreTorneo = partidoRequest.getNombreTorneo();

        // Inicialización de contadores y variables a utilizar durante el programa
        int juegosJugador1 = 0;
        int juegosJugador2 = 0;
        int puntosJugador1 = 0;
        int puntosJugador2 = 0;
        int setsJugador1 = 0;
        int setsJugador2 = 0;
        int alLlegarA = 0;
        int set = 1;

        String resultadoJ1 = jugador1;
        String resultadoJ2 = jugador2;

        // Se determina a cuántos sets ganados tiene que llegar un jugador para finalizar el partido
        if (sets == 5) {
            alLlegarA = 3;
        } else if (sets == 3) {
            alLlegarA = 2;
        }

        //Se instancia un objeto de clase Random para útilizar luego y determinar el ganador del punto de forma aleatoria
        Random random = new Random();

        while (setsJugador1 < alLlegarA && setsJugador2 < alLlegarA) {
            resultado.add(crearMapaPunto("Bienvenido al torneo: " + nombreTorneo));
            resultado.add(crearMapaPunto("Comienza el partido entre: " + jugador1 + " y " + jugador2));
            resultado.add(crearMapaPunto("Inicio del set " + set));
            set++;
            if ((juegosJugador1 + juegosJugador2) % 2 == 0){
                resultado.add(crearMapaPunto(jugador1 + " tiene el servicio"));
            } else {
                resultado.add(crearMapaPunto(jugador2 + " tiene el servicio"));
            }
            while (!finDelSet(juegosJugador1, juegosJugador2)) {

                // Se simula un punto
                String ganadorPunto = simularPunto(probabilidadJugador1, probabilidadJugador2, random.nextInt(100));
                String nombreGanadorPunto = "";
                if (ganadorPunto.equals("Jugador1")) {
                    nombreGanadorPunto = jugador1;
                } else {
                    nombreGanadorPunto = jugador2;
                }

                if ("Jugador1".equals(ganadorPunto)) {
                    puntosJugador1++;
                } else {
                    puntosJugador2++;
                }

                // Se verifica el estado del juego (deuce, ventaja, fin del juego)
                if (puntosJugador1 == puntosJugador2 && puntosJugador1 >= 4 && puntosJugador2 >= 4) {
                    resultado.add(crearMapaPunto("Punto para " +nombreGanadorPunto+". Deuce"));
                    puntosJugador1 = 3;
                    puntosJugador2 = 3;
                } else if (puntosJugador1 >= 4 && puntosJugador1 == puntosJugador2 + 1) {
                    resultado.add(crearMapaPunto("Ventaja para " + jugador1));
                } else if (puntosJugador2 >= 4 && puntosJugador2 == puntosJugador1 + 1) {
                    resultado.add(crearMapaPunto("Ventaja para " + jugador2));
                } else if (puntosJugador1 >= 4 || puntosJugador2 >= 4
                        && Math.abs(puntosJugador1 - puntosJugador2) >= 2) {
                    // Fin del juego
                    if (puntosJugador1 > puntosJugador2) {
                        juegosJugador1++;
                        resultado.add(crearMapaPunto(" Punto para " + jugador1 +". Fin del game. Ganador: " + jugador1));
                    } else {
                        juegosJugador2++;
                        resultado.add(crearMapaPunto(" Punto para " + jugador2 +". Fin del game. Ganador: " + jugador2));
                    }
                    resultado.add(crearMapaPunto("Resultado parcial del set: "
                            + jugador1 + " " + juegosJugador1 + " "
                            + jugador2 + " " + juegosJugador2));
                    //Se reinician los puntos
                    puntosJugador1 = 0;
                    puntosJugador2 = 0;
                    //Se determina quien debe sacar, según si el game es impar o par, saca uno o el otro jugador
                    if ((juegosJugador1 + juegosJugador2) % 2 == 0){
                        resultado.add(crearMapaPunto(jugador1 + " tiene el servicio"));
                    } else {
                        resultado.add(crearMapaPunto(jugador2 + " tiene el servicio"));
                    }
                } else {
                    // Mostrar el marcador parcial del juego
                    resultado.add(crearMapaPunto("Punto para " + nombreGanadorPunto + ". Resultado parcial del game: "
                            + jugador1 + " " + obtenerMarcador(puntosJugador1) + " "
                            + jugador2 + " " + obtenerMarcador(puntosJugador2)));

                }
            }

            // Se verifica el resultado del set
            if (juegosJugador1 > juegosJugador2) {
                setsJugador1++;
            } else {
                setsJugador2++;
            }
            //En estas variables se van armando los formatos para el resultado parcial y final del partido
            resultadoJ1 = resultadoJ1 + "|" + Integer.toString(juegosJugador1) + "|";
            resultadoJ2 = resultadoJ2 + "|" + Integer.toString(juegosJugador2) + "|";
            //Se informa el ganador del set
            resultado.add(crearMapaPunto("Fin del set. Ganador: " + (juegosJugador1 > juegosJugador2 ? jugador1 : jugador2)));
            resultado.add(crearMapaSet(resultadoJ1, resultadoJ2));

            // Reiniciar juegos y puntos para el próximo set
            juegosJugador1 = 0;
            juegosJugador2 = 0;
            puntosJugador1 = 0;
            puntosJugador2 = 0;
        }
        //Se informa el ganador del partido
        resultado.add(crearMapaPunto("Fín del Partido. Ganador: " + (setsJugador1 > setsJugador2 ? jugador1 : jugador2)));
        resultado.add(crearMapaPunto("Desea ver una revancha?"));
        return resultado;
    }

    //Crea un mapa con el identificador de mensaje y se utiliza para informar
    // todo lo que va sucediendo durante el partido
    public Map<String, String> crearMapaPunto(String mensaje) {
        Map<String, String> puntoMap = new HashMap<>();
        puntoMap.put("mensaje", mensaje);
        return puntoMap;
    }

    //Crea un mapa con los resultados en los diferentes sets de ambos jugadores y lo devuelve en el formato utilizado en el deporte
    private Map<String, String> crearMapaSet(String resultadoj1, String resultadoj2) {
        Map<String, String> puntoMap = new HashMap<>();
        puntoMap.put("jugador1", resultadoj1);
        puntoMap.put("jugador2", resultadoj2);
        return puntoMap;
    }

    //Determina cuando termina el set para poder reiniciar los valores y comenzar uno nuevo
    public boolean finDelSet(int juegosJugador1, int juegosJugador2) {
        return ((juegosJugador1 >= 6 || juegosJugador2 >= 6) && Math.abs(juegosJugador1 - juegosJugador2) >= 2)||((juegosJugador1 >= 7 || juegosJugador2 >= 7) && Math.abs(juegosJugador1 - juegosJugador2) >= 1);
    }

    //Simula el punto en base a las probabilidades ingresadas
    private String simularPunto(int probabilidadJugador1, int probabilidadJugador2, int valorAleatorio) {
        // Simulación probabilística del punto
        return valorAleatorio < probabilidadJugador1 ? "Jugador1" : "Jugador2";
    }

    //Convierte el valor de los contadores de puntos en los valores utilizados en el tenis
    public String obtenerMarcador(int puntos) {
        switch (puntos) {
            case 1:
                return "15";
            case 2:
                return "30";
            case 3:
                return "40";
            case 4:
                return "Ventaja";
            case 5:
                return "Ganador";
            default:
                return "0";
        }
    }
}
