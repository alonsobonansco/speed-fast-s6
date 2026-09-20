package cl.duoc.speedfast.model;

/**
 * Define el contrato de comportamiento para permitir una cancelación
 * definitiva de su flujo operativo.
 */
public interface Cancelable {
    /**
     * Ejecuta el proceso de cancelación del componente.
     * Las clases que implementen este método deben encargarse de modificar
     * su estado interno de forma segura y notificar la baja al sistema.
     */
    void cancelar();
}
