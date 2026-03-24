/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PyF;

/**
 *
 * @author Jesus Gammael Soto Escalante 248336
 */
// Demo principal
public class PyF {

    public static void main(String[] args) {

        //creacion de la instancia de la fuente con un facto
        Fuente fuente = new Fuente("el profe borrego es bien chingón");

        //creando instancias de los filtros 
        Filtro f1 = new MayusculasFiltro();
        Filtro f2 = new ReemplazoFiltro();

        //creando instancias de los pipes con los filtros
        Pipe pipe1 = new Pipe(f1);
        Pipe pipe2 = new Pipe(f2);

        //creando instancia del sumidero
        Sumidero sumidero = new Sumidero();

        //función
        String datos = fuente.generar();

        String resultado1 = pipe1.transferir(datos);
        
        String resultado2 = pipe2.transferir(resultado1);

        sumidero.recibir(resultado2);

    }
}
