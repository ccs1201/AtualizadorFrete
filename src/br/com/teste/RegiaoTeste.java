/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.teste;

import br.com.controller.RegiaoController;
import br.com.entity.Municipio;
import br.com.entity.Regiao;
import java.util.List;

/**
 *
 * @author ti
 */
public class RegiaoTeste {
    
    public static void main(String[] args) {
        
        RegiaoController controller = new RegiaoController();
        List<Regiao> regioes =  null;
        
        try {
            regioes = controller.getAllEager();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        
        for (Regiao regiao : regioes) {
            System.out.println("Nome regiao: " + regiao.getNome() );
            
            for(Municipio m : regiao.getMunicipios()){
                System.out.println("Municipio: " + m.getNome());
            }
            
        }
        
        
        
    }
    
}
