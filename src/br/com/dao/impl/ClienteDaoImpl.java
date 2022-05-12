package br.com.dao.impl;

import br.com.dao.ClienteDAO;
import br.com.dto.ClienteDTO;
import br.com.utils.ConnectionFactory;
import static br.com.utils.SQLServerError.validaErro;
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
public class ClienteDaoImpl implements ClienteDAO {

    Connection conn;
    PreparedStatement ps;
    ResultSet rs;

    private List<ClienteDTO> criaLista(ResultSet rs) throws Exception {
        List<ClienteDTO> clientes;
        clientes = new ArrayList<ClienteDTO>();
        try {
            while (rs.next()) {
                ClienteDTO dto = new ClienteDTO();
                dto.setId(rs.getInt("PK"));
                dto.setNome(rs.getString("Nome"));
                dto.setFixo(rs.getDouble("vlfrete"));
                dto.setLimite(rs.getDouble("limitevlfrete"));
                dto.setPercentual(rs.getDouble("percentualfrete"));
                dto.setCnpj(rs.getString("cnpj"));
                clientes.add(dto);
            }
        } catch (SQLException ex) {
            throw new TrataErro("Erro ao criar lista de Clientes.", validaErro(ex));
        } catch (Exception e) {
            throw new TrataErro("Erro ao criar lista de Clientes.", e.getLocalizedMessage());
        }
        return clientes;
    }

    @Override
    public List<ClienteDTO> getAll() throws Exception {
        String sql = "SELECT pk, nome, vlfrete, percentualfrete, limitevlfrete,"
                + " cnpj FROM CLIENTE WHERE cdsituacao = 1 ORDER BY nome";
        List<ClienteDTO> clientes = null;

        try {
            conn = ConnectionFactory.getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            clientes = criaLista(rs);
            return clientes;
        } catch (SQLException e) {
            throw new TrataErro("Erro ao recuperar lista de Clientes.", validaErro(e));
        } finally {
            ConnectionFactory.closeConnection(conn, ps, rs);
        }

    }

    @Override
    public List<ClienteDTO> findByNome(String nome) throws Exception {
        String sql = "SELECT pk, nome, vlfrete, percentualfrete, limitevlfrete, cnpj "
                + "FROM CLIENTE WHERE cdsituacao = 1 and nome like ? "
                + "ORDER BY nome";
        List<ClienteDTO> clientes = null;

        try {
            conn = ConnectionFactory.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, "%" + nome + "%");
            rs = ps.executeQuery();
            clientes = criaLista(rs);
            return clientes;
        } catch (SQLException e) {
            throw new TrataErro("Erro ao recuperar lista de Clientes.", validaErro(e));
        } finally {
            ConnectionFactory.closeConnection(conn, ps, rs);
        }
    }

    /**
     * Recupera um cliente pelo CNPJ. Retorna new ClienteDTO com o CNPJ
     * informado e "id = -1" se nenhum cliente for localizado.
     *
     * @param cnpj String
     * @return ClienteDTO
     * @throws Exception
     */
    @Override
    public ClienteDTO findByCNPJ(String cnpj) throws Exception {

        String sql = "SELECT pk, nome, vlfrete, percentualfrete, limitevlfrete, cnpj, cdsituacao "
                + "FROM CLIENTE WHERE cnpj like ?";

        try {
            conn = ConnectionFactory.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, cnpj);
            rs = ps.executeQuery();

            if (rs.next()) {
                ClienteDTO dto = new ClienteDTO();
                dto.setId(rs.getInt("PK"));
                dto.setNome(rs.getString("Nome"));
                dto.setFixo(rs.getDouble("vlfrete"));
                dto.setLimite(rs.getDouble("limitevlfrete"));
                dto.setPercentual(rs.getDouble("percentualfrete"));
                dto.setCnpj(rs.getString("cnpj"));
                dto.setAtivo(rs.getBoolean("cdsituacao"));

                return dto;

            } else {
                ClienteDTO cliente = new ClienteDTO();
                cliente.setCnpj(cnpj);
                cliente.setId(-1);
                return cliente;
            }

        } catch (SQLException e) {
            throw new TrataErro("Erro ao recuperar cliente pelo CNPJ", validaErro(e));
        } finally {
            ConnectionFactory.closeConnection(conn, ps, rs);
        }
    }

    /**
     * Recupera um cliente pelo id.
     *
     * Retorna null se nehum cliente for encontrado.
     *
     * @param id
     * @return ClienteDTO
     * @throws Exception
     */
    public ClienteDTO findByID(int id) throws Exception {

        String sql = "SELECT pk, nome, vlfrete, percentualfrete, limitevlfrete, cnpj "
                + "FROM CLIENTE WHERE pk = ?";

        try {
            conn = ConnectionFactory.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();

            if (rs.next()) {
                ClienteDTO dto = new ClienteDTO();
                dto.setId(rs.getInt("PK"));
                dto.setNome(rs.getString("Nome"));
                dto.setFixo(rs.getDouble("vlfrete"));
                dto.setLimite(rs.getDouble("limitevlfrete"));
                dto.setPercentual(rs.getDouble("percentualfrete"));
                dto.setCnpj(rs.getString("cnpj"));

                return dto;
            } else {
                throw new TrataErro("Cliente não cadastrado.",
                        "Um cliente com ID " + id + " não foi localizado no banco de dados.");
            }
        } catch (SQLException e) {
            throw new TrataErro("Erro ao recuperar cliente pelo ID", validaErro(e));
        } finally {
            ConnectionFactory.closeConnection(conn, ps, rs);
        }
    }
}
