package br.com.dao.impl;

import br.com.dto.DestinatarioDTO;
import br.com.dao.DestinatarioDAO;
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
public class DestinatarioDaoImpl implements DestinatarioDAO {

    Connection conn;
    PreparedStatement ps;
    ResultSet rs;

    private List<DestinatarioDTO> criarLista(ResultSet rs) throws Exception {
        List<DestinatarioDTO> destinatarios = new ArrayList<DestinatarioDTO>();

        try {
            while (rs.next()) {
                DestinatarioDTO destinatario = new DestinatarioDTO();
                destinatario.setCpnj(rs.getString("cnpj"));
                destinatario.setNome(rs.getString("nome"));
                destinatario.setId(rs.getInt("pk"));

                destinatarios.add(destinatario);
            }
        } catch (Exception e) {
            throw new TrataErro("Erro ao criar lista de Destinatários", e.getMessage());
        }

        return destinatarios;
    }

    @Override
    public List<DestinatarioDTO> getAll() throws Exception {
        String sql = "select pk, nome, cnpj from destinatario order by nome, cnpj";

        try {
            conn = ConnectionFactory.getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            return criarLista(rs);

        } catch (SQLException e) {
            throw new TrataErro("Erro ao recuperar lista de Destinatários", SQLServerError.validaErro(e));
        } finally {
            ConnectionFactory.closeConnection(conn, ps, rs);
        }
    }

    @Override
    public List<DestinatarioDTO> getByNome(String nome) throws Exception {
        String sql = "select pk, nome, cnpj from destinatario where nome like ? order by nome, cnpj";

        try {
            conn = ConnectionFactory.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, "%" + nome + "%");
            rs = ps.executeQuery();

            return criarLista(rs);

        } catch (SQLException e) {
            throw new TrataErro("Erro ao recuperar lista de Destinatários", SQLServerError.validaErro(e));
        } finally {
            ConnectionFactory.closeConnection(conn, ps, rs);
        }
    }

    /**
     * Localiza um destinatario pelo CNPJ. Retorna new DestinatarioDTO 
     * com cnpj informado e "id = -1" se nehum destinatario for localizado.
     *
     * @param cnpj string
     * @return DestinatarioDTO
     * @throws Exception
     */
    @Override
    public DestinatarioDTO findByCNPJ(String cnpj) throws Exception {
        String sql = "select pk, nome, cnpj from destinatario "
                + "where cnpj like ?";
        try {
            conn = ConnectionFactory.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, cnpj);
            rs = ps.executeQuery();

            if (rs.next()) {
                DestinatarioDTO destinatario = new DestinatarioDTO();
                destinatario.setCpnj(rs.getString("cnpj"));
                destinatario.setNome(rs.getString("nome"));
                destinatario.setId(rs.getInt("pk"));

                return destinatario;

            } else {
                DestinatarioDTO destinatario = new DestinatarioDTO();
                destinatario.setCpnj(cnpj);
                destinatario.setId(-1);
                return destinatario;
            }

        } catch (SQLException ex) {
            throw new TrataErro("Erro ao recuperar destinátario pelo CNPJ", SQLServerError.validaErro(ex));
        } finally {
            ConnectionFactory.closeConnection(conn, ps, rs);
        }
    }
    
    @Override
    public DestinatarioDTO findByID(int id) throws Exception {
        String sql = "select pk, nome, cnpj from destinatario "
                + "where pk = ?";
        try {
            conn = ConnectionFactory.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();

            if (rs.next()) {
                DestinatarioDTO destinatario = new DestinatarioDTO();
                destinatario.setCpnj(rs.getString("cnpj"));
                destinatario.setNome(rs.getString("nome"));
                destinatario.setId(rs.getInt("pk"));

                return destinatario;

            } else {
                throw new TrataErro("Destinatário nao cadastrado.", 
                        "Um destinatário com ID " + id + " não foi localizado no banco de dados.");
            }

        } catch (SQLException ex) {
            throw new TrataErro("Erro ao recuperar destinátario pelo ID", SQLServerError.validaErro(ex));
        } finally {
            ConnectionFactory.closeConnection(conn, ps, rs);
        }
    }
}
