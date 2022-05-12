package br.com.teste;

import javax.swing.JOptionPane;
import javax.swing.JProgressBar;

public class TesteProgresBar {

    public static void main(String[] args) {
        JProgressBar bar = new JProgressBar();

        bar.setIndeterminate(true);
        bar.setVisible(true);
        int opt = JOptionPane.showConfirmDialog(null, "teste", "titulo", JOptionPane.YES_NO_OPTION);

        if (opt == JOptionPane.YES_OPTION) {
        }
    }
}