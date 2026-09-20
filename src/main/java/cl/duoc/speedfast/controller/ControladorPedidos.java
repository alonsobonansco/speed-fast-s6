package cl.duoc.speedfast.controller;

import cl.duoc.speedfast.model.Pedido;
import cl.duoc.speedfast.service.LogListener;
import cl.duoc.speedfast.service.Repartidor;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ControladorPedidos {

    private final BlockingQueue<Pedido> pedidosPendientes = new LinkedBlockingQueue<>();
    private LogListener logListener;

    public void setLogListener(LogListener logListener) {
        this.logListener = logListener;
    }

    public void iniciarSimulacionReparto(List<Pedido> listaPedidos) {
        if (listaPedidos == null || listaPedidos.isEmpty()) {
            escribirMensaje("[AVISO] No hay pedidos registrados en el sistema para despachar.");
            return;
        }

        escribirMensaje("\n --- INICIANDO REPARTO CONCURRENTE --- ");

        pedidosPendientes.clear();

        for (Pedido pedido : listaPedidos) {
            if (pedido.getEstadoPedido().equalsIgnoreCase("PENDIENTE")) {

                if (pedido.validarPedido()) {
                    agregarPedido(pedido);
                } else {
                    escribirMensaje("[ERROR] Pedido #" + pedido.getIdPedido() + " no cumple con los requisitos de validación y será omitido.");
                }
            }
        }

        String[] nombresRepartidores = {"Juan", "María", "Carlos"};
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

    public Pedido retirarPedido() {
        return pedidosPendientes.poll();
    }

    public synchronized void escribirMensaje(String mensaje) {
        if (logListener != null) {
            logListener.onLog(mensaje);
        }
    }

    public boolean estaVacia() {
        return pedidosPendientes.isEmpty();
    }
}
