package cl.duoc.speedfast;

import cl.duoc.speedfast.controller.ControladorPrincipal;
import cl.duoc.speedfast.view.VentanaPrincipal;

import javax.swing.*;

/**
 * Punto de entrada único y director de orquesta global de la aplicación.
 * Se encarga de inicializar la infraestructura base del sistema y arrancar
 * el hilo de renderizado gráfico de forma segura.
 */
public class Main {
    /**
     * Gatilla el inicio del software aislando la ejecución.
     * Fuerza el despliegue del árbol visual dentro del Event Dispatch Thread (EDT)
     * para prevenir bloqueos gráficos en el hilo principal del sistema operativo.
     *
     * @param args Arreglo de argumentos de inicialización por línea de comandos (no utilizados).
     */
    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            VentanaPrincipal ventanaPrincipal = new VentanaPrincipal();
            new ControladorPrincipal(ventanaPrincipal);
            ventanaPrincipal.setVisible(true);
        });
    }
}
