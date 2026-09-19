package cl.duoc.speedfast.model;

public class PedidoEncomienda extends Pedido {

    private static final double CAPACIDAD_MAXIMA_KG = 40.0;
    private final double pesoEncomienda;

    public PedidoEncomienda(String idPedido, String direccionEntrega, double pesoEncomienda) {
        super("PEDIDO ENCOMIENDA", idPedido, direccionEntrega);

        if (pesoEncomienda <= 0) {
            throw new IllegalArgumentException("El peso de la encomienda debe ser válido.");
        }
        this.pesoEncomienda = pesoEncomienda;
    }

    @Override
    public boolean validarPedido() {
        System.out.println("Verificando que el peso de la encomienda no exceda el límite...");

        if (pesoEncomienda > CAPACIDAD_MAXIMA_KG) {
            System.out.println("[ERROR] El peso de la encomienda supera el límite permitido.\n");
            //this.cancelar();
            return false;
        }

        System.out.println("[OK] Peso de la encomienda permitido.\n");
        return true;
    }
}
