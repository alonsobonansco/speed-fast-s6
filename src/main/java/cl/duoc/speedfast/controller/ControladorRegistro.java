package cl.duoc.speedfast.controller;

import cl.duoc.speedfast.model.Pedido;
import cl.duoc.speedfast.model.PedidoComida;
import cl.duoc.speedfast.model.PedidoEncomienda;
import cl.duoc.speedfast.model.PedidoExpress;
import cl.duoc.speedfast.view.VentanaRegistroPedido;

import java.util.List;

public class ControladorRegistro {

    private final VentanaRegistroPedido ventanaRegistroPedido;
    private final List<Pedido> listaPedidos;

    public ControladorRegistro(VentanaRegistroPedido ventanaRegistroPedido, List<Pedido> listaPedidos) {
        this.ventanaRegistroPedido = ventanaRegistroPedido;
        this.listaPedidos = listaPedidos;

        inicializarListeners();
    }

    private void inicializarListeners() {
        ventanaRegistroPedido.addVolverAtrasListener(e -> ventanaRegistroPedido.cerrarVentana());
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

            Pedido nuevoPedido = switch (tipoPedido.toUpperCase()) {
                case "COMIDA" -> new PedidoComida(idPedido, direccionEntrega);
                case "EXPRESS" -> new PedidoExpress(idPedido, direccionEntrega);
                case "ENCOMIENDA" -> new PedidoEncomienda(idPedido, direccionEntrega);
                default -> throw new IllegalArgumentException("Tipo de pedido no válido.");
            };

            listaPedidos.add(nuevoPedido);

            ventanaRegistroPedido.mostrarMensajeConfirmacion("Pedido registrado exitosamente.");
            ventanaRegistroPedido.limpiarFormulario();

        } catch (IllegalArgumentException e) {
            ventanaRegistroPedido.mostrarMensajeError(e.getMessage());
        }
    }
}
