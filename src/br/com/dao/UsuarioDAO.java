
package br.com.dao;

import br.com.model.Usuario;
import java.util.List;

/**
 *
 * @author Cleber
 */
public interface UsuarioDAO {

    public void insert(Usuario usuario) throws Exception;

    public Usuario getByID(int id) throws Exception;

    public List<Usuario> getAll() throws Exception;

    public Usuario getLogin(String login, String senha) throws Exception;

    public void update(Usuario usuario) throws Exception;

    public List<Usuario> findByNome(String nome) throws Exception;

    public List<Usuario> findByLogin(String login) throws Exception;

    public List<Usuario> findByCriteria(String nome, String login) throws Exception;
}
