
package br.com.utils.exceptions;

/**
 *
 * @author Cleber
 */
public class LeitorXmlException extends Exception {

    public LeitorXmlException(String msg, Exception e) {

        super("Erro no leitor de XML \n\n"
                + "Tipo de Erro: \n" + msg + "\n\n"
                + "Detlahes do Erro: \n" + e.getMessage());
    }
}
