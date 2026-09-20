package cl.duoc.speedfast.controller;

import cl.duoc.speedfast.model.EstadoPedido;
import cl.duoc.speedfast.model.Pedido;
import cl.duoc.speedfast.model.PedidoEncomienda;
import cl.duoc.speedfast.model.PedidoExpress;
import cl.duoc.speedfast.event.LogListener;
import cl.duoc.speedfast.service.Repartidor;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Orquestador y central del motor logístico concurrente.
 * Administra el andén de despacho seguro y coordina el ciclo de vida asíncrono
 * de los hilos de los trabajadores mediante el patrón Productor-Consumidor.
 */
public class ControladorPedidos {

    private final BlockingQueue<Pedido> pedidosPendientes = new LinkedBlockingQueue<>();
    private LogListener logListener;
    private int repartidoresActivos = 0;

    public void setLogListener(LogListener logListener) {
        this.logListener = logListener;
    }

    /**
     * Filtra los pedidos pendientes del historial permanente mediante control de calidad.
     * Los paquetes aprobados se inyectan en la cola concurrente y se encienden
     * los hilos de los trabajadores en segundo plano si existen tareas aptas.
     *
     * @param listaPedidos Colección histórica de persistencia temporal en memoria.
     */
    public void iniciarSimulacionReparto(List<Pedido> listaPedidos) {
        if (listaPedidos == null || listaPedidos.isEmpty()) {
            escribirMensaje("[AVISO] No hay pedidos registrados en el sistema para despachar.");
            return;
        }

        pedidosPendientes.clear();

        for (Pedido pedido : listaPedidos) {
            if (pedido.getEstadoPedido() == EstadoPedido.PENDIENTE) {

                if (pedido.validarPedido()) {
                    agregarPedido(pedido);
                } else {
                    String motivo = switch (pedido.getTipoPedido()) {
                        case COMIDA -> "Comida en mal estado.";
                        case ENCOMIENDA -> "El peso excede el límite máximo de " +
                                PedidoEncomienda.getCapacidadMaximaKg() + " kg.";
                        case EXPRESS -> "La distancia excede el límite máximo de " +
                                PedidoExpress.getDistanciaMaximaKm() + " km.";
                        default -> "Tipo de pedido desconocido.";
                    };

                    escribirMensaje("[RECHAZADO] Pedido #" + pedido.getIdPedido() + ". Motivo: " + motivo);
                }
            }
        }

        if (pedidosPendientes.isEmpty()) {
            escribirMensaje("[AVISO] No quedan pedidos pendientes por entregar.");
            return;
        }

        escribirMensaje("\n --- INICIANDO REPARTO CONCURRENTE --- ");

        String[] nombresRepartidores = {"Juan", "María", "Carlos"};
        this.repartidoresActivos = nombresRepartidores.length;

        for (String nombre : nombresRepartidores) {
            Thread hiloRepartidor = new Thread(new Repartidor(nombre, this));
            hiloRepartidor.start();
        }
    }

    private void agregarPedido(Pedido pedido) {
        pedidosPendientes.add(Objects.requireNonNull(
                pedido, "El pedido no puede ser nulo."
        ));
    }

    /**
     * Extrae de forma destructiva y atómica el siguiente paquete de la cola.
     * Es invocado concurrentemente por múltiples hilos de reparto sin riesgo de doble retiro.
     *
     * @return El {@link Pedido} retirado, o {@code null} si la lista está vacía.
     */
    public Pedido retirarPedido() {
        return pedidosPendientes.poll();
    }

    /**
     * Decrementa el contador de repartidores activos mediante exclusión mutua.
     * El último hilo vivo en apagar su motor tiene la responsabilidad de
     * estampar el aviso único de fin de jornada en la bitácora.
     */
    public synchronized void finalizarSimulacion() {
        this.repartidoresActivos--;
        if (repartidoresActivos == 0) {
            escribirMensaje("\n[AVISO] Todos los pedidos han sido procesados.");
        }
    }

    /**
     * Canaliza los mensajes asíncronos hacia la interfaz gráfica de forma segura.
     * Protege el orden cronológico evitando colisiones o condiciones de carrera de hilos.
     *
     * @param mensaje Cadena de caracteres con el registro lógico a desplegar.
     */
    public synchronized void escribirMensaje(String mensaje) {
        if (logListener != null) {
            logListener.registrarMensaje(mensaje);
        }
    }
}
