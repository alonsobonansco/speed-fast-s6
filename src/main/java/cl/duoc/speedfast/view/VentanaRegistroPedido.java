package cl.duoc.speedfast.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class VentanaRegistroPedido extends JFrame {

    private JLabel tituloLabel;
    private JTextField idTextField;
    private JTextField direccionTextField;
    private JComboBox<String> tipoComboBox;

    private JButton guardarButton;
    private JButton atrasButton;

    public VentanaRegistroPedido() {
        setTitle("SpeedFast App");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
        setResizable(false);

        inicializarComponentes();
        construirLayout();
    }

    public void inicializarComponentes() {

        tituloLabel = new JLabel("Formulario de Registros de Pedidos", SwingConstants.CENTER);
        tituloLabel.setFont(new Font("Arial", Font.BOLD, 18));
        tituloLabel.setBorder(BorderFactory.createEmptyBorder(15, 10, 5, 10));

        idTextField = new JTextField(15);
        direccionTextField = new JTextField(15);

        String[] tipos = {"Comida", "Encomienda", "Express"};
        tipoComboBox = new JComboBox<>(tipos);

        guardarButton = new JButton("Guardar");
        atrasButton = new JButton("Atrás");
    }

    public void construirLayout() {
        add(tituloLabel, BorderLayout.NORTH);

        JPanel panelFormulario = new JPanel(new GridLayout(3, 2, 10, 20));
        panelFormulario.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        panelFormulario.add(new JLabel("ID del Pedido:"));
        panelFormulario.add(idTextField);

        panelFormulario.add(new JLabel("Dirección de Entrega:"));
        panelFormulario.add(direccionTextField);

        panelFormulario.add(new JLabel("Tipo de Pedido:"));
        panelFormulario.add(tipoComboBox);

        add(panelFormulario, BorderLayout.CENTER);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
        panelBotones.add(guardarButton);
        panelBotones.add(atrasButton);

        add(panelBotones, BorderLayout.SOUTH);
    }

    public String getIdPedido() {
        return idTextField.getText().trim();
    }

    public String getDireccionEntrega() {
        return direccionTextField.getText().trim();
    }

    public String getTipoPedido() {
        return (String) tipoComboBox.getSelectedItem();
    }

    public void cerrarVentana() {
        this.dispose();
    }

    public void addGuardarListener(ActionListener listener) {
        guardarButton.addActionListener(listener);
    }

    public void addVolverAtrasListener(ActionListener listener) {
        atrasButton.addActionListener(listener);
    }

    public void mostrarMensajeConfirmacion(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Pedido registrado", JOptionPane.INFORMATION_MESSAGE);
    }

    public void mostrarMensajeError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public void limpiarFormulario() {
        idTextField.setText("");
        direccionTextField.setText("");
        idTextField.requestFocus();
    }
}
