/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View;

import Assembler.ProductoInteraccionEvent;
import Assembler.VentaErrorEvent;
import Assembler.VentaEvent;
import Assembler.VentaExitosaEvent;
import Controller.VentaController;
import Model.DetalleVenta;
import Model.Producto;
import Model.Venta;
import Observer.VentaObserver;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

/**
 *
 * @author luisb
 */
public class PuntoVentaGUI2 extends JFrame implements  VentaObserver{

    private JPanel panelPrincipal;
    private JPanel panelProductos;
    private JPanel panelCarrito;
    private final VentaController controller;
    List<Producto> productos;
    JPanel contenidoCarrito;
    JLabel lblTotal;
    List<DetalleVenta> carrito;
    VentanaPago ventanaPago;

    public PuntoVentaGUI2(List<Producto> productos, VentaController controller) {
        carrito = new ArrayList<>();
        Venta venta = new Venta(1);
        this.productos = productos;
        this.controller = controller;
        contenidoCarrito = new JPanel();
        setTitle("Punto de Venta");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        setLocationRelativeTo(null);

        inicializarComponentes();
    }
    
    public void cargarProductos() {
        panelProductos.removeAll();

        for (Producto producto : productos) {
            ProductoPanel panel = new ProductoPanel(producto, controller);
            panelProductos.add(panel);
        }

        panelProductos.revalidate();
        panelProductos.repaint();
    }

    private void inicializarComponentes() {
        
        panelPrincipal = new JPanel(new BorderLayout(15, 15));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelPrincipal.setBackground(Color.DARK_GRAY);

        panelProductos = new JPanel();
        panelProductos.setLayout(new GridLayout(0, 3, 10, 10)); // 0 filas = tantas como haga falta, 4 columnas
        panelProductos.setBackground(new Color(220, 220, 220));

        JScrollPane scrollProductos = new JScrollPane(panelProductos);
        scrollProductos.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollProductos.setBorder(BorderFactory.createTitledBorder("Productos"));

        panelCarrito = new JPanel(new BorderLayout());
        panelCarrito.setBackground(new Color(220, 220, 220));

        JLabel lblCarrito = new JLabel("Carrito", SwingConstants.CENTER);
        lblCarrito.setFont(new Font("Arial", Font.BOLD, 18));

        contenidoCarrito = new JPanel();
        contenidoCarrito.setLayout(new BoxLayout(contenidoCarrito, BoxLayout.Y_AXIS));
        contenidoCarrito.setBackground(Color.WHITE);

        JScrollPane scrollCarrito = new JScrollPane(contenidoCarrito);
        scrollCarrito.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        JPanel panelPago = new JPanel();
        panelPago.setLayout(new BoxLayout(panelPago, BoxLayout.Y_AXIS));
        panelPago.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        lblTotal = new JLabel("Total: $0.00");
        lblTotal.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton btnPagar = new JButton("Pagar");
        btnPagar.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnPagar.addActionListener(e -> {

            if (carrito.isEmpty()) {
                JOptionPane.showMessageDialog(
                        this,
                        "El carrito está vacío",
                        "Error",
                        JOptionPane.WARNING_MESSAGE
                );
            } else {
                ventanaPago = new VentanaPago(this, carrito, controller);
                ventanaPago.setVisible(true);
            }});
        

        panelPago.add(lblTotal);
        panelPago.add(Box.createVerticalStrut(10));
        panelPago.add(btnPagar);

        panelCarrito.add(lblCarrito, BorderLayout.NORTH);
        panelCarrito.add(scrollCarrito, BorderLayout.CENTER);
        panelCarrito.add(panelPago, BorderLayout.SOUTH);
        
        cargarProductos();
        
        panelPrincipal.add(scrollProductos, BorderLayout.CENTER);
        panelPrincipal.add(panelCarrito, BorderLayout.EAST);

        panelCarrito.setPreferredSize(new Dimension(300, 0));

        add(panelPrincipal);
    }
                

    @Override
    public void update(VentaEvent event) {
        if (event instanceof ProductoInteraccionEvent e) { 
            this.carrito = e.getProductos();
            contenidoCarrito.removeAll();
            for (DetalleVenta detalle : e.getProductos()) {
                DetalleVentaPanel detallePanel = new DetalleVentaPanel(detalle, controller);
                
                for (int i = 1; i < detalle.getCantidad(); i++) {
                    detallePanel.getBtnMas().doClick();
                }

                contenidoCarrito.add(detallePanel);
                contenidoCarrito.add(Box.createVerticalStrut(5)); // espacio entre productos
            }
            lblTotal.setText("Total: $" + e.getTotalVenta());
            contenidoCarrito.revalidate();
            contenidoCarrito.repaint();
        }else if(event instanceof VentaErrorEvent e) {
            if (ventanaPago != null) {
                ventanaPago.mostrarError(e);
            }
        }else if(event instanceof VentaExitosaEvent e ) {
            if (ventanaPago != null) {
                ventanaPago.mostrarVentaExitosa(e.getCambio());
                carrito.clear();
                contenidoCarrito.removeAll();
                contenidoCarrito.revalidate();
                contenidoCarrito.repaint();
                ventanaPago.dispose();
            }
        }
        else {
            System.out.println("Evento no reconocido, tipo: " + event.getClass().getSimpleName());
        }
    }
    
    public static void main(String[] args) {
        
        Venta venta = new Venta(1);
        VentaController controller = new VentaController(venta);
        List<Producto> productos = List.of(
            new Producto(new BigDecimal("159.00"), "Hamburguesa"),
            new Producto(new BigDecimal("80.00"), "HotDog"),
            new Producto(new BigDecimal("80.00"), "Coca"),
            new Producto(new BigDecimal("80.00"), "Coca"),
            new Producto(new BigDecimal("80.00"), "Coca"),
            new Producto(new BigDecimal("80.00"), "Coca"),
            new Producto(new BigDecimal("80.00"), "Coca"),
            new Producto(new BigDecimal("80.00"), "Coca"),
            new Producto(new BigDecimal("80.00"), "Coca"),
            new Producto(new BigDecimal("80.00"), "Coca"),
            new Producto(new BigDecimal("80.00"), "Coca"),
            new Producto(new BigDecimal("80.00"), "Coca")
        );
        
        PuntoVentaGUI2 gui = new PuntoVentaGUI2(productos, controller);
        gui.setVisible(true);
        venta.getSuscriptores().add(gui);
        
    }
}
