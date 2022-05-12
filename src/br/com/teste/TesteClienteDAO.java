/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.teste;

import br.com.dao.impl.ClienteDaoImpl;
import br.com.dto.ClienteDTO;
import br.com.dao.ClienteDAO;

/**
 *
 * @author Cleber
 */
public class TesteClienteDAO {

    public static void main(String[] args) {
        ClienteDAO dao = new ClienteDaoImpl();

        ClienteDTO dto;
        try {
            dto = dao.findByCNPJ("79643565000147");
            System.out.println("Nome: " + dto.getNome());
            System.out.println("CNPJ: " + dto.getCnpj());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}
