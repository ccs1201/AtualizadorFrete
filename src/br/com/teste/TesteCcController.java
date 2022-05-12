/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.teste;

import br.com.controller.ClienteController;
import br.com.controller.DestinatarioController;
import br.com.dto.ClienteDTO;
import br.com.dto.DestinatarioDTO;
import br.com.dao.LeitorXML;
import br.com.model.ConhecimentoCarga;
import br.com.model.DetalheCC;
import br.com.utils.exceptions.LeitorXmlException;
import br.com.utils.FormatadorData;
import br.com.utils.LeitorXMLImpl;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Cleber
 */
public class TesteCcController {

    public void importarXML(List<String> paths) throws LeitorXmlException, Exception {

        ClienteController clienteController = new ClienteController();
        DestinatarioController destinatarioController = new DestinatarioController();
        List<ConhecimentoCarga> ccs = new ArrayList<ConhecimentoCarga>();
        ClienteDTO cliente = null;
        LeitorXML leitor = new LeitorXMLImpl();
        leitor.carregarXML(paths.get(0));

        String cnpjAtual = leitor.getEmitente();
        String novoCnpj = null;

        for (String path : paths) {
            leitor.carregarXML(path);
            novoCnpj = leitor.getEmitente();

            if (isNovoEmitente(cnpjAtual, novoCnpj) || cliente == null) {
                cliente = new ClienteDTO();
                cliente = clienteController.findByCNPJ(novoCnpj);
                System.out.println("#### Carregou novo Cliente ####");
                System.out.println("Nome: " + cliente.getNome());
                System.out.println("CNPJ: " + cliente.getCnpj());
                cnpjAtual = cliente.getCnpj();

            }

            DestinatarioDTO destinatario = new DestinatarioDTO();
            destinatario = destinatarioController.findByCNPJ(leitor.getDestinatario());

            ConhecimentoCarga cc = new ConhecimentoCarga();
            cc.setClienteDTO(cliente);
            cc.setDestinatarioDTO(destinatario);
            cc.setDataCC(new Date());

            DetalheCC detalheCC = new DetalheCC();
            detalheCC.setNuNF(leitor.getNumeroDanfe());
            detalheCC.setVlNF(leitor.getValorDanfe());
            detalheCC.setQtdVlumes(leitor.getVolumes());
           System.out.println("data do cc: " + FormatadorData.toString(cc.getDataCC()));

            this.calculaFrete(cc);

            ccs.add(cc);

        }
        
        this.imprimir(ccs);

    }

    private boolean isNovoEmitente(String cnpjAtual, String novoCnpj) {

        if (cnpjAtual == null || cnpjAtual.length() == 0) {
            return true;

        } else if (!cnpjAtual.equals(novoCnpj)) {
            return true;

        } else {
            return false;
        }
    }

    private void calculaFrete(ConhecimentoCarga cc) {
        double percentual = cc.getClienteDTO().getPercentual() / 100;
        double limite = cc.getClienteDTO().getLimite();
        double fixo = cc.getClienteDTO().getFixo();

        if (cc.getVlcc() <= limite) {
            cc.setVlFrete(fixo);
        } else {
            double frete = (((cc.getVlcc() - limite) * percentual) + fixo);
            cc.setVlFrete(frete);
        }
    }

    private void imprimir(List<ConhecimentoCarga> ccs) {

        for (ConhecimentoCarga cc : ccs) {
            System.out.println("Nome Cliente: " + cc.getClienteDTO().getNome());
            System.out.println("CNPJ Cliente: " + cc.getClienteDTO().getCnpj());
            System.out.println("Nome Destinatario: " + cc.getDestinatarioDTO().getNome());
            System.out.println("CNPJ Destinatario: " + cc.getDestinatarioDTO().getCpnj());
        }
    }
}