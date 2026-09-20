package cl.duoc.speedfast.model;

public class PedidoComida extends Pedido {

    private boolean comidaEnBuenEstado;

    public PedidoComida(String idPedido, String direccionEntrega, boolean comidaEnBuenEstado) {
        super("COMIDA", idPedido, direccionEntrega);
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
