/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.teste;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;

/**
 *
 * @author Cleber
 */
public class ProgressBarExemplo extends JPanel {

    JProgressBar pbar;
    static final int MY_MINIMUM = 0;
    static final int MY_MAXIMUM = 100;

    public ProgressBarExemplo() {
        pbar = new JProgressBar();
        pbar.setMinimum(MY_MINIMUM);
        pbar.setMaximum(MY_MAXIMUM);
        pbar.setStringPainted(true);
        add(pbar);
    }

    public void atualizaBarra(int valor) {
        pbar.setValue(valor);
    }

    public static void main(String args[]) {
        
        JProgressBar bar = new JProgressBar();
        bar.setIndeterminate(true);
        Thread t = new Thread();
        try {
            
            t.start();
            t.sleep(1000);
        } catch (Exception e) {
        }

        final ProgressBarExemplo barra = new ProgressBarExemplo();

        JFrame frame = new JFrame("Progress Bar Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(barra);

        frame.pack();
        frame.setVisible(true);
        frame.setLocation(1, 500); // aonde vai aparecer na tela 

        for (int i = MY_MINIMUM; i <= MY_MAXIMUM; i++) {
            final int percent = i;
            try {
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        barra.atualizaBarra(percent);
                    }
                });
                Thread.sleep(5);
            } catch (InterruptedException e) {
            }
        }
    }
}
