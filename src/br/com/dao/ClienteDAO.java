
package br.com.dao;

import br.com.dto.ClienteDTO;
import java.util.List;

/**
 *
 * @author Cleber
 */
public interface ClienteDAO {

    ClienteDTO findByCNPJ(String cnpj) throws Exception;

    List<ClienteDTO> findByNome(String nome) throws Exception;

    List<ClienteDTO> getAll() throws Exception;

    ClienteDTO findByID(int id) throws Exception;
}
