/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.teste;

/**
 *
 * @author Cleber
 */
import java.awt.Font;
import java.awt.Rectangle;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Explorer extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private JButton btAbrir = null;	

	/**
	 * This is the default constructor
	 */
	public Explorer() {
		super();
		initialize();
		informacoes();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(300, 200);
		this.setContentPane(getJContentPane());
		this.setTitle("Log");
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(getBtAbrir(), null);
		}
		return jContentPane;
	}

	/**
	 * This method initializes btAbrir
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtAbrir() {
		if (btAbrir == null) {
			btAbrir = new JButton();
			btAbrir.setBounds(new Rectangle(60, 49, 166, 69));
			btAbrir.setFont(new Font("Dialog", Font.BOLD, 24));
			btAbrir.setText("Abrir");
			btAbrir.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {					
					abrir();
				}
			});
		}
		return btAbrir;
	}

	private void abrir() {
		try {			
			Runtime.getRuntime().exec("explorer.exe " + "C:\\Logs\\");
		} catch (IOException e1) {			
			e1.printStackTrace();
		}
	}
	
	private void informacoes(){
		Properties p = new Properties(System.getProperties());	
		StringBuilder log = new StringBuilder();
		log.append("Diretótio da aplicação:"+p.getProperty("user.dir"));
		log.append("\nUsuário: "+p.getProperty("user.name"));
		log.append("\nOS Versão: "+p.getProperty("os.version"));
		log.append("\nNome OS: "+p.getProperty("os.name"));
		log.append("\nJava Versão: "+p.getProperty("java.version"));		
		salvarLog(log);
	}

	private void salvarLog(StringBuilder log) {
		try {			
			File dir = new File("C:\\Logs");
			dir.mkdir();
			File file = new File(dir,"log.txt");			
			file.createNewFile();			
			FileWriter fw = new FileWriter(file);
			fw.write(log.toString());
			fw.flush();
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	

}

