package br.com.controller;

import br.com.dao.impl.UsuarioDaoImpl;
import br.com.dao.UsuarioDAO;
import br.com.model.Usuario;
import java.util.List;

/**
 *
 * @author Cleber
 */
public class UsuarioController {

    private UsuarioDAO dao;
    private Usuario usuario;

    public Usuario getUsuario() {
        if (this.usuario == null) {
            this.usuario = new Usuario();
            return usuario;
        } else {
            return this.usuario;
        }
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    private void insert() throws Exception {
        dao = new UsuarioDaoImpl();
        dao.insert(usuario);
    }

    private void update() throws Exception {
        dao = new UsuarioDaoImpl();
        dao.update(usuario);
    }

    public List<Usuario> getAll() throws Exception {
        dao = new UsuarioDaoImpl();
        return dao.getAll();
    }

    public void autenticar(String login, String senha) throws Exception {
        dao = new UsuarioDaoImpl();
        usuario = dao.getLogin(login, senha);
    }

    public void getByID(int id) throws Exception {
        dao = new UsuarioDaoImpl();
        usuario = dao.getByID(id);
    }

    public List<Usuario> procurar(String nome, String login) throws Exception {
        dao = new UsuarioDaoImpl();
        if (login.isEmpty() && nome.isEmpty()) {

            return dao.getAll();

        } else if (!login.isEmpty()) {

            return dao.findByLogin(login);

        } else if (!nome.isEmpty()) {

            return dao.findByNome(nome);

        } else {

            return dao.findByCriteria(nome, login);
        }
    }

    public void salvar() throws Exception {
        if (usuario.getId() == 0) {
            this.insert();
        } else {
            this.update();
        }
    }
}
