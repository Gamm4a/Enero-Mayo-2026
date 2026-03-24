/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Exception;

/**
 *
 * @author luisb
 */
public class CantitadCeroException extends Exception {
    public CantitadCeroException() {
        super("Error: Ingresa una cantidad mayor a cero");
    }
}
