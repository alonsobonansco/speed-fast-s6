package cl.duoc.speedfast.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class VentanaPrincipal extends JFrame {

    private JButton registrarPedidoButton;
    private JButton listarPedidosButton;
    private JButton asignarRepartidorButton;
    private JLabel tituloLabel;

    public VentanaPrincipal() {
        setTitle("SpeedFast - Gestión de Entregas");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setResizable(false);

        inicializarComponentes();
        construirLayout();
    }

    private void inicializarComponentes() {
        tituloLabel = new JLabel("Panel de Control SpeedFast", SwingConstants.CENTER);
        tituloLabel.setFont(new Font("Arial", Font.BOLD, 28));
        tituloLabel.setBorder(BorderFactory.createEmptyBorder(15, 10, 15, 10));

        registrarPedidoButton = new JButton("Registrar Pedido");
        listarPedidosButton = new JButton("Listar Pedidos");
        asignarRepartidorButton = new JButton("Asignar Repartidor");

        Dimension botonDimension = new Dimension(200, 40);
        registrarPedidoButton.setPreferredSize(botonDimension);
        listarPedidosButton.setPreferredSize(botonDimension);
        asignarRepartidorButton.setPreferredSize(botonDimension);
    }


    private void construirLayout() {
        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;

        gbc.gridy = 0;
        panelBotones.add(registrarPedidoButton, gbc);

        gbc.gridy = 1;
        panelBotones.add(listarPedidosButton, gbc);

        gbc.gridy = 2;
        panelBotones.add(asignarRepartidorButton, gbc);

        add(panelBotones, BorderLayout.CENTER);
    }

    public void addRegistrarPedidoListener(ActionListener listener) {
        registrarPedidoButton.addActionListener(listener);
    }

    public void addListarPedidosListener(ActionListener listener) {
        listarPedidosButton.addActionListener(listener);
    }

    public void addAsignarRepartidorListener(ActionListener listener) {
        asignarRepartidorButton.addActionListener(listener);
    }




    /* hereda de JFrame
      con botones para
     - registrar pedido
     - listar pedidos
     - asignar repartidor/iniciar entrega */

    // organizar los componentes con borderlayout, gridlayout u otro adecuado

    /*Desde VentanaPrincipal, botones que abren VentanaRegistroPedido y
    VentanaListaPedidos*/


}
