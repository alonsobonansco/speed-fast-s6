package cl.duoc.speedfast.service;

import cl.duoc.speedfast.controller.ControladorPedidos;
import cl.duoc.speedfast.model.Pedido;

import java.util.concurrent.ThreadLocalRandom;

import static cl.duoc.speedfast.model.EstadoPedido.ENTREGADO;

public class Repartidor implements Runnable {

    private final String nombreRepartidor;
    private final ControladorPedidos controladorPedidos;

    public Repartidor(String nombreRepartidor, ControladorPedidos controladorPedidos) {
        this.nombreRepartidor = nombreRepartidor;
        this.controladorPedidos = controladorPedidos;
    }

    @Override
    public void run() {
        while (true) {
            Pedido pedido = controladorPedidos.retirarPedido();

            if (pedido == null) {
                break;
            }

            try {
                // FASE 1: Carga y preparación
                Thread.sleep(calcularTiempoAleatorio(1000, 1000));
                controladorPedidos.escribirMensaje("📦 [CARGA] Repartidor [" + nombreRepartidor + "] retirando pedido #" + pedido.getIdPedido());

                // FASE 2: Transporte en ruta
                Thread.sleep(calcularTiempoAleatorio(1500, 1500));

                controladorPedidos.escribirMensaje("🚚 [RUTA] Pedido #" + pedido.getIdPedido() + " se encuentra EN REPARTO por [" + nombreRepartidor + "]");

                // FASE 3: Entrega final exitosa
                Thread.sleep(calcularTiempoAleatorio(1000, 1000));

                // 🚀 NUEVO: El pedido pasa a estar entregado
                pedido.setEstadoPedido(ENTREGADO);

                controladorPedidos.escribirMensaje("✅ [ÉXITO] ¡Pedido #" + pedido.getIdPedido() + " ha sido ENTREGADO por [" + nombreRepartidor + "]!");

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    private int calcularTiempoAleatorio(int baseMilisegundos, int rangoAleatorio) {
        return baseMilisegundos + ThreadLocalRandom.current().nextInt(rangoAleatorio);
    }
}
