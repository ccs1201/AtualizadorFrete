
package br.com.utils;

/**
 *
 * @author Cleber
 */
public class TrataErro extends Exception {

    public TrataErro(String msgPersonalizada, String error) {
        super(msgPersonalizada + "\n" 
                + "----------------------------- \n" 
                +"Detalhes do Erro: \n \n" + error);
    }
}
