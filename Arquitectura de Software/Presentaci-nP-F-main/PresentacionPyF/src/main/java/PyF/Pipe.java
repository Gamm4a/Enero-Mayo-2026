/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PyF;

/**
 *
 * @author Jesus Gammael Soto Escalante 248336
 */
public class Pipe {

    private Filtro destino;

    public Pipe(Filtro destino) {
        this.destino = destino;
    }

    public String transferir(String datos) {
        // Pipe transfiere los datos al filtro
        return destino.procesar(datos);
    }
}