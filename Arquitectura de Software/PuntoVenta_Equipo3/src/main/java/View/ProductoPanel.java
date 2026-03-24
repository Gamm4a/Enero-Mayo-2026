package View;

import Controller.VentaController;
import Exception.CantitadCeroException;
import Model.Producto;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ProductoPanel extends JPanel {

    private JLabel lblNombre;
    private JLabel lblPrecio;
    private JButton btnAgregar;
    private final Producto producto;

    public ProductoPanel(Producto producto, VentaController controller) {
        this.producto = producto;

        Dimension size = new Dimension(180, 75);
        setPreferredSize(size);
        setMaximumSize(size);
        setMinimumSize(size);

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(new Color(190, 205, 235));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        lblNombre = new JLabel(producto.getNombre());
        lblNombre.setFont(new Font("Arial", Font.BOLD, 14));
        lblNombre.setAlignmentX(Component.CENTER_ALIGNMENT);

        lblPrecio = new JLabel("Precio: $" + producto.getPrecio());
        lblPrecio.setAlignmentX(Component.CENTER_ALIGNMENT);

        btnAgregar = new JButton("Agregar");
        btnAgregar.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnAgregar.setBackground(new Color(100, 255, 100));

        btnAgregar.addActionListener(e -> {
            try {
                System.out.println(producto.getNombre());
                controller.agregarProducto(producto);
            } catch (CantitadCeroException ex) {
                System.out.println("j");            }
        });

        add(lblNombre);
        add(Box.createVerticalStrut(10));
        add(lblPrecio);
        add(Box.createVerticalStrut(15));
        add(btnAgregar);
    }
}