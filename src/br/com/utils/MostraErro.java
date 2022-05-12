package br.com.utils;

import br.com.view.ErroDialog;

/**
 *
 * @author Cleber
 */
public class MostraErro {

    /**
     * Classe responsavel por exibir Erros na Tela. Recebe um objeto do Tipo
     * Frame e Exception e apresenta a msg em um Dialog.
     * 
     * @param parent Form onde o erro foi gerado.
     * @param ex A Exception lançada pelo método
     */
    public static void show(java.awt.Frame parent, Exception ex) {

        ErroDialog ed = new ErroDialog(parent, ex.getMessage());
    }
}
