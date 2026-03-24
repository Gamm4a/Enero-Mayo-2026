/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PyF;

/**
 *
 * @author Jesus Gammael Soto Escalante 248336
 */
public class MayusculasFiltro implements Filtro {

    @Override
    public String procesar(String entrada) {
        return entrada.toUpperCase();
    }
}
