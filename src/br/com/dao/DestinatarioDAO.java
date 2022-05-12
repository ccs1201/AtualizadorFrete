
package br.com.dao;

import br.com.dto.DestinatarioDTO;
import java.util.List;

/**
 *
 * @author Cleber
 */
public interface DestinatarioDAO {

    public List<DestinatarioDTO> getAll() throws Exception;

    public List<DestinatarioDTO> getByNome(String nome) throws Exception;

    public DestinatarioDTO findByCNPJ(String cnpj) throws Exception;

    public DestinatarioDTO findByID(int id) throws Exception;
}
