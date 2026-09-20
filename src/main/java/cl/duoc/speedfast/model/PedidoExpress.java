package cl.duoc.speedfast.model;

public class PedidoExpress extends Pedido {

    private static final double LIMITE_DISTANCIA_KM = 20.0;
    private final double distanciaKm;

    public PedidoExpress(String idPedido, String direccionEntrega, double distanciaKm) {
        super(TipoPedido.EXPRESS, idPedido, direccionEntrega);
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
