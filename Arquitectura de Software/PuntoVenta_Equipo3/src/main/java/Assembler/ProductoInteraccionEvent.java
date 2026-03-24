/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Assembler;

import Model.DetalleVenta;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author luisb
 */
public class ProductoInteraccionEvent extends VentaEvent{
    
    List<DetalleVenta> productos;
    BigDecimal totalVenta;

    public ProductoInteraccionEvent(List<DetalleVenta> productos, BigDecimal totalVenta, String tipoEvento) {
        super(tipoEvento);
        this.productos = productos;
        this.totalVenta = totalVenta;
    }

    public List<DetalleVenta> getProductos() {
        return productos;
    }

    public BigDecimal getTotalVenta() {
        return totalVenta;
    }
    
    
    
    
}
