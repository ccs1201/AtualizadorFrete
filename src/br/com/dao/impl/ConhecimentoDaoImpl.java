package br.com.dao.impl;

import br.com.dao.ConhecimentoDAO;
import br.com.dto.ClienteDTO;
import br.com.dto.ConhecimentoDTO;
import br.com.dao.ClienteDAO;
import br.com.dao.DestinatarioDAO;
import br.com.entity.Municipio;
import br.com.entity.Regiao;
import br.com.model.ConhecimentoCarga;
import br.com.model.DetalheCC;
import br.com.utils.ConnectionFactory;
import br.com.utils.FormatadorData;
import br.com.utils.SQLServerError;
import br.com.utils.TrataErro;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Cleber
 */
public class ConhecimentoDaoImpl implements ConhecimentoDAO {

    private Connection conn;
    private ResultSet rs;

    /**
     * Salva um unico conhecimento. Este método avalia se o conhecimento é novo
     * ID == 0 ou se é um UPDATE ID != 0.
     *
     * @param cc
     * @throws Exception
     */
    @Override
    public void salvar(ConhecimentoCarga cc) throws Exception {

        if (cc.getId() == 0) {
            this.insert(cc);
        } else {
            this.update(cc);
        }
    }

    /**
     * Avalia a lista de conhecimentos e decide se efetua um insert ou update.
     *
     * Se o conhecimento avaliado tiver ID != 0 efetua o update e o remove da
     * lista.
     *
     * Por fim chama o metodo insertLista para persistir a lista de novos
     * conhecimentos.
     *
     * Obs: Todo conhecimento novo deve ter obrigatóriamente ID = 0 que é setado
     * no construtor da Classe ConhecimentoCarga.
     *
     * @param ccs List
     * @throws Exception
     */
    @Override
    public void salvar(List<ConhecimentoCarga> ccs) throws Exception {

        //Percorre a lista para verificar se
        //deve executar salvar ou update
        //se tiver ID !=0 faz update
        for (ConhecimentoCarga cc : ccs) {
            if (cc.getId() != 0) {
                this.update(cc);
                ccs.remove(cc);
            }
        }
        this.insertLista(ccs);
    }

    @Override
    public List<ConhecimentoDTO> findByData(Date inicio, Date fim, ClienteDTO clienteDTO) throws Exception {

        String sql = "select cc.pk, cc.vlcc, cc.datacc, cc.vlfrete,cc.javaAlterouFrete, "
                + "c.nome as nomeCliente, c.pk as idCliente, d.nome as nomeDest, d.pk as idDest, "
                + "d.cnpj as cnpjDest  from conhecimentocarga as cc "
                + "join cliente as c on cc.cdcliente = c.pk "
                + "join destinatario as d on cc.cddestinatario = d.pk "
                + "WHERE DATACC BETWEEN ? AND ? AND CDCLIENTE = ? ORDER BY vlcc, DATACC, PK";

        PreparedStatement ps = null;

        try {
            conn = ConnectionFactory.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setDate(1, new java.sql.Date(inicio.getTime()));
            ps.setDate(2, new java.sql.Date(fim.getTime()));
            ps.setInt(3, clienteDTO.getId());
            rs = ps.executeQuery();
            return criarLista(rs);
        } catch (SQLException e) {
            throw new TrataErro("Erro ao recuperar lista de Conhecimentos.", SQLServerError.validaErro(e));

        } finally {
            ConnectionFactory.closeConnection(conn, ps, rs);
        }
    }

    @Override
    public List<ConhecimentoDTO> findByData(Date inicio, Date fim, ClienteDTO clienteDTO, String destinatario) throws Exception {

        String sql = "select cc.pk, cc.vlcc, cc.datacc, cc.vlfrete,cc.javaAlterouFrete, "
                + "c.nome as nomeCliente, c.pk as idCliente, d.nome as nomeDest, d.pk as idDest, "
                + "d.cnpj as cnpjDest  from conhecimentocarga as cc "
                + "join cliente as c on cc.cdcliente = c.pk "
                + "join destinatario as d on cc.cddestinatario = d.pk "
                + "WHERE DATACC BETWEEN ? AND ? AND CDCLIENTE = ? and d.cnpj like ? ORDER BY vlcc, DATACC, PK";

        PreparedStatement ps = null;
        try {
            conn = ConnectionFactory.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setDate(1, new java.sql.Date(inicio.getTime()));
            ps.setDate(2, new java.sql.Date(fim.getTime()));
            ps.setInt(3, clienteDTO.getId());
            ps.setString(4, destinatario + "%");
            rs = ps.executeQuery();
            return criarLista(rs);
        } catch (SQLException e) {
            throw new TrataErro("Erro ao recuperar lista de Conhecimentos Por Destinátario.", SQLServerError.validaErro(e));

        } finally {
            ConnectionFactory.closeConnection(conn, ps, rs);
        }
    }

