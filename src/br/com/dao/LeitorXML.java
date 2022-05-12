
package br.com.dao;

import br.com.utils.exceptions.LeitorXmlException;
import br.com.utils.TrataErro;

/**
 *
 * @author Cleber
 */
public interface LeitorXML {

    void carregarXML(String path) throws LeitorXmlException;

    /*
     * Retorna String CNPJ destinatario
     */
    String getDestinatario();

    /**
     * Retorna String CNPJ Emitente
     */
    String getEmitente();
    
    /*
     * Retorna int numero da Danfe
     */
    int getNumeroDanfe() throws TrataErro;

    /*
     * Retorna double Valor da NF
     */
    double getValorDanfe() throws TrataErro;

    /*
     * Retorna int quantidade de volumes
     */
    int getVolumes() throws TrataErro, LeitorXmlException;
    
//    Map<String, String> getElementsMap() throws LeitorXmlException;
    
}
