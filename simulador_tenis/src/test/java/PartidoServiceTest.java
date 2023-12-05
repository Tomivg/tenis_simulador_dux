import com.dux.simulador_tenis.service.PartidoService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;


public class PartidoServiceTest {

    @Test
    void test1() {
        PartidoService servicio = new PartidoService();
        Boolean terminoSet;
        terminoSet = servicio.finDelSet(4,6);
        Assertions.assertTrue(terminoSet);
        Boolean noTerminoSet;
        noTerminoSet = servicio.finDelSet(4,4);
        Assertions.assertFalse(noTerminoSet);

    }

    @Test
    void test2() {
        PartidoService servicio = new PartidoService();
        String esperado = "40";
        String recibido = servicio.obtenerMarcador(3);
        Assertions.assertEquals(recibido, esperado);
        String esperado2 = "00";
        String recibido2 = servicio.obtenerMarcador(0);
        Assertions.assertEquals(recibido, esperado);
    }

    @Test
    void test3() {
        PartidoService servicio = new PartidoService();
        Map<String, String> esperado = new HashMap<>();
        esperado.put("mensaje", "Punto para Nadal");
        Map<String, String> recibido = servicio.crearMapaPunto("Punto para Nadal");
        Assertions.assertEquals(recibido, esperado);
    }


}
