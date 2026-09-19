package cl.duoc.speedfast.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class VentanaPrincipal extends JFrame {

    private JButton registrarPedidoButton;
    private JButton listarPedidosButton;
    private JButton asignarRepartidorButton;
    private JLabel tituloLabel;
    private JTextArea logTextArea;

    public VentanaPrincipal() {
        setTitle("SpeedFast App");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setResizable(false);

        inicializarComponentes();
        construirLayout();
    }

    private void inicializarComponentes() {
        tituloLabel = new JLabel("Panel de Control SpeedFast", SwingConstants.CENTER);
        tituloLabel.setBounds(50, 20, 300, 40);
        tituloLabel.setFont(new Font("Arial", Font.BOLD, 20));
        tituloLabel.setBorder(BorderFactory.createEmptyBorder(15, 10, 15, 10));

        registrarPedidoButton = new JButton("Registrar Pedido");
        listarPedidosButton = new JButton("Listar Pedidos");
        asignarRepartidorButton = new JButton("Asignar Repartidor");

        Dimension botonDimension = new Dimension(200, 40);
        registrarPedidoButton.setPreferredSize(botonDimension);
        listarPedidosButton.setPreferredSize(botonDimension);
        asignarRepartidorButton.setPreferredSize(botonDimension);

        logTextArea = new JTextArea();
        logTextArea.setEditable(false);
        logTextArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
    }


    private void construirLayout() {
        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridy = 0;

        gbc.gridx = 0;
        panelBotones.add(registrarPedidoButton, gbc);

        gbc.gridx = 1;
        panelBotones.add(listarPedidosButton, gbc);

        gbc.gridx = 2;
        panelBotones.add(asignarRepartidorButton, gbc);

        add(panelBotones, BorderLayout.CENTER);
        add(tituloLabel, BorderLayout.NORTH);

        JScrollPane scrollPane = new JScrollPane(logTextArea);
        scrollPane.setPreferredSize(new Dimension(760, 220)); // Altura fija para la sección de texto
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        JPanel panelInferior = new JPanel(new BorderLayout());
        panelInferior.setBorder(BorderFactory.createEmptyBorder(0, 20, 20, 20)); // Margen externo del scroll
        panelInferior.add(scrollPane, BorderLayout.CENTER);

        add(panelInferior, BorderLayout.SOUTH);
    }

    public void addRegistrarPedidoListener(ActionListener listener) {
        registrarPedidoButton.addActionListener(listener);
    }

    public void addListarPedidosListener(ActionListener listener) {
        listarPedidosButton.addActionListener(listener);
    }

    public void addIniciarEntregasListener(ActionListener listener) {
        asignarRepartidorButton.addActionListener(listener);
    }

    public void appendLog(String mensaje) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            logTextArea.append(mensaje + "\n");
            logTextArea.setCaretPosition(logTextArea.getDocument().getLength());
        });
    }
}
