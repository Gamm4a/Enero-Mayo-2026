/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View;

import Controller.VentaController;
import Exception.ProductoNoAsociadoException;
import Model.DetalleVenta;
import Model.Producto;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.math.BigDecimal;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 *
 * @author luisb
 */
public class DetalleVentaPanel extends JPanel {

    private final JLabel lblCantidad;
    private int cantidad = 1; 
    private final JButton btnMas;
    private final JButton btnMenos;
    private final JButton btnEliminar;
    private final Producto producto;

    public DetalleVentaPanel(DetalleVenta detalleVenta, VentaController controller) {

        producto = detalleVenta.getProducto();

        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(0, 90));
        setMaximumSize(new Dimension(Integer.MAX_VALUE, 90));

        JPanel panelInfo = new JPanel();
        panelInfo.setLayout(new BoxLayout(panelInfo, BoxLayout.Y_AXIS));
        panelInfo.setBackground(Color.WHITE);

        JLabel lblNombre = new JLabel(producto.getNombre());
        lblNombre.setFont(new Font("Arial", Font.BOLD, 16));

        JLabel lblPrecio = new JLabel(
                "Precio: $" + producto.getPrecio().setScale(2, BigDecimal.ROUND_HALF_UP)
        );
        lblPrecio.setFont(new Font("Arial", Font.PLAIN, 14));

        panelInfo.add(lblNombre);
        panelInfo.add(lblPrecio);

        JPanel panelCantidad = new JPanel();
        panelCantidad.setBackground(Color.WHITE);
        panelCantidad.setLayout(new FlowLayout(FlowLayout.LEFT));

        btnMenos = new JButton("-");

        lblCantidad = new JLabel(String.valueOf(cantidad));
        lblCantidad.setFont(new Font("Arial", Font.BOLD, 16));
        lblCantidad.setHorizontalAlignment(SwingConstants.CENTER);
        lblCantidad.setPreferredSize(new Dimension(30, 30));

        btnMas = new JButton("+");

        panelCantidad.add(btnMenos);
        panelCantidad.add(lblCantidad);
        panelCantidad.add(btnMas);

        btnEliminar = new JButton("Eliminar");

        JPanel panelDerecho = new JPanel();
        panelDerecho.setLayout(new BorderLayout());
        panelDerecho.setBackground(Color.WHITE);
        panelDerecho.add(panelCantidad, BorderLayout.CENTER);
        panelDerecho.add(btnEliminar, BorderLayout.SOUTH);

        add(panelInfo, BorderLayout.CENTER);
        add(panelDerecho, BorderLayout.EAST);

        btnMas.addActionListener(e -> {
            cantidad++;
            lblCantidad.setText(String.valueOf(cantidad));
            try {
                    controller.editarProducto(producto, cantidad);
                } catch (ProductoNoAsociadoException ex) {
                    System.getLogger(DetalleVentaPanel.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
                }
        });


        btnMenos.addActionListener(e -> {
            if (cantidad > 1) {
                cantidad--;
                lblCantidad.setText(String.valueOf(cantidad));
                try {
                    controller.editarProducto(producto, cantidad);
                } catch (ProductoNoAsociadoException ex) {
                    System.getLogger(DetalleVentaPanel.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
                }
            }
            
        });

        btnEliminar.addActionListener(e -> {
            try {
                controller.quitarProducto(producto);
            } catch (ProductoNoAsociadoException ex) {
                System.getLogger(DetalleVentaPanel.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            }
        });
    }

    public JButton getBtnMas() {
        return btnMas;
    }

    public JButton getBtnMenos() {
        return btnMenos;
    }
}

