/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.dao.impl;

import br.com.dao.UsuarioDAO;
import br.com.model.Usuario;
import br.com.utils.ConnectionFactory;
import br.com.utils.SQLServerError;
import br.com.utils.TrataErro;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Cleber
 */
public class UsuarioDaoImpl implements UsuarioDAO {

    private Connection conn;
    private PreparedStatement ps;
    private ResultSet rs;

    @Override
    public void insert(Usuario usuario) throws Exception {
        String sql = "insert into JavaUsuario (nome,login, senha, ativo) values (?,?,?,?)";
        try {
            conn = ConnectionFactory.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, usuario.getNome());
            ps.setString(2, usuario.getLogin());
            ps.setString(3, usuario.getSenha());
            ps.setBoolean(4, usuario.isAtivo());
            ps.execute();

        } catch (SQLException e) {
            throw new TrataErro("Erro ao gravar Usuário. \n"
                    + "Usuário ja cadastrado.", SQLServerError.validaErro(e));
        } finally {
            ConnectionFactory.closeConnection(conn, ps);
        }
    }

    @Override
    public List<Usuario> getAll() throws Exception {
        String sql = "select id, nome, senha, login, ativo from JavaUsuario order by nome";

        try {
            conn = ConnectionFactory.getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            return criarLista(rs);

        } catch (SQLException e) {
            throw new TrataErro("Erro ao recuperar lista de usuários.", SQLServerError.validaErro(e));
        } finally {
            ConnectionFactory.closeConnection(conn, ps, rs);
        }

    }

    @Override
    public Usuario getLogin(String login, String senha) throws Exception {
        String sql = "select id, nome, senha, login, ativo from JavaUsuario where login=? and senha=?";

        try {
            conn = ConnectionFactory.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, login);
            ps.setString(2, senha);
            rs = ps.executeQuery();

            if (rs.next()) {
                Usuario usuario = new Usuario();
                usuario.setId(rs.getInt("id"));
                usuario.setNome(rs.getString("nome"));
                usuario.setSenha(rs.getString("senha"));
                usuario.setLogin(rs.getString("login"));
                usuario.setAtivo(rs.getBoolean("ativo"));

                return usuario;
            } else {
                return null;
            }

        } catch (SQLException e) {
            throw new TrataErro("Erro ao recuperar login do Usuário.", SQLServerError.validaErro(e));
        } finally {
            ConnectionFactory.closeConnection(conn, ps, rs);
        }
    }

    @Override
    public void update(Usuario usuario) throws Exception {
        String sql = "update JavaUsuario set nome =?, senha=?, login=?, ativo =? where id = ?";
        try {
            conn = ConnectionFactory.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, usuario.getNome());
            ps.setString(2, usuario.getSenha());
            ps.setString(3, usuario.getLogin());
            ps.setBoolean(4, usuario.isAtivo());
            ps.setInt(5, usuario.getId());
            ps.execute();
        } catch (SQLException e) {
            throw new TrataErro("Erro ao atualizar Usuário.", SQLServerError.validaErro(e));
        } finally {
            ConnectionFactory.closeConnection(conn, ps);
        }
    }

    private List<Usuario> criarLista(ResultSet rs) throws Exception {
        List<Usuario> usuarios = new ArrayList<Usuario>();

        try {
            while (rs.next()) {
                Usuario usuario = new Usuario();
                usuario.setId(rs.getInt("id"));
                usuario.setNome(rs.getString("nome"));
                usuario.setSenha(rs.getString("senha"));
                usuario.setLogin(rs.getString("login"));
                usuario.setAtivo(rs.getBoolean("ativo"));

                usuarios.add(usuario);
            }
        } catch (Exception e) {
            throw new TrataErro("Erro ao criar lista de Usuários.", e.getMessage());
        }

        return usuarios;
    }

    @Override
    public Usuario getByID(int id) throws Exception {
        String sql = "select id, nome, login, senha, ativo from JavaUsuario where id = ?";
        Usuario usuario = new Usuario();
        try {
            conn = ConnectionFactory.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();

            if (rs.next()) {

                usuario.setId(rs.getInt("id"));
                usuario.setAtivo(rs.getBoolean("ativo"));
                usuario.setLogin(rs.getString("login"));
                usuario.setNome(rs.getString("nome"));
                usuario.setSenha(rs.getString("senha"));

                return usuario;
            }
            return null;
        } catch (SQLException e) {
            throw new TrataErro("Erro ao recuperar Usuário pelo ID", SQLServerError.validaErro(e));
        } finally {
            ConnectionFactory.closeConnection(conn, ps, rs);
        }
    }

    @Override
    public List<Usuario> findByNome(String nome) throws Exception {
        String sql = "select * from javaUsuario where nome like ? order by nome";
        try {
            conn = ConnectionFactory.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, "%" + nome + "%");
            rs = ps.executeQuery();

            return criarLista(rs);
        } catch (SQLException e) {
            throw new TrataErro("Erro recupera lista de Usuários pelo nome", SQLServerError.validaErro(e));
        } finally {
            ConnectionFactory.closeConnection(conn, ps, rs);
        }

    }

    @Override
    public List<Usuario> findByLogin(String login) throws Exception {
        String sql = "select * from javaUsuario where login like ? order by nome";
        try {
            conn = ConnectionFactory.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, "%" + login + "%");
            rs = ps.executeQuery();

            return criarLista(rs);
        } catch (SQLException e) {
            throw new TrataErro("Erro recupera lista de Usuários pelo login", SQLServerError.validaErro(e));
        } finally {
            ConnectionFactory.closeConnection(conn, ps, rs);
        }
    }

    @Override
    public List<Usuario> findByCriteria(String nome, String login) throws Exception {
        String sql = "select * from javaUsuario where login like ? and nome like ? order by nome";
        try {
            conn = ConnectionFactory.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, "%" + login + "%");
            ps.setString(2, "%" + nome + "%");
            rs = ps.executeQuery();

            return criarLista(rs);
        } catch (SQLException e) {
            throw new TrataErro("Erro recupera lista de Usuários por nome e login", SQLServerError.validaErro(e));
        } finally {
            ConnectionFactory.closeConnection(conn, ps, rs);
        }
    }
}
