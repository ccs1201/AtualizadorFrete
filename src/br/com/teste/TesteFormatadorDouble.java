/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.teste;

import br.com.utils.FormatadorDouble;

/**
 *
 * @author Cleber
 */
public class TesteFormatadorDouble {
    
    public static void main(String[] args) {
        
        double d = 1111123.456789;
        
        System.out.println("Sem formatador: " + d);
        try {
            System.out.println("Com formatador: " + FormatadorDouble.duasCasasDecimais(d));
            
            
            String s = "0,00";
    
            System.out.println(FormatadorDouble.toDouble(s));
    
            
        } catch (Exception ex) {
           ex.printStackTrace();
        }
    }
    
    
    
    
}