    private List<ConhecimentoDTO> criarLista(ResultSet rs) throws Exception {
        List<ConhecimentoDTO> conhecimentos = new ArrayList<>();
        try {
            while (rs.next()) {
                ConhecimentoDTO cc = new ConhecimentoDTO();
                cc.setAlterouFrete(rs.getBoolean("JavaAlterouFrete"));
                cc.getCliente().setId(rs.getInt("idcliente"));
                cc.getCliente().setNome(rs.getString("nomecliente"));
                cc.setId(rs.getInt("pk"));
                cc.setVlCC(rs.getDouble("vlcc"));
                cc.setVlFrete(rs.getDouble("vlfrete"));
                cc.setDataCC(rs.getDate("datacc"));
                cc.getDestinatario().setCpnj(rs.getString("cnpjDest"));
                cc.getDestinatario().setId(rs.getInt("idDest"));
                cc.getDestinatario().setNome(rs.getString("nomeDest"));

                conhecimentos.add(cc);
            }
            return conhecimentos;
        } catch (SQLException e) {
            throw new TrataErro("Erro ao Criar lista de Conhecimentos", SQLServerError.validaErro(e));
        }
    }

    private List<ConhecimentoDTO> criarListaSimulada(ResultSet rs) throws Exception {
        List<ConhecimentoDTO> conhecimentos = new ArrayList<>();
        try {
            while (rs.next()) {
                ConhecimentoDTO cc = new ConhecimentoDTO();
                cc.setAlterouFrete(rs.getBoolean("JavaAlterouFrete"));
                cc.getCliente().setId(rs.getInt("idcliente"));
                cc.getCliente().setNome(rs.getString("nomecliente"));
                cc.setId(rs.getInt("pk"));
                cc.setVlCC(rs.getDouble("vlcc"));
                cc.setVlFrete(rs.getDouble("vlfrete"));
                cc.setDataCC(rs.getDate("datacc"));
                cc.getDestinatario().setCpnj(rs.getString("cnpjDest"));
                cc.getDestinatario().setId(rs.getInt("idDest"));
                cc.getDestinatario().setNome(rs.getString("nomeDest"));
                cc.setNovoFrete(rs.getDouble("novoFrete"));
                cc.setVaiAlterar(rs.getBoolean("vaialterar"));

                conhecimentos.add(cc);
            }
            return conhecimentos;
        } catch (SQLException e) {
            throw new TrataErro("Erro ao Criar lista de Conhecimentos", SQLServerError.validaErro(e));
        }
    }

    /**
     * Ataualiza apenas o frete do conhecimento e seta javaAlteroufrete como
     * true.
     *
     * @param conhecimentos
     * @throws Exception
     */
    @Override
    public void updateFretes(List<ConhecimentoDTO> conhecimentos) throws Exception {
        String sql = "update conhecimentocarga set vlfrete = ?, JavaAlteroufrete= ?"
                + " where pk = ?";

        PreparedStatement ps = null;

        try {
            conn = ConnectionFactory.getConnection();
            conn.setAutoCommit(false);
            ps = conn.prepareStatement(sql);

            for (ConhecimentoDTO conhecimento : conhecimentos) {
                if (conhecimento.isVaiAlterar()) {
                    ps.setDouble(1, conhecimento.getNovoFrete());
                    ps.setBoolean(2, true);
                    ps.setInt(3, conhecimento.getId());
                    ps.executeUpdate();
                }
            }

        } catch (SQLException e) {
            conn.rollback();
            throw new TrataErro("Erro ao atualizar frete.", SQLServerError.validaErro(e));

        } finally {
            conn.commit();
            ConnectionFactory.closeConnection(conn, ps);
        }
    }

    @Override
    public List<ConhecimentoDTO> simularFretePorDestinatarioFixoLimite(Date inicio, Date fim,
            ClienteDTO clienteDTO, String destinatario, double vlFrete, double vlrLimite) throws Exception {

        String sql = "select cc.pk, cc.vlcc, cc.datacc, cc.vlfrete, cc.javaAlterouFrete, (?) as novoFrete, "
                + "case when(cc.vlfrete <> ?)"
                + "then 'true' "
                + "else 'false' "
                + " end as vaiAlterar, "
                + "c.nome as nomeCliente, c.pk as idCliente, d.nome as nomeDest, d.pk as idDest, "
                + "d.cnpj as cnpjDest  from conhecimentocarga as cc "
                + "join cliente as c on cc.cdcliente = c.pk "
                + "join destinatario as d on cc.cddestinatario = d.pk "
                + "WHERE DATACC BETWEEN ? AND ? AND CDCLIENTE = ? and d.cnpj like ? and cc.vlcc<= ? "
                + "ORDER BY vlcc, DATACC, PK";
        PreparedStatement ps = null;
        try {
            conn = ConnectionFactory.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setDouble(1, vlFrete);
            ps.setDouble(2, vlFrete);
            ps.setDate(3, new java.sql.Date(inicio.getTime()));
            ps.setDate(4, new java.sql.Date(fim.getTime()));
            ps.setInt(5, clienteDTO.getId());
            ps.setString(6, destinatario + "%");
            ps.setDouble(7, vlrLimite);
            rs = ps.executeQuery();
            return criarListaSimulada(rs);

        } catch (SQLException e) {
            throw new TrataErro("Erro ao simular atualização de Conhecimentos por Destinátario"
                    + " e valor Limite.", SQLServerError.validaErro(e));

        } finally {
            ConnectionFactory.closeConnection(conn, ps, rs);
        }
    }

