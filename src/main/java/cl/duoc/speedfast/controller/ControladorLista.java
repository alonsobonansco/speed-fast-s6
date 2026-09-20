package cl.duoc.speedfast.controller;

import cl.duoc.speedfast.model.Pedido;
import cl.duoc.speedfast.view.VentanaListaPedidos;

import java.util.List;

/**
 * Gestor y puente de datos para el despliegue del historial de envíos.
 * Sincroniza la colección central de memoria RAM con la interfaz gráfica tabular,
 * gatillando el refresco polimórfico de la grilla en cada inicialización.
 */
public class ControladorLista {

    private final VentanaListaPedidos ventanaListaPedidos;
    private final List<Pedido> listaPedidos;

    /**
     * Enlaza la vista del listado histórico con el almacén central de datos puros.
     *
     * @param ventanaListaPedidos Instancia de la interfaz gráfica tabular.
     * @param listaPedidos        Colección histórica de persistencia compartida.
     */
    public ControladorLista(VentanaListaPedidos ventanaListaPedidos, List<Pedido> listaPedidos) {
        this.ventanaListaPedidos = ventanaListaPedidos;
        this.listaPedidos = listaPedidos;

        inicializarListeners();
        cargarDatosEnTabla();
    }

    private void inicializarListeners() {
        ventanaListaPedidos.addVolverAtrasListener(e -> ventanaListaPedidos.cerrarVentana());
    }

    private void cargarDatosEnTabla() {
        this.ventanaListaPedidos.actualizarTabla(this.listaPedidos);
    }
}
