package cl.duoc.speedfast.model;

public class PedidoComida extends Pedido {

    private boolean mochilaEnBuenEstado;

    public PedidoComida(String idPedido, String direccionEntrega) {
        super("PEDIDO COMIDA", idPedido, direccionEntrega);

    }

    @Override
    public boolean validarPedido() {
        System.out.println("Verificando que la mochila térmica esté en buen estado...");

        if (!mochilaEnBuenEstado) {
            System.out.println("[ERROR] Mochila térmica en mal estado.\n");
            //this.cancelar();
            return false;
        }

        System.out.println("[OK] Mochila térmica en buen estado.\n");
        return true;
    }
}
