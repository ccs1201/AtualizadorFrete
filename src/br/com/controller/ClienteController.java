package br.com.controller;

import br.com.dao.impl.ClienteDaoImpl;
import br.com.dto.ClienteDTO;
import br.com.dao.ClienteDAO;
import java.util.List;

/**
 *
 * @author Cleber
 */
public class ClienteController {

    private ClienteDTO cliente;
    private List<ClienteDTO> clientes;
    private ClienteDAO dao;

    private void listarClientes() throws Exception {
        dao = new ClienteDaoImpl();
        clientes = dao.getAll();

    }

    public ClienteDTO getCliente() {
        return cliente;
    }

    public void setCliente(ClienteDTO cliente) {
        this.cliente = cliente;
    }

    public List<ClienteDTO> getClientes() throws Exception {
        listarClientes();
        return clientes;
    }

    public void setClientes(List<ClienteDTO> clientes) {
        this.clientes = clientes;
    }

    public ClienteDTO findByCNPJ(String cnpj) throws Exception {
        dao = new ClienteDaoImpl();
        ClienteDTO clienteDTO =  dao.findByCNPJ(cnpj);
        return clienteDTO;
    }
}
