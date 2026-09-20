package cl.duoc.speedfast.service;

import cl.duoc.speedfast.controller.ControladorPedidos;
import cl.duoc.speedfast.model.Pedido;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import static cl.duoc.speedfast.model.EstadoPedido.ENTREGADO;

/**
 * Hilo de ejecución independiente (Worker Thread) del sistema logístico.
 * Consume de forma destructiva y asíncrona la cola de despachos concurrentes,
 * simulando las fases de transporte en ruta mediante pausas dinámicas.
 */
public class Repartidor implements Runnable {

    private final String nombreRepartidor;
    private final ControladorPedidos controladorPedidos;

    /**
     * Instancia un trabajador acoplando la validación defensiva de sus recursos.
     *
     * @param nombreRepartidor   Nombre del trabajador.
     * @param controladorPedidos Central de andenes y exclusión mutua de logs.
     */
    public Repartidor(String nombreRepartidor, ControladorPedidos controladorPedidos) {
        if (nombreRepartidor == null || nombreRepartidor.isEmpty()) {
            throw new IllegalArgumentException("El nombre del repartidor no puede ser nulo o vacío");
        }
        if (controladorPedidos == null) {
            throw new IllegalArgumentException("El controlador de pedidos no puede ser nulo");
        }
        this.nombreRepartidor = nombreRepartidor;
        this.controladorPedidos = controladorPedidos;
    }

    /**
     * Ciclo de vida activo del hilo secundario.
     * Remueve pedidos de la cola de forma segura, procesa las fases físicas mediante
     * pausas escalonadas y maneja interrupciones abruptas de la CPU de forma pacífica.
     */
    @Override
    public void run() {
        while (true) {
            Pedido pedido = controladorPedidos.retirarPedido();

            if (pedido == null) {
                break;
            }

            try {
                TimeUnit.MILLISECONDS.sleep(calcularTiempoAleatorio(1000, 1000));
                controladorPedidos.escribirMensaje("[CARGA] Repartidor [" + nombreRepartidor + "] retirando pedido #" + pedido.getIdPedido());

                TimeUnit.MILLISECONDS.sleep(calcularTiempoAleatorio(1500, 1500));

                controladorPedidos.escribirMensaje("[RUTA] Pedido #" + pedido.getIdPedido() + " se encuentra en reparto");

                TimeUnit.MILLISECONDS.sleep(calcularTiempoAleatorio(1000, 1000));

                pedido.setEstadoPedido(ENTREGADO);

                controladorPedidos.escribirMensaje("[ENTREGA] Pedido #" + pedido.getIdPedido() + " ha sido entregado por [" + nombreRepartidor + "]");

            } catch (InterruptedException e) {
                controladorPedidos.escribirMensaje("Entrega interrumpida");

                Thread.currentThread().interrupt();
                break;
            }
        }

        controladorPedidos.finalizarSimulacion();
    }

    private int calcularTiempoAleatorio(int baseMilisegundos, int rangoAleatorio) {
        return baseMilisegundos + ThreadLocalRandom.current().nextInt(rangoAleatorio);
    }
}
