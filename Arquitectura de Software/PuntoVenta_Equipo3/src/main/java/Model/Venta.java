/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import Assembler.ProductoInteraccionEvent;
import Assembler.VentaErrorEvent;
import Assembler.VentaEvent;
import Assembler.VentaExitosaEvent;
import Exception.ProductoNoAsociadoException;
import Observer.VentaObserver;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author luisb
 */
public class Venta {
    
    private int id;
    private List<VentaObserver> suscriptores;
    private List<DetalleVenta> productos;
    private BigDecimal total;
    private LocalDateTime fecha;

    public Venta(int id) {
        this.id = id;
        this.suscriptores = new ArrayList<>();
        this.productos = new ArrayList<>();
        this.fecha = null;
    }

    public Venta() {
    }

    public int getId() {
        return id;
    }
    
    public void agregarProducto(Producto producto) {
        boolean encontrado = false;
        for (DetalleVenta p : productos) {
            if (p.getProducto().equals(producto)) {
                p.setCantidad(p.getCantidad() + 1);
                p.setTotal();
                encontrado = true;
                break;
            }
        }
        
        if (!encontrado) {
            productos.add(new DetalleVenta(this, producto, 1, producto.getPrecio()));
        }
        
        total = getTotal();
        notificar(new ProductoInteraccionEvent(productos, total, "Agregación de Producto"));
    }
    
    public void quitarProducto(Producto producto) throws ProductoNoAsociadoException {
        Iterator<DetalleVenta> it = productos.iterator();

        while (it.hasNext()) {
            DetalleVenta p = it.next();
            if (p.getProducto().equals(producto)) {
                it.remove();

                total = getTotal();
                notificar(new ProductoInteraccionEvent(
                    productos,
                    total,
                    "Eliminación de Producto"
                ));

                return;
            }
        }

        throw new ProductoNoAsociadoException();
    }
    
    public void editarProducto(Producto producto, int cantidad) throws ProductoNoAsociadoException {
        boolean encontrado = false;

        for (DetalleVenta p : productos) {
            if (p.getProducto().equals(producto)) {
                p.setCantidad(cantidad);
                p.setTotal();
                encontrado = true;
                break;
            }
        }

        if (!encontrado) {
            throw new ProductoNoAsociadoException();
        }

        total = getTotal();
    }

    
    public void finalizarVenta(BigDecimal pagoRecibido) {
        if(productos.size() <= 0) {
            notificar(new VentaErrorEvent("Carrito Vacio"));
        } else if (pagoRecibido.compareTo(getTotal()) < 0) {
            notificar(new VentaErrorEvent("Cantidad Insuficiente"));
        } else {
            BigDecimal cambio = pagoRecibido.subtract(getTotal());
            
            notificar(new VentaExitosaEvent("Pago exitoso", cambio));
        }
                
    }
     
    public void notificar(VentaEvent event) {
        for(VentaObserver observer: suscriptores) {
            observer.update(event);
        }
    }
    
    
    public BigDecimal getTotal() {
        BigDecimal total = BigDecimal.ZERO;
        for (DetalleVenta producto : productos) {
            total = total.add(producto.getTotal());
        }
        return total;
    }


    public List<VentaObserver> getSuscriptores() {
        return suscriptores;
    }

    public void setSuscriptores(List<VentaObserver> suscriptores) {
        this.suscriptores = suscriptores;
    }

    public List<DetalleVenta> getProductos() {
        return productos;
    }

    public void setProductos(List<DetalleVenta> productos) {
        this.productos = productos;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }
    
    
    
    
    
    
    
    
}
