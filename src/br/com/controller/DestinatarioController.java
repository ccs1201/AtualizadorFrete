package br.com.controller;

import br.com.dao.impl.DestinatarioDaoImpl;
import br.com.dto.DestinatarioDTO;
import br.com.dao.DestinatarioDAO;
import java.util.List;

/**
 *
 * @author Cleber
 */
public class DestinatarioController {

    private DestinatarioDTO destinatario;
    private List<DestinatarioDTO> destinatarios;

    public DestinatarioDTO getDestinatario() {
        return destinatario;
    }

    public void setDestinatario(DestinatarioDTO destinatario) {
        this.destinatario = destinatario;
    }

    public List<DestinatarioDTO> getAll() throws Exception {
        DestinatarioDAO dao = new DestinatarioDaoImpl();
        destinatarios = dao.getAll();
        return destinatarios;

    }

    public List<DestinatarioDTO> getByNome(String cnpj) throws Exception {
        DestinatarioDAO dao = new DestinatarioDaoImpl();
        destinatarios = dao.getByNome(cnpj);
        return destinatarios;
    }

    public DestinatarioDTO findByCNPJ(String cnpj) throws Exception {
        DestinatarioDAO dao = new DestinatarioDaoImpl();
        return dao.findByCNPJ(cnpj);
    }
}
