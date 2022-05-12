/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Cleber
 */
public final class ConnectionFactory {
    
    /**
     * Classe responsavel por gerenciar a abertura e fechamento
     * das conexões com o banco de dados.
     */

    
    private static final LeitorProperties l = LeitorProperties.getINSTANCE();
    private static final String login = l.getProps().getProperty("login");
    private static final String senha = l.getProps().getProperty("senha");
    private static final String DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    private static final String host = l.getProps().getProperty("host");
    private static final String instancia = l.getProps().getProperty("instancia");
    private static final String porta = l.getProps().getProperty("porta");
    private static final String database = l.getProps().getProperty("database");
    private static final String url = "jdbc:sqlserver://" + host + "\\" + instancia + ":" + porta + ";databaseName=" + database;
    

    /**
     *
     * @return <code> Connection
     * @throws Exception
     */
    public static Connection getConnection() throws Exception {

        try {
            Connection conn = DriverManager.getConnection(url,
                    login, senha);
            conn.setClientInfo("Atualizador de Frete", "Versão 1.1");
            return conn;
        } catch (SQLException ex) {
            throw new TrataErro("Erro ao abrir conexão com Banco de Dados", SQLServerError.validaErro(ex));
        }
    }

    public static void closeConnection(Connection conn) throws Exception {
        close(conn, null, null);
    }

    public static void closeConnection(Connection conn, PreparedStatement ps) throws Exception {
        close(conn, ps, null);
    }

    public static void closeConnection(Connection conn, PreparedStatement ps, ResultSet rs) throws Exception {
        close(conn, ps, rs);
    }

    private static void close(Connection conn, PreparedStatement ps, ResultSet rs) throws Exception {
        try {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            throw new TrataErro("Erro ao fechar conexão com Bando de Dados.", SQLServerError.validaErro(e));
        }
    }
}
