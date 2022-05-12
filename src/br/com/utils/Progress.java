
package br.com.utils;

import javax.swing.JDialog;
import javax.swing.JProgressBar;

/**
 * Fonte http://www.guj.com.br/java/48799-exemplo-de-jprogressbar
 * @author Cleber
 */
public class Progress extends JDialog {  
    public Progress() {  
        this.setTitle("Aguarde...");  
        setModal(true);  
        JProgressBar progress = new JProgressBar();  
        progress.setIndeterminate(true);  
        progress.setSize(200,20);  
        getContentPane().add(progress);  
        pack();  
         
        this.setLocationRelativeTo(null);  
    }  
}  