    @Override
    public List<ConhecimentoDTO> simularFretePorDestinatarioFixoIntervaloValor(Date inicio, Date fim,
            ClienteDTO clienteDTO, String destinatario, double vlFrete, double vlrInicial, double vlrFinal) throws Exception {

        String sql = "select cc.pk, cc.vlcc, cc.datacc, cc.vlfrete, cc.javaAlterouFrete, (?) as novoFrete, "
                + "case when(cc.vlfrete <> ?)"
                + "then 'true' "
                + "else 'false' "
                + " end as vaiAlterar, "
                + "c.nome as nomeCliente, c.pk as idCliente, d.nome as nomeDest, d.pk as idDest, "
                + "d.cnpj as cnpjDest  from conhecimentocarga as cc "
                + "join cliente as c on cc.cdcliente = c.pk "
                + "join destinatario as d on cc.cddestinatario = d.pk "
                + "WHERE DATACC BETWEEN ? AND ? AND CDCLIENTE = ? and d.cnpj like ? "
                + "and cc.vlcc> ? and cc.vlcc<= ? "
                + "ORDER BY vlcc, DATACC, PK";

        PreparedStatement ps = null;

        try {
            conn = ConnectionFactory.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setDouble(1, vlFrete);
            ps.setDouble(2, vlFrete);
            ps.setDate(3, new java.sql.Date(inicio.getTime()));
            ps.setDate(4, new java.sql.Date(fim.getTime()));
            ps.setInt(5, clienteDTO.getId());
            ps.setString(6, destinatario + "%");
            ps.setDouble(7, vlrInicial);
            ps.setDouble(8, vlrFinal);
            rs = ps.executeQuery();
            return criarListaSimulada(rs);

        } catch (SQLException e) {
            throw new TrataErro("Erro ao simular atualização de Conhecimentos por Destinátario"
                    + " e intervalo de valores.", SQLServerError.validaErro(e));

        } finally {
            ConnectionFactory.closeConnection(conn, ps, rs);
        }
    }

    @Override
    public List<ConhecimentoDTO> simularFretePorDestinatarioPercentualIntervaloValor(Date inicio, Date fim,
            ClienteDTO clienteDTO, String destinatario, double percentualFrete, double vlrInicial, double vlrFinal) throws Exception {

        String sql = "select cc.pk, cc.vlcc, cc.datacc, cc.vlfrete, cc.javaAlterouFrete, (vlcc * ?) as novoFrete, "
                + "case when(cc.vlfrete <> vlcc * ?)"
                + "then 'true' "
                + "else 'false' "
                + "end as vaiAlterar, "
                + "c.nome as nomeCliente, c.pk as idCliente, d.nome as nomeDest, d.pk as idDest, "
                + "d.cnpj as cnpjDest  from conhecimentocarga as cc "
                + "join cliente as c on cc.cdcliente = c.pk "
                + "join destinatario as d on cc.cddestinatario = d.pk "
                + "WHERE DATACC BETWEEN ? AND ? AND CDCLIENTE = ? and d.cnpj like ? "
                + "and cc.vlcc> ? and cc.vlcc<= ? "
                + "ORDER BY vlcc, DATACC, PK";

        PreparedStatement ps = null;

        try {
            conn = ConnectionFactory.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setDouble(1, percentualFrete);
            ps.setDouble(2, percentualFrete);
            ps.setDate(3, new java.sql.Date(inicio.getTime()));
            ps.setDate(4, new java.sql.Date(fim.getTime()));
            ps.setInt(5, clienteDTO.getId());
            ps.setString(6, destinatario + "%");
            ps.setDouble(7, vlrInicial);
            ps.setDouble(8, vlrFinal);
            rs = ps.executeQuery();
            return criarListaSimulada(rs);

        } catch (SQLException e) {
            throw new TrataErro("Erro ao simular atualização de Conhecimentos em percentual \n"
                    + "por Destinátario e intervalo de valores.", SQLServerError.validaErro(e));

        } finally {
            ConnectionFactory.closeConnection(conn, ps, rs);
        }
    }

    @Override
    public List<ConhecimentoDTO> simularFretePorDestinatarioPercentualLimiteValor(Date inicio, Date fim,
            ClienteDTO clienteDTO, String destinatario, double percentualFrete, double vlrLimite) throws Exception {

        String sql = "select cc.pk, cc.vlcc, cc.datacc, cc.vlfrete, cc.javaAlterouFrete, (vlcc * ?) as novoFrete, "
                + "case when(cc.vlfrete <> vlcc * ?)"
                + "then 'true' "
                + "else 'false' "
                + "end as vaiAlterar, "
                + "c.nome as nomeCliente, c.pk as idCliente, d.nome as nomeDest, d.pk as idDest, "
                + "d.cnpj as cnpjDest  from conhecimentocarga as cc "
                + "join cliente as c on cc.cdcliente = c.pk "
                + "join destinatario as d on cc.cddestinatario = d.pk "
                + "WHERE DATACC BETWEEN ? AND ? AND CDCLIENTE = ? and d.cnpj like ? "
                + "and cc.vlcc<= ? "
                + "ORDER BY vlcc, DATACC, PK";

        PreparedStatement ps = null;

        try {
            conn = ConnectionFactory.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setDouble(1, percentualFrete);
            ps.setDouble(2, percentualFrete);
            ps.setDate(3, new java.sql.Date(inicio.getTime()));
            ps.setDate(4, new java.sql.Date(fim.getTime()));
            ps.setInt(5, clienteDTO.getId());
            ps.setString(6, destinatario + "%");
            ps.setDouble(7, vlrLimite);

            rs = ps.executeQuery();
            return criarListaSimulada(rs);

        } catch (SQLException e) {
            throw new TrataErro("Erro ao simular atualização de Conhecimentos em percentual \n"
                    + "por Destinátario e intervalo de valores.", SQLServerError.validaErro(e));

        } finally {
            ConnectionFactory.closeConnection(conn, ps, rs);
        }
    }

