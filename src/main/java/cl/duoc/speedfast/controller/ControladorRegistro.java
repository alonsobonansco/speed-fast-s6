package cl.duoc.speedfast.controller;

import cl.duoc.speedfast.model.Pedido;
import cl.duoc.speedfast.model.PedidoComida;
import cl.duoc.speedfast.model.PedidoEncomienda;
import cl.duoc.speedfast.model.PedidoExpress;
import cl.duoc.speedfast.view.VentanaRegistroPedido;

import java.util.List;

/**
 * Gestor del ciclo de vida y validación del formulario de ingresos.
 * Procesa la captura de datos en la interfaz dinámica, aplica el escudo
 * defensivo contra formatos inválidos y restringe duplicados en el historial.
 */
public class ControladorRegistro {

    private final VentanaRegistroPedido ventanaRegistroPedido;
    private final List<Pedido> listaPedidos;

    /**
     * Enlaza la interfaz gráfica de registro con la lista central de memoria.
     *
     * @param ventanaRegistroPedido Instancia del formulario dinámico de ingreso.
     * @param listaPedidos          Colección histórica de persistencia compartida.
     */
    public ControladorRegistro(VentanaRegistroPedido ventanaRegistroPedido, List<Pedido> listaPedidos) {
        this.ventanaRegistroPedido = ventanaRegistroPedido;
        this.listaPedidos = listaPedidos;

        inicializarListeners();
    }

    private void inicializarListeners() {
        ventanaRegistroPedido.addVolverAtrasListener(e -> ventanaRegistroPedido.cerrarVentana());
        ventanaRegistroPedido.addGuardarListener(e -> procesarGuardado());
    }

    /**
     * Extrae, valida y procesa los datos del formulario gráfico.
     * Aplica un flujo defensivo multifase (campos vacíos, IDs negativos, duplicados
     * y errores de casteo numérico) antes de instanciar la subclase polimórfica.
     */
    private void procesarGuardado() {
        try {
            String idPedidoStr = ventanaRegistroPedido.getIdPedido();
            String direccionEntrega = ventanaRegistroPedido.getDireccionEntrega();
            String tipoPedido = ventanaRegistroPedido.getTipoPedido();

            if (idPedidoStr.isBlank() || direccionEntrega.isBlank()) {
                ventanaRegistroPedido.mostrarMensajeError("Todos los campos son obligatorios.");
                return;
            }

            int idPedido = Integer.parseInt(idPedidoStr);

            if (idPedido <= 0) {
                throw new IllegalArgumentException("El ID del pedido debe ser un número positivo.");
            }

            if (existePedido(idPedido)) {
                ventanaRegistroPedido.mostrarMensajeError("El ID del pedido ya existe. Por favor, ingrese un ID único.");
                return;
            }

            Pedido nuevoPedido = switch (tipoPedido.toUpperCase()) {
                case "COMIDA" -> {
                    boolean comidaOK = ventanaRegistroPedido.getInputDinamicoCheck();
                    yield new PedidoComida(idPedido, direccionEntrega, comidaOK);
                }
                case "EXPRESS" -> {
                    String distanciaStr = ventanaRegistroPedido.getInputDinamicoTexto();
                    if (distanciaStr.isBlank()) {
                        throw new IllegalArgumentException("La distancia del pedido no puede estar vacía.");
                    }
                    double distancia = Double.parseDouble(distanciaStr);
                    yield new PedidoExpress(idPedido, direccionEntrega, distancia);
                }
                case "ENCOMIENDA" -> {
                    String pesoPedidoStr = ventanaRegistroPedido.getInputDinamicoTexto();
                    if (pesoPedidoStr.isBlank()) {
                        throw new IllegalArgumentException("El peso del pedido no puede estar vacío.");
                    }
                    double pesoPedido = Double.parseDouble(pesoPedidoStr);
                    yield new PedidoEncomienda(idPedido, direccionEntrega, pesoPedido);
                }
                default -> throw new IllegalArgumentException("Tipo de pedido no válido.");
            };

            listaPedidos.add(nuevoPedido);

            ventanaRegistroPedido.mostrarMensajeConfirmacion("Pedido registrado exitosamente.");
            ventanaRegistroPedido.limpiarFormulario();

        } catch (NumberFormatException e) {
            ventanaRegistroPedido.mostrarMensajeError("Debe ingresar solo números en los campos que correspondan.");
        } catch (IllegalArgumentException e) {
            ventanaRegistroPedido.mostrarMensajeError(e.getMessage());
        }
    }

    private boolean existePedido(int idBuscado) {
        for (Pedido pedido : listaPedidos) {
            if (pedido.getIdPedido() == idBuscado) {
                return true;
            }
        }
        return false;
    }
}
