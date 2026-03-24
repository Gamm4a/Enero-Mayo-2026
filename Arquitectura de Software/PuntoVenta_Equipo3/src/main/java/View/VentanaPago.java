/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author luisb
 */
package View;

import Assembler.VentaErrorEvent;
import Assembler.VentaEvent;
import Assembler.VentaExitosaEvent;
import Controller.VentaController;
import Model.DetalleVenta;
import Observer.VentaObserver;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.math.BigDecimal;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;




public class VentanaPago extends JDialog {

    private JTable tablaResumen;
    private JLabel lblTotalCompra;
    private JTextField txtPago;
    private JButton btnConfirmar;
    private VentaController controller;


    public VentanaPago(JFrame parent, List<DetalleVenta> detallesVenta, VentaController controller) {
        
        super(parent, "Pago", true); 
        this.controller = controller;
        setSize(600, 400);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout(10, 10));

        add(crearTabla(detallesVenta), BorderLayout.CENTER);
        add(crearPanelInferior(detallesVenta), BorderLayout.SOUTH);
    }

    private JScrollPane crearTabla(List<DetalleVenta> detallesVenta) {

        String[] columnas = {
                "Producto",
                "Precio Unitario",
                "Cantidad",
                "Total"
        };

        DefaultTableModel modelo = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; 
            }
        };

        BigDecimal totalCompra = BigDecimal.ZERO;

        for (DetalleVenta d : detallesVenta) {
            BigDecimal totalLinea =
                    d.getProducto().getPrecio()
                            .multiply(BigDecimal.valueOf(d.getCantidad()));

            modelo.addRow(new Object[]{
                    d.getProducto().getNombre(),
                    d.getProducto().getPrecio(),
                    d.getCantidad(),
                    totalLinea
            });

            totalCompra = totalCompra.add(totalLinea);
        }

        tablaResumen = new JTable(modelo);
        tablaResumen.setRowHeight(24);
        tablaResumen.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));

        return new JScrollPane(tablaResumen);
    }

    private JPanel crearPanelInferior(List<DetalleVenta> detallesVenta) {

        JPanel panelInferior = new JPanel();
        panelInferior.setLayout(new BoxLayout(panelInferior, BoxLayout.Y_AXIS));
        panelInferior.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        BigDecimal totalCompra = calcularTotal(detallesVenta);

        lblTotalCompra = new JLabel("Total: $" + totalCompra);
        lblTotalCompra.setFont(new Font("Arial", Font.BOLD, 18));
        lblTotalCompra.setAlignmentX(CENTER_ALIGNMENT);

        JPanel panelPago = new JPanel();
        panelPago.setLayout(new BoxLayout(panelPago, BoxLayout.X_AXIS));

        JLabel lblPago = new JLabel("Pago con: ");
        txtPago = new JTextField();
        txtPago.setMaximumSize(new Dimension(150, 30));

        panelPago.add(lblPago);
        panelPago.add(Box.createHorizontalStrut(10));
        panelPago.add(txtPago);

        panelInferior.add(lblTotalCompra);
        panelInferior.add(Box.createVerticalStrut(10));
        panelInferior.add(panelPago);
        
        btnConfirmar = new JButton("Confirmar compra");
        btnConfirmar.setAlignmentX(CENTER_ALIGNMENT);
        btnConfirmar.setPreferredSize(new Dimension(200, 35));

        btnConfirmar.addActionListener(e -> {
            try {
                BigDecimal pago = new BigDecimal(txtPago.getText().trim());

                if (pago.compareTo(BigDecimal.ZERO) <= 0) {
                    JOptionPane.showMessageDialog(
                        this,
                        "El monto de pago debe ser mayor a cero",
                        "Pago inválido",
                        JOptionPane.WARNING_MESSAGE
                    );
                    return;
                }

                controller.finalizarVenta(pago);

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(
                    this,
                    "Ingresa un monto válido",
                    "Formato incorrecto",
                    JOptionPane.ERROR_MESSAGE
                );
            }
        });
        
        panelInferior.add(lblTotalCompra);
        panelInferior.add(Box.createVerticalStrut(10));
        panelInferior.add(panelPago);

        panelInferior.add(Box.createVerticalStrut(15));
        panelInferior.add(btnConfirmar);

        return panelInferior;
    }

    private BigDecimal calcularTotal(List<DetalleVenta> detallesVenta) {
        BigDecimal total = BigDecimal.ZERO;

        for (DetalleVenta d : detallesVenta) {
            total = total.add(
                    d.getProducto().getPrecio()
                            .multiply(BigDecimal.valueOf(d.getCantidad()))
            );
        }
        return total;
    }

    public BigDecimal getPagoIngresado() {
        try {
            return new BigDecimal(txtPago.getText());
        } catch (NumberFormatException e) {
            return BigDecimal.ZERO;
        }
    }

   public void mostrarError(VentaErrorEvent e) {
        JOptionPane.showMessageDialog(
            this,
            e.getTipoEvento(),
            "Pago inválido",
            JOptionPane.WARNING_MESSAGE
        );
    }

    public void mostrarVentaExitosa(BigDecimal cambio) {
        JOptionPane.showMessageDialog(
            this,
            "Venta completada. Cambio: $" + cambio,
            "Éxito",
            JOptionPane.INFORMATION_MESSAGE
        );
    }
    
}
