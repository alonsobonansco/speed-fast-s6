package cl.duoc.speedfast.model;

/**
 * Clase genérica de un pedido y superclase de subtipos de pedidos.
 */
public abstract class Pedido implements Cancelable {

    private final TipoPedido tipoPedido;
    private final int idPedido;
    private String direccionEntrega;
    private boolean pedidoActivo = true;
    private EstadoPedido estadoPedido = EstadoPedido.PENDIENTE;

    /**
     * Constructor que inicializa el estado base e inmutable de un pedido para las subclases.
     *
     * @param tipoPedido       El tipo de pedido.
     * @param idPedido         El ID del pedido.
     * @param direccionEntrega La dirección de entrega del pedido.
     * @throws IllegalArgumentException Si el idPedido es menor o igual a cero,
     *                                  o si la direccionEntrega es nula o vacía.
     */
    public Pedido(TipoPedido tipoPedido, int idPedido, String direccionEntrega) {
        if (idPedido <= 0) {
            throw new IllegalArgumentException("El ID del pedido debe ser válido.");
        }

        this.tipoPedido = tipoPedido;
        this.idPedido = idPedido;
        setDireccionEntrega(direccionEntrega);
    }

    /**
     * Cancela el pedido actual modificando su estado interno a falso.
     * Cuenta con un escudo defensivo que bloquea solicitudes de anulación duplicadas.
     */
    @Override
    public void cancelar() {
        if (!pedidoActivo) {
            return;
        }

        pedidoActivo = false;
        estadoPedido = EstadoPedido.CANCELADO;
    }

    /**
     * Evalúa si las condiciones operativas de la subclase permiten el envío.
     * Cada tipo de pedido implementa sus propias reglas de negocio.
     *
     * @return true si el pedido pasa los controles; false si es rechazado.
     */
    public abstract boolean validarPedido();

    public TipoPedido getTipoPedido() {
        return tipoPedido;
    }

    public int getIdPedido() {
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

    /**
     * Obtiene un detalle específico del pedido, que varía según el tipo de pedido.
     *
     * @return Un string que representa el detalle específico del pedido.
     */
    public abstract String getDetalleEspecifico();
}
