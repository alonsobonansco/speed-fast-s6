package cl.duoc.speedfast.model;

/**
 * Sus atributos propios son LIMITE_DISTANCIA_KM y distanciaKm: el primero es
 * el valor límite que un repartidor puede estar del objetivo para realizar una entrega express; el segundo,
 * la distancia real hasta la dirección de entrega.
 */
public class PedidoExpress extends Pedido {

    private static final double LIMITE_DISTANCIA_KM = 20.0;
    private final double distanciaKm;

    /**
     * Construye un pedido express.
     *
     * @param idPedido         Identificador único de la orden.
     * @param direccionEntrega Destino físico del despacho.
     * @param distanciaKm      Trayecto en kilómetros.
     * @throws IllegalArgumentException Si la distancia del pedido es menor o igual a cero.
     */
    public PedidoExpress(int idPedido, String direccionEntrega, double distanciaKm) {
        super(TipoPedido.EXPRESS, idPedido, direccionEntrega);

        if (distanciaKm <= 0) {
            throw new IllegalArgumentException("La distancia del pedido debe ser válida.");
        }
        this.distanciaKm = distanciaKm;
    }

    public double getDistanciaKm() {
        return distanciaKm;
    }

    public static double getDistanciaMaximaKm() {
        return LIMITE_DISTANCIA_KM;
    }

    @Override
    public boolean validarPedido() {
        if (getDistanciaKm() > LIMITE_DISTANCIA_KM) {
            this.cancelar();
            return false;
        }

        return true;
    }

    @Override
    public String getDetalleEspecifico() {
        return distanciaKm + " km";
    }
}
