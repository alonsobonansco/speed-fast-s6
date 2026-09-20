package cl.duoc.speedfast.model;

public class PedidoComida extends Pedido {

    private boolean comidaEnBuenEstado = true;

    public PedidoComida(String idPedido, String direccionEntrega, boolean comidaEnBuenEstado) {
        super("COMIDA", idPedido, direccionEntrega);

    }

    @Override
    public boolean validarPedido() {
        System.out.println("Verificando que la comida esté en buen estado...");

        if (!comidaEnBuenEstado) {
            System.out.println("[ERROR] Comida en mal estado.\n");
            this.cancelar();
            return false;
        }

        System.out.println("[OK] Comida en buen estado.\n");
        return true;
    }

    @Override
    public String getDetalleEspecifico() {
        return "Comida en buen estado";
    }
}