    @Override
    public List<ConhecimentoDTO> atualizarFretePadrão(Date DataInicio, Date dataFim,
            ClienteDTO cliente, double vlrFixo, double vlrLImite, double percentual) throws Exception {

        String sql = "select cc.pk, cc.vlcc, cc.datacc, cc.vlfrete, cc.javaAlterouFrete,"
                + "case when (vlcc<= ? ) "
                + "then ? "
                + "else ((vlcc - ?) * ?) + ? "
                + "end as novoFrete, "
                + "('false') as vaiAlterar, "
                + "c.nome as nomeCliente, c.pk as idCliente, d.nome as nomeDest, d.pk as idDest, "
                + "d.cnpj as cnpjDest  from conhecimentocarga as cc "
                + "join cliente as c on cc.cdcliente = c.pk "
                + "join destinatario as d on cc.cddestinatario = d.pk "
                + "WHERE DATACC BETWEEN ? AND ? AND CDCLIENTE = ? "
                + "ORDER BY vlcc, DATACC, PK";

        PreparedStatement ps = null;

        try {
            conn = ConnectionFactory.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setDouble(1, vlrLImite);
            ps.setDouble(2, vlrFixo);
            ps.setDouble(3, vlrLImite);
            ps.setDouble(4, percentual);
            ps.setDouble(5, vlrFixo);
            ps.setDate(6, new java.sql.Date(DataInicio.getTime()));
            ps.setDate(7, new java.sql.Date(dataFim.getTime()));
            ps.setInt(8, cliente.getId());

            rs = ps.executeQuery();

            return criarListaSimulada(rs);

        } catch (SQLException e) {
            throw new TrataErro("Erro ao atualizar frete padrão.", SQLServerError.validaErro(e));
        } finally {
            ConnectionFactory.closeConnection(conn, ps, rs);
        }
    }

    @Override
    public List<ConhecimentoDTO> atualizarFretePadrão(Date DataInicio, Date dataFim,
            ClienteDTO cliente, double vlrFixo, double vlrLImite,
            double percentual, String cnpjDestinatario) throws Exception {

        String sql = "select cc.pk, cc.vlcc, cc.datacc, cc.vlfrete, cc.javaAlterouFrete, "
                + "case when (vlcc > ? ) "
                + "then ((vlcc - ? ) * ?) + ? "
                + "else ? "
                + "end as novoFrete, "
                + "('false') as vaiAlterar, "
                + "c.nome as nomeCliente, c.pk as idCliente, d.nome as nomeDest, "
                + "d.pk as idDest, d.cnpj as cnpjDest  from conhecimentocarga as cc "
                + "join cliente as c on cc.cdcliente = c.pk "
                + "join destinatario as d on cc.cddestinatario = d.pk "
                + "WHERE DATACC BETWEEN ? AND ? AND CDCLIENTE = ? and d.cnpj like ? "
                + "ORDER BY vlcc, DATACC, PK";

        PreparedStatement ps = null;

        try {
            conn = ConnectionFactory.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setDouble(1, vlrLImite);
            ps.setDouble(2, vlrLImite);
            ps.setDouble(3, percentual);
            ps.setDouble(4, vlrFixo);
            ps.setDouble(5, vlrFixo);
            ps.setDate(6, new java.sql.Date(DataInicio.getTime()));
            ps.setDate(7, new java.sql.Date(dataFim.getTime()));
            ps.setInt(8, cliente.getId());
            ps.setString(9, cnpjDestinatario + "%");

            rs = ps.executeQuery();

            return criarListaSimulada(rs);

        } catch (SQLException e) {
            throw new TrataErro("Erro ao atualizar frete padrão.", SQLServerError.validaErro(e));
        } finally {
            ConnectionFactory.closeConnection(conn, ps, rs);
        }
    }

