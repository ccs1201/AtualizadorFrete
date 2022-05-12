/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.teste;

import br.com.controller.ConhecimentoController;
import br.com.dto.ClienteDTO;
import br.com.dto.ConhecimentoDTO;
import br.com.utils.FormatadorData;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Cleber
 */
public class CcRNTeste {

    /**
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {

        List<ConhecimentoDTO> ccs;
        ConhecimentoController ccRN = new ConhecimentoController();
        Date inicio = FormatadorData.toDate("10/09/2012");
        Date fim = FormatadorData.toDate("15/09/2012");
        ccRN.getConhecimento().setCliente(new ClienteDTO());
        ccRN.getConhecimento().getCliente().setId(13);
        ccs = ccRN.getConhecimentos(inicio, fim);

        System.out.println("Imprimindo Conhecimentos");
        int i=0;
        System.out.println("Linha | ID   | Cliente  | Vl CC | Vl Frete \n");
        for (ConhecimentoDTO cc : ccs) {
            i++;
            System.out.println("" + i + " | " + cc);
        }

    }
}
