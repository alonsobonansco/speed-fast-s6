package cl.duoc.speedfast.controller;

import cl.duoc.speedfast.model.Pedido;
import cl.duoc.speedfast.model.PedidoComida;
import cl.duoc.speedfast.view.VentanaRegistroPedido;

import java.util.List;

public class ControladorRegistroPedido {

    private final VentanaRegistroPedido ventanaRegistroPedido;
    private final List<Pedido> listaPedidos;

    public ControladorRegistroPedido(VentanaRegistroPedido ventanaRegistroPedido, List<Pedido> listaPedidos) {
        this.ventanaRegistroPedido = ventanaRegistroPedido;
        this.listaPedidos = listaPedidos;

        inicializarListeners();
    }

    private void inicializarListeners() {
        ventanaRegistroPedido.addCancelarListener(e -> ventanaRegistroPedido.cerrarVentana());
        ventanaRegistroPedido.addGuardarListener(e -> procesarGuardado());
    }

    private void procesarGuardado() {
        try {
            String idPedido = ventanaRegistroPedido.getIdPedido();
            String direccionEntrega = ventanaRegistroPedido.getDireccionEntrega();
            String tipoPedido = ventanaRegistroPedido.getTipoPedido();

            if (idPedido.isBlank() || direccionEntrega.isBlank()) {
                ventanaRegistroPedido.mostrarMensajeError("Todos los campos son obligatorios.");
                return;
            }

            Pedido nuevoPedido = new PedidoComida(
                    idPedido,
                    direccionEntrega
            );

            listaPedidos.add(nuevoPedido);

            ventanaRegistroPedido.mostrarMensajeConfirmacion("Pedido registrado exitosamente.");
            ventanaRegistroPedido.limpiarFormulario();

        } catch (IllegalArgumentException e) {
            ventanaRegistroPedido.mostrarMensajeError(e.getMessage());
        }
    }
}
