package cl.duoc.speedfast.model;

/**
 * Subclase de Pedido. Sus atributos son CAPACIDAD_MAXIMA_KG y pesoEncomienda: el primero es
 * el valor máximo permitido para el transporte y el personal; el segundo, el peso real
 * de la encomienda.
 */
public class PedidoEncomienda extends Pedido {

    private static final double CAPACIDAD_MAXIMA_KG = 40.0;
    private final double pesoEncomienda;

    /**
     * Construye un pedido de encomienda.
     *
     * @param idPedido         El ID del pedido.
     * @param direccionEntrega La dirección de entrega del pedido.
     * @param pesoEncomienda   El peso de la encomienda.
     * @throws IllegalArgumentException Si el peso de la encomienda es menor o igual a cero.
     */
    public PedidoEncomienda(int idPedido, String direccionEntrega, double pesoEncomienda) {
        super(TipoPedido.ENCOMIENDA, idPedido, direccionEntrega);

        if (pesoEncomienda <= 0) {
            throw new IllegalArgumentException("El peso de la encomienda debe ser válido.");
        }
        this.pesoEncomienda = pesoEncomienda;
    }

    public static double getCapacidadMaximaKg() {
        return CAPACIDAD_MAXIMA_KG;
    }

    @Override
    public boolean validarPedido() {
        if (pesoEncomienda > CAPACIDAD_MAXIMA_KG) {
            this.cancelar();
            return false;
        }

        return true;
    }

    @Override
    public String getDetalleEspecifico() {
        return pesoEncomienda + " kg";
    }
}
