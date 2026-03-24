/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Assembler;

import java.math.BigDecimal;

/**
 *
 * @author luisb
 */
public class VentaExitosaEvent extends VentaEvent {
    
    BigDecimal cambio;
    
    public VentaExitosaEvent(String tipoEvento, BigDecimal cambio) {
        super(tipoEvento);
        this.cambio = cambio;
    }

    public BigDecimal getCambio() {
        return cambio;
    }
    
    
    
}
