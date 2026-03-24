/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Exception.CantitadCeroException;
import Exception.ProductoNoAsociadoException;
import Model.Producto;
import Model.Venta;
import java.math.BigDecimal;

/**
 *
 * @author luisb
 */
public class VentaController {
    
    private Venta model;
    
    public VentaController(Venta model) {
        this.model = model;
    }
    
    public void agregarProducto(Producto producto) throws CantitadCeroException {
        model.agregarProducto(producto);
    }
    
    public void quitarProducto(Producto producto) throws ProductoNoAsociadoException {
        model.quitarProducto(producto);
    }
    
    public void editarProducto(Producto producto, int cantidad) throws ProductoNoAsociadoException {
        model.editarProducto(producto, cantidad);
    }
    
    public void finalizarVenta(BigDecimal pagoRecibido) {
        model.finalizarVenta(pagoRecibido);
    }
    
    
    
    
    
    
}
