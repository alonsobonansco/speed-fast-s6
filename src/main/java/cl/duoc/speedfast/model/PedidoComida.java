package cl.duoc.speedfast.model;

/**
 * Subclase de Pedido. Su atributo propio es comidaEnBuenEstado para verificar que
 * la comida esté en óptimas condiciones antes de ser entregada.
 */
public class PedidoComida extends Pedido {

    private final boolean comidaEnBuenEstado;

    /**
     * Construye un pedido de comida.
     *
     * @param idPedido           El ID del pedido.
     * @param direccionEntrega   La dirección de entrega del pedido.
     * @param comidaEnBuenEstado Indica si la comida está en buen estado.
     */
    public PedidoComida(int idPedido, String direccionEntrega, boolean comidaEnBuenEstado) {
        super(TipoPedido.COMIDA, idPedido, direccionEntrega);
        this.comidaEnBuenEstado = comidaEnBuenEstado;
    }

    @Override
    public boolean validarPedido() {
        if (!comidaEnBuenEstado) {
            this.cancelar();
            return false;
        }
        return true;
    }

    @Override
    public String getDetalleEspecifico() {
        return comidaEnBuenEstado ? "Buen estado" : "Mal estado";
    }
}
