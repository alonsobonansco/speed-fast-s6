package cl.duoc.speedfast;

import cl.duoc.speedfast.view.VentanaListaPedidos;
import cl.duoc.speedfast.view.VentanaPrincipal;
import cl.duoc.speedfast.view.VentanaRegistroPedido;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {

        /*SwingUtilities.invokeLater(() -> {
            VentanaPrincipal ventanaPrincipal = new VentanaPrincipal();
            ventanaPrincipal.setVisible(true);
        });*/

        SwingUtilities.invokeLater(() -> {
            VentanaRegistroPedido vrp = new VentanaRegistroPedido();
            vrp.setVisible(true);
        });


       /* Paso 4: Integra la navegación entre ventanas
             Desde VentanaPrincipal, botones que abren VentanaRegistroPedido y
            VentanaListaPedidos
             Comparte datos mediante un controlador o lista común*/




        /*La aplicación debe iniciarse desde una clase Main en el paquete main, llamando a
        new VentanaPrincipal().*/
    }
}
