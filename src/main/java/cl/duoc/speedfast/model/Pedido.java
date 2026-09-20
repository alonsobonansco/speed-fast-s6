package cl.duoc.speedfast.model;

public abstract class Pedido implements Cancelable {

    private TipoPedido tipoPedido;
    private final String idPedido;
    private String direccionEntrega;
    private boolean pedidoActivo = true;
    private EstadoPedido estadoPedido = EstadoPedido.PENDIENTE;

    public Pedido(TipoPedido tipoPedido, String idPedido, String direccionEntrega) {
        if (idPedido == null || idPedido.isBlank()) {
            throw new IllegalArgumentException("El ID del pedido no puede estar vacío.");
        }

        this.tipoPedido = tipoPedido;
        this.idPedido = idPedido;
        setDireccionEntrega(direccionEntrega);
    }

    @Override
    public void cancelar() {
        if (!pedidoActivo) {
            return;
        }

        pedidoActivo = false;
        estadoPedido = EstadoPedido.CANCELADO;
    }

    public abstract boolean validarPedido();

    public TipoPedido getTipoPedido() {
        return tipoPedido;
    }

    public String getIdPedido() {
        return idPedido;
    }

    public String getDireccionEntrega() {
        return direccionEntrega;
    }

    public void setDireccionEntrega(String direccionEntrega) {
        if (direccionEntrega == null || direccionEntrega.isBlank()) {
            throw new IllegalArgumentException("La dirección de entrega debe ser válida.");
        }
        this.direccionEntrega = direccionEntrega;
    }

    public EstadoPedido getEstadoPedido() {
        return estadoPedido;
    }

    public void setEstadoPedido(EstadoPedido estadoPedido) {
        this.estadoPedido = estadoPedido;
    }

    public abstract String getDetalleEspecifico();
}