    private void insert(ConhecimentoCarga cc) throws Exception {
        String sql = "INSERT INTO CONHECIMENTOCARGA(PK, CDCC, CDCLIENTE, "
                + "CDDESTINATARIO, VLCC, VLFRETE, QTVOL, NOTAS, DATACC, "
                + "OBS, horaCC, importado) "
                + "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement ps = null;
        try {
            conn = ConnectionFactory.getConnection();
            conn.setAutoCommit(false);
            ps = conn.prepareStatement(sql);
            int proximoID = this.getNextIdConhecimento(conn);
            ps.setInt(1, proximoID);
            ps.setInt(2, proximoID);
            ps.setInt(3, cc.getClienteDTO().getId());
            ps.setInt(4, cc.getDestinatarioDTO().getId());
            ps.setDouble(5, cc.getVlcc());
            ps.setDouble(6, cc.getVlFrete());
            ps.setInt(7, cc.getQtVol());
            ps.setString(8, cc.getNotas());
            ps.setDate(9, new java.sql.Date(cc.getDataCC().getTime()));
            ps.setString(10, cc.getHoraCC());
            ps.setBoolean(11, cc.isImportado());

            ps.execute();

            this.insertDetalhes(conn, proximoID, cc);

            conn.commit();

        } catch (SQLException e) {
            conn.rollback();
            throw new TrataErro("Erro ao salvar conhecimento.", SQLServerError.validaErro(e));
        } finally {
            ConnectionFactory.closeConnection(conn, ps);

        }
    }

    private void update(ConhecimentoCarga cc) throws Exception {

        String sql = "UPDATE [CONHECIMENTOCARGA] set "
                + "[CDCLIENTE] = ?"
                + ",[CDDESTINATARIO] = ?"
                + ",[VLCC] = ?"
                + ",[VLFRETE] = ?"
                + ",[QTVOL] = ?"
                + ",[NOTAS] =?"
                + ",[OBS] = ?"
                + ",[importado] = ?"
                + " WHERE pk = ?";

        PreparedStatement ps = null;
        try {
            conn = ConnectionFactory.getConnection();
            conn.setAutoCommit(false);
            this.deleteDetalhes(cc.getId(), conn);
            ps = conn.prepareStatement(sql);
            ps.setInt(1, cc.getClienteDTO().getId());
            ps.setInt(2, cc.getDestinatarioDTO().getId());
            ps.setDouble(3, cc.getVlcc());
            ps.setDouble(4, cc.getVlFrete());
            ps.setInt(5, cc.getQtVol());
            ps.setString(6, cc.getNotas());
            ps.setString(7, cc.getObs());
            ps.setBoolean(8, cc.isImportado());
            ps.setInt(9, cc.getId());

            ps.execute();

            this.insertDetalhes(conn, cc.getId(), cc);

            conn.commit();

        } catch (SQLException e) {
            conn.rollback();
            throw new TrataErro("Erro ao atualizar conhecimento numero: " + cc.getId(), SQLServerError.validaErro(e));
        } finally {
            ConnectionFactory.closeConnection(conn, ps);
        }
    }

    private void insertLista(List<ConhecimentoCarga> conhecimentosCarga) throws Exception {
        String sql = "INSERT INTO CONHECIMENTOCARGA(PK, CDCC, CDCLIENTE, "
                + "CDDESTINATARIO, VLCC, VLFRETE, QTVOL, NOTAS, DATACC, "
                + "OBS, horaCC, importado) "
                + "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement ps = null;

        try {

            conn = ConnectionFactory.getConnection();
            conn.setAutoCommit(false);
            ps = conn.prepareStatement(sql);

            for (ConhecimentoCarga conhecimentoCarga : conhecimentosCarga) {

                //Busca o proximo ID de Conhecimento no Banco
                //Garantido que não haverá Exceção por PK duplicada
                int proximoID = (this.getNextIdConhecimento(conn));

                ps.setInt(1, proximoID);
                ps.setInt(2, proximoID);
                ps.setInt(3, conhecimentoCarga.getClienteDTO().getId());
                ps.setInt(4, conhecimentoCarga.getDestinatarioDTO().getId());
                ps.setDouble(5, conhecimentoCarga.getVlcc());
                ps.setDouble(6, conhecimentoCarga.getVlFrete());
                ps.setInt(7, conhecimentoCarga.getQtVol());
                ps.setString(8, conhecimentoCarga.getNotas());
                ps.setDate(9, new java.sql.Date(conhecimentoCarga.getDataCC().getTime()));
                ps.setString(10, conhecimentoCarga.getObs());
                ps.setString(11, conhecimentoCarga.getHoraCC());
                ps.setBoolean(12, conhecimentoCarga.isImportado());

                ps.execute();

                this.insertDetalhes(conn, proximoID, conhecimentoCarga);

                conn.commit();
            }

        } catch (SQLException e) {
            conn.rollback();
            throw new TrataErro("Erro ao inserir Conhecimento.", SQLServerError.validaErro(e));
        } finally {
            ConnectionFactory.closeConnection(conn, ps);
        }
    }

