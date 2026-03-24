/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Exception;

/**
 *
 * @author luisb
 */
public class ProductoNoAsociadoException extends Exception{
    public ProductoNoAsociadoException() {
        super("Error: Producto no relacionado a la venta");
    }
}
