package cl.duoc.speedfast.model;

public abstract class Pedido {

    private final String tipoPedido;
    private final String idPedido;
    private String direccionEntrega;
    private boolean pedidoActivo = true;
    private String estadoPedido = "PENDIENTE";

    public Pedido(String tipoPedido, String idPedido, String direccionEntrega) {
        if (idPedido == null || idPedido.isBlank()) {
            throw new IllegalArgumentException("El ID del pedido no puede estar vacío.");
        }

        this.tipoPedido = tipoPedido;
        this.idPedido = idPedido;
        setDireccionEntrega(direccionEntrega);
    }

    /*@Override
    public void despachar() {
        if (!pedidoActivo) {
            System.out.println("No se puede despachar un pedido cancelado.\n");
            return;
        }

        System.out.println("→ " + getTipoPedido() + " #" + getIdPedido() + " listo para reparto.");
    }

    @Override
    public void cancelar() {
        if (!pedidoActivo) {
            System.out.println("- El pedido #" + idPedido + " ya se encuentra cancelado.\n");
            return;
        }

        pedidoActivo = false;
    }*/

    public abstract boolean validarPedido();

    public void mostrarResumen() {
        String textoResumen = """
                \n===================
                %s #%s
                ===================
                
                Dirección: %s
                Distancia: %.1f km
                Tiempo estimado de entrega: %d minutos
                """.formatted(
                getTipoPedido(), getIdPedido(),
                getDireccionEntrega()
        );

        System.out.println(textoResumen);
    }

    public String getTipoPedido() {
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

    public boolean isPedidoActivo() {
        return pedidoActivo;
    }

    public String getEstadoPedido() {
        return estadoPedido;
    }

    public void setEstadoPedido(String estadoPedido) {
        this.estadoPedido = estadoPedido;
    }
}
