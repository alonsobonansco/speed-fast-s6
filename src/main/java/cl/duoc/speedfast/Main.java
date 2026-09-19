package cl.duoc.speedfast;

import cl.duoc.speedfast.controller.ControladorPrincipal;
import cl.duoc.speedfast.view.VentanaPrincipal;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            VentanaPrincipal ventanaPrincipal = new VentanaPrincipal();
            new ControladorPrincipal(ventanaPrincipal);
            ventanaPrincipal.setVisible(true);
        });

       /* Paso 4: Integra la navegación entre ventanas
             Desde VentanaPrincipal, botones que abren VentanaRegistroPedido y
            VentanaListaPedidos
             Comparte datos mediante un controlador o lista común*/
    }
}
