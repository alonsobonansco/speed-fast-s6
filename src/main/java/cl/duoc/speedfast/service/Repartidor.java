package cl.duoc.speedfast.service;

import cl.duoc.speedfast.controller.ControladorPedidos;

public class Repartidor implements Runnable {

    private final String nombreRepartidor;
    private final ControladorPedidos controladorPedidos;

    public Repartidor(String nombreRepartidor, ControladorPedidos controladorPedidos) {
        this.nombreRepartidor = nombreRepartidor;
        this.controladorPedidos = controladorPedidos;
    }

    public String getNombreRepartidor() {
        return nombreRepartidor;
    }

    @Override
    public void run() {
        // Lógica para el hilo del repartidor
    }
}