    private void insertDetalhes(Connection conn, int chaveCC, ConhecimentoCarga conhecimentoCarga) throws Exception {

        String sql = "INSERT INTO DETALHECC ([PK],[CHAVECC],[NUNF],[VLNF],"
                + "[QTVOLUMES],[status]) values(?,?,?,?,?,?)";

        PreparedStatement ps = null;

        try {
            ps = conn.prepareStatement(sql);

            Collection<DetalheCC> DetalhesCC = conhecimentoCarga.getDetalhesCC();

            for (DetalheCC detalheCC : DetalhesCC) {
                int proximoID = this.getNextIdDetalhe(conn);
                ps.setInt(1, proximoID);
                ps.setInt(2, chaveCC);
                ps.setString(3, detalheCC.getNuNF());
                ps.setDouble(4, detalheCC.getVlNF());
                ps.setInt(5, detalheCC.getQtdVolumes());
                ps.setBoolean(6, false);

                ps.execute();
            }
        } catch (SQLException e) {
            throw new TrataErro("Erro ao inserir detalhes do Conhecimento.", SQLServerError.validaErro(e));
        } finally {
            ConnectionFactory.closeConnection(null, ps);
        }
    }

    /**
     * Exclui do banco todos os detalhes do conhecimento cujo ID foi informado.
     * Deve ser chamado antes de um update, para garantir que não hajam detalhes
     * duplicados ou orfãos.
     *
     * @param idConhecimento
     * @param conn
     * @throws Exception
     */
    private void deleteDetalhes(int idConhecimento, Connection conn) throws Exception {
        String sql = "delete from detalhecc where chavecc = ?";
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, idConhecimento);
            ps.execute();

        } catch (SQLException e) {
            throw new TrataErro("Erro ao excluir notas do conhecimento.", SQLServerError.validaErro(e));
        } finally {
            ConnectionFactory.closeConnection(null, ps);
        }
    }

    private int getNextIdConhecimento(Connection conn) throws Exception {
        String sql = "select max(pk) from conhecimentocarga";
        int id = 0;

        PreparedStatement ps = null;

        try {
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            if (rs.next()) {
                id = 1 + rs.getInt(1);
            }
            return id;
        } catch (SQLException e) {
            throw new TrataErro("Erro ao obter próximo ID do Conhecimento.", SQLServerError.validaErro(e));
        } finally {
            ConnectionFactory.closeConnection(null, ps);
        }
    }

    private int getNextIdDetalhe(Connection conn) throws Exception {
        String sql = "select max(pk) from detalheCC";
        int id = 0;

        PreparedStatement ps = null;

        try {
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            if (rs.next()) {
                id = 1 + rs.getInt(1);
            }
            return id;
        } catch (SQLException e) {
            throw new TrataErro("Erro ao obter proximo ID do DetalheCC.", SQLServerError.validaErro(e));
        } finally {
            ConnectionFactory.closeConnection(null, ps);
        }
    }

    /**
     * Verifica se ja existe conhecimento cadastrado com numero da nota e
     * cliente informados.
     * <p>
     * Retorna true sempre que a nota já estiver cadastrada
     *
     * @param data
     * @param idCliente
     * @param detalheCC
     * @return boolean
     * @throws Exception
     */
    @Override
    public boolean isNFJaExiste(Date data, int idCliente, DetalheCC detalheCC) throws Exception {

        String sql = "select cc.cdcliente, cc.datacc, d.nunf, d.pk "
                + "from conhecimentocarga as cc "
                + "join detalhecc as d on cc.pk=d.chavecc "
                + "where cc.datacc = ? and cc.cdcliente = ? and d.nunf = ?";

        PreparedStatement ps = null;

        try {
            conn = ConnectionFactory.getConnection();
            ps = conn.prepareStatement(sql);

            ps.setDate(1, new java.sql.Date(data.getTime()));
            ps.setInt(2, idCliente);
            ps.setString(3, detalheCC.getNuNF());
            rs = ps.executeQuery();

            if (rs.next()) {
                return true;
            } else {
                return false;
            }

        } catch (SQLException e) {
            throw new TrataErro("Erro ao verificar se a nota fiscal já foi "
                    + "cadastrada para este cliente no dia: " + FormatadorData.toString(data) + ".", SQLServerError.validaErro(e));
        } finally {
            ConnectionFactory.closeConnection(conn, ps, rs);
        }
    }

    /**
     * Verfica se ja existe um conhecimento para o cliente e destinatario para a
     * data do cc. Se já existir retorna o conhecimento localizado se não retona
     * cc = null
     *
     * @param cc
     * @return ConhecimentoCarga
     * @throws Exception
     */
    @Override
    public ConhecimentoCarga verificaConhecimentoDestinatarioDia(ConhecimentoCarga cc) throws Exception {
        String sql = "SELECT PK, VLCC, VLFRETE, QTVOL, NOTAS, DATACC, OBS, cdcliente, "
                + "cddestinatario, importado"
                + " FROM CONHECIMENTOCARGA "
                + " where datacc = ? and cdcliente = ? and cddestinatario = ?";

        PreparedStatement ps = null;
        try {
            conn = ConnectionFactory.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setDate(1, new java.sql.Date(cc.getDataCC().getTime()));
            ps.setInt(2, cc.getClienteDTO().getId());
            ps.setInt(3, cc.getDestinatarioDTO().getId());
            rs = ps.executeQuery();

            ConhecimentoCarga conhecimentoCarga = null;
            if (rs.next()) {
                conhecimentoCarga = new ConhecimentoCarga();
                conhecimentoCarga.setId(rs.getInt("pk"));
                conhecimentoCarga.setVlcc(rs.getDouble("vlcc"));
                conhecimentoCarga.setVlFrete(rs.getDouble("vlfrete"));
                conhecimentoCarga.setQtVol(rs.getInt("qtvol"));
                conhecimentoCarga.setNotas(rs.getString("notas"));
                conhecimentoCarga.setDataCC(rs.getDate("datacc"));
                conhecimentoCarga.setObs(rs.getString("obs"));
                conhecimentoCarga.setImportado(rs.getBoolean("importado"));

                int cdDestinatario = rs.getInt("cddestinatario");
                int cdCliente = rs.getInt("cdcliente");

                ClienteDAO clienteDAO = new ClienteDaoImpl();
                conhecimentoCarga.setClienteDTO(clienteDAO.findByID(cdCliente));

                DestinatarioDAO destDAO = new DestinatarioDaoImpl();
                conhecimentoCarga.setDestinatarioDTO(destDAO.findByID(cdDestinatario));

                conhecimentoCarga.setDetalhesCC(this.getDetalhes(conhecimentoCarga.getId(), conn));

                return conhecimentoCarga;
            } else {
                return conhecimentoCarga;
            }

        } catch (SQLException e) {
            throw new TrataErro("Erro ao verificar se já existe uma entrega para o "
                    + "cliente " + cc.getClienteDTO().getNome()
                    + " e destinatário " + cc.getDestinatarioDTO().getNome()
                    + " na data de " + FormatadorData.toString(cc.getDataCC()) + ".", SQLServerError.validaErro(e));
        } finally {
            ConnectionFactory.closeConnection(conn, ps, rs);
        }
    }

    private List<DetalheCC> getDetalhes(int idCC, Connection conn) throws Exception {

        String sql = "select pk, chavecc, nunf, vlnf, qtvolumes, status"
                + " from detalhecc where chavecc = ? ";

        PreparedStatement ps = null;

        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, idCC);
            ResultSet rs = ps.executeQuery();

            return this.criarListaDetalhes(rs);

        } catch (SQLException e) {
            throw new TrataErro("Erro ao recuperar detalhes do conhecimento pelo id", SQLServerError.validaErro(e));
        } finally {
            ConnectionFactory.closeConnection(null, ps);
        }
    }

    private List<DetalheCC> criarListaDetalhes(ResultSet rs) throws Exception {
        List<DetalheCC> detalhes = new ArrayList<DetalheCC>();

        try {
            while (rs.next()) {
                DetalheCC detalhe = new DetalheCC();
                detalhe.setId(rs.getInt("pk"));
                detalhe.setNuNF(rs.getInt("nunf"));
                detalhe.setQtdVlumes(rs.getInt("qtvolumes"));
                detalhe.setStatus(rs.getBoolean("status"));
                detalhe.setVlNF(rs.getDouble("vlnf"));
                detalhe.setChavecc(rs.getInt("chavecc"));

                detalhes.add(detalhe);
            }
            return detalhes;
        } catch (SQLException e) {
            throw new TrataErro("Erro ao criar lista de detalhes do conhecimento.", SQLServerError.validaErro(e));
        }
    }

    /*
     * Carrega todos os conhecimentos para a regiao.
     * @param pkRegiao
     * @param dataInicio
     * @param dataFim
     */
    @Override
    public List<ConhecimentoDTO> getConhecimentosByRegiao(Regiao regiao, ClienteDTO cliente, 
            Date dataInicio, Date dataFim) throws Exception {

        String sql = "SELECT     CONHECIMENTOCARGA.PK as pk, cliente.nome as cliente, CONHECIMENTOCARGA.DATACC as datacc, DESTINATARIO.NOME AS destinatario, \n" +
"                MUNICIPIO.NOME AS Municipio, regiao.nome AS Regiao,conhecimentocarga.vlfrete as frete, \n" +
"		 CONHECIMENTOCARGA.JavaAlterouFrete AS alterouFrete, regiao.pk as idRegiao \n" +
"FROM \n" +
"	CONHECIMENTOCARGA \n" +
"		INNER JOIN \n" +
"                DESTINATARIO ON CONHECIMENTOCARGA.CDDESTINATARIO = DESTINATARIO.PK \n" +
"		AND \n" +
"				CONHECIMENTOCARGA.CDDESTINATARIO = DESTINATARIO.PK \n" +
"\n" +
"		INNER JOIN \n" +
"				CLIENTE ON CONHECIMENTOCARGA.CDCLIENTE = CLIENTE.PK\n" +
"								\n" +
"		INNER JOIN \n" +
"                MUNICIPIO ON DESTINATARIO.CDMUNICIPIO = MUNICIPIO.PK \n" +
"\n" +
"		AND \n" +
"				DESTINATARIO.CDMUNICIPIO = MUNICIPIO.PK \n" +
"\n" +
"		INNER JOIN \n" +
"                municipioregiao ON MUNICIPIO.PK = municipioregiao.cdmunicipio\n" +
"\n" +
"		INNER JOIN \n" +
"                regiao ON municipioregiao.cdregiao = regiao.pk\n" +
"\n" +
"WHERE\n" +
"     CONHECIMENTOCARGA.DATACC BETWEEN ? AND ? AND CONHECIMENTOCARGA.CDCLIENTE = ? \n" +
"                AND municipioregiao.cdregiao = ?\n" +
"\n" +
"ORDER BY \n" +
"	pk";

        PreparedStatement ps = null;
        ResultSet rs = null;
        List<ConhecimentoDTO> ccs = new ArrayList<>();
        
        
        try {
            conn = ConnectionFactory.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setDate(1, new java.sql.Date(dataInicio.getTime()));
            ps.setDate(2, new java.sql.Date(dataFim.getTime()));
            ps.setInt(3, cliente.getId());
            ps.setInt(4, regiao.getPk());
            rs = ps.executeQuery();
                        
            while(rs.next()){
                ConhecimentoDTO dto = new ConhecimentoDTO();
                dto.setId(rs.getInt("pk"));
                dto.setDataCC(rs.getDate("datacc"));
                dto.getCliente().setNome(rs.getString("cliente"));
                Municipio m = new Municipio();
                m.setNome(rs.getString("municipio"));
                dto.getDestinatario().setNome(rs.getString("destinatario"));
                dto.getDestinatario().setMunicipio(m);
                dto.setAlterouFrete(rs.getBoolean("alterouFrete"));
                Regiao r = new Regiao();
                r.setNome(rs.getString("Regiao"));
                dto.setRegiao(regiao);
                dto.setVlFrete(rs.getDouble("frete"));
                
                ccs.add(dto);
            }
            
            return ccs;

        } catch (SQLException e) {
            throw new TrataErro("Erro ao listar conheicmentos por região.", SQLServerError.validaErro(e));

        } finally {
            ConnectionFactory.closeConnection(conn, ps,rs);
        }

        
    }
    public List<ConhecimentoDTO> getConhecimentosByRegiao(Regiao regiao, 
            Date dataInicio, Date dataFim) throws Exception {

        String sql = "SELECT     CONHECIMENTOCARGA.PK as pk, cliente.nome as cliente, CONHECIMENTOCARGA.DATACC as datacc, DESTINATARIO.NOME AS destinatario, \n" +
"                MUNICIPIO.NOME AS Municipio, regiao.nome AS Regiao,conhecimentocarga.vlfrete as frete, \n" +
"		 CONHECIMENTOCARGA.JavaAlterouFrete AS alterouFrete, regiao.pk as idRegiao \n" +
"FROM \n" +
"	CONHECIMENTOCARGA \n" +
"		INNER JOIN \n" +
"                DESTINATARIO ON CONHECIMENTOCARGA.CDDESTINATARIO = DESTINATARIO.PK \n" +
"		AND \n" +
"				CONHECIMENTOCARGA.CDDESTINATARIO = DESTINATARIO.PK \n" +
"\n" +
"		INNER JOIN \n" +
"				CLIENTE ON CONHECIMENTOCARGA.CDCLIENTE = CLIENTE.PK\n" +
"								\n" +
"		INNER JOIN \n" +
"                MUNICIPIO ON DESTINATARIO.CDMUNICIPIO = MUNICIPIO.PK \n" +
"\n" +
"		AND \n" +
"				DESTINATARIO.CDMUNICIPIO = MUNICIPIO.PK \n" +
"\n" +
"		INNER JOIN \n" +
"                municipioregiao ON MUNICIPIO.PK = municipioregiao.cdmunicipio\n" +
"\n" +
"		INNER JOIN \n" +
"                regiao ON municipioregiao.cdregiao = regiao.pk\n" +
"\n" +
"WHERE\n" +
"     CONHECIMENTOCARGA.DATACC BETWEEN ? AND ? \n" +
"                AND municipioregiao.cdregiao = ?\n" +
"\n" +
"ORDER BY \n" +
"	pk";

        PreparedStatement ps = null;
        ResultSet rs = null;
        List<ConhecimentoDTO> ccs = new ArrayList<>();
        
        
        try {
            conn = ConnectionFactory.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setDate(1, new java.sql.Date(dataInicio.getTime()));
            ps.setDate(2, new java.sql.Date(dataFim.getTime()));
            ps.setInt(3, regiao.getPk());
            rs = ps.executeQuery();
                        
            while(rs.next()){
                ConhecimentoDTO dto = new ConhecimentoDTO();
                dto.setId(rs.getInt("pk"));
                dto.setDataCC(rs.getDate("datacc"));
                dto.getCliente().setNome(rs.getString("cliente"));
                Municipio m = new Municipio();
                m.setNome(rs.getString("municipio"));
                dto.getDestinatario().setNome(rs.getString("destinatario"));
                dto.getDestinatario().setMunicipio(m);
                dto.setAlterouFrete(rs.getBoolean("alterouFrete"));
                Regiao r = new Regiao();
                r.setNome(rs.getString("Regiao"));
                dto.setRegiao(regiao);
                dto.setVlFrete(rs.getDouble("frete"));
                
                ccs.add(dto);
            }
            
            return ccs;

        } catch (SQLException e) {
            throw new TrataErro("Erro ao listar conheicmentos por região.", SQLServerError.validaErro(e));

        } finally {
            ConnectionFactory.closeConnection(conn, ps,rs);
        }

        
    }


}
