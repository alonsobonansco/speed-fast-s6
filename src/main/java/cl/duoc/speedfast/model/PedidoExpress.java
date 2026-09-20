package cl.duoc.speedfast.model;

public class PedidoExpress extends Pedido {

    private static final double LIMITE_DISTANCIA_KM = 20.0;
    private final double distanciaKm;

    public PedidoExpress(String idPedido, String direccionEntrega, double distanciaKm) {
        super("EXPRESS", idPedido, direccionEntrega);
        this.distanciaKm = distanciaKm;
    }

    public double getDistanciaKm() {
        return distanciaKm;
    }

    @Override
    public boolean validarPedido() {
        System.out.println("Verificando que la distancia esté dentro del límite permitido...");

        if (getDistanciaKm() > LIMITE_DISTANCIA_KM) {
            System.out.println("[ERROR] No es posible realizar un envío express por la distancia.\n");
            //this.cancelar();
            return false;
        }

        System.out.println("[OK] Distancia dentro del límite.\n");
        return true;
    }

    @Override
    public String getDetalleEspecifico() {
        return "Distancia dentro del límite permitido";
    }
}
