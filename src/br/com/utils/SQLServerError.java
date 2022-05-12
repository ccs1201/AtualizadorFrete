
package br.com.utils;

import java.sql.SQLException;

/**
 * Classe responsavel por fazer o tratamento de erros do MS SQL Server.
 *
 * @author Cleber
 */
public class SQLServerError {

//    private static SQLError _INSTANCE = new SQLError();
//
//    public static SQLError getInstance() {
//        return _INSTANCE;
//    }
    private SQLServerError() {
    }

    /**
     * Recebe um objeto do tipo SQLException e avalia o ErrorCode
     * (SQLException.getErrorCode) devolvendo uma menssagem de erro "Humanizada"
     * para o usuario.
     *
     * @param sqle
     * @return String msg
     */
    public static String validaErro(SQLException sqle) {
        String msg = "";
        String stack = sqle.toString();

        int errorCode = sqle.getErrorCode();

        switch (errorCode) {
            case 0: {

                msg = "Erro de sintaxe SQL. Este erro pode ocorrer quando, uma \n "
                        + "propriedade da instrução possui um formato inválido ou "
                        + "não foi encontrada. \n \n"
                        + "Um exemplo disso pode ser uma entrada de datas inválida como "
                        + "datas fora do período 01/01/1900 e 31/12/2049, uma tabela inexistente no "
                        + "banco de dados ou uma clausula SQL mau construida. \n \n"
                        + "Mensagem do Driver:  \n" + sqle.getMessage()
                        + "\n" + "Causa : " + sqle.getCause()
                        + "\n" + "SQL Status: " + sqle.getSQLState()
                        + "\n" + "Codigo do Erro: " + sqle.getErrorCode()
                        + "\n" + "Stack: \n" + stack;

//                msg = "Não foi possivel localizar o servidor no IP/HOST informado. \n"
//                        + "Verifque as configurações de rede e IP.\n \n"
//                        + "Mensagem do Driver:  \n" + sqle.getMessage()
//                        + "\n" + "Causa : " + sqle.getCause()
//                        + "\n" + "SQL Status: " + sqle.getSQLState()
//                        + "\n" + "Codigo do Erro: " + sqle.getErrorCode();
                break;
            }
            case 102: {
                msg = "Erro de sintaxe na instrução SQL. \n \n"
                        + "Mensagem do Driver:  \n" + sqle.getMessage()
                        + "\n" + "Causa : " + sqle.getCause()
                        + "\n" + "SQL Status: " + sqle.getSQLState()
                        + "\n" + "Codigo do Erro: " + sqle.getErrorCode()
                        + "\n" + "Stack: \n" + stack;
                break;
            }
            case 156: {
                msg = "Erro de sintaxe na instrução SQL. \n \n"
                        + "Mensagem do Driver:  \n" + sqle.getMessage()
                        + "\n" + "Causa : " + sqle.getCause()
                        + "\n" + "SQL Status: " + sqle.getSQLState()
                        + "\n" + "Codigo do Erro: " + sqle.getErrorCode()
                        + "\n" + "Stack: \n" + stack;
                break;
            }

            case 207: {
                msg = "Erro de sintaxe na instrução SQL. \n"
                        + "Um ou mais argumentos não puderam ser encontrados. \n"
                        + "Verifique os detalhes abaixo. \n \n"
                        + "Mensagem do Driver:  \n" + sqle.getMessage()
                        + "\n" + "Causa : " + sqle.getCause()
                        + "\n" + "SQL Status: " + sqle.getSQLState()
                        + "\n" + "Codigo do Erro: " + sqle.getErrorCode()
                        + "\n" + "Stack: \n" + stack;
                break;
            }
            case 208: {
                msg = "Tabela inexistente. \n \n"
                        + "Mensagem do Driver:  \n" + sqle.getMessage()
                        + "\n" + "Causa : " + sqle.getCause()
                        + "\n" + "SQL Status: " + sqle.getSQLState()
                        + "\n" + "Codigo do Erro: " + sqle.getErrorCode()
                        + "\n" + "Stack: \n" + stack;
                break;
            }
            case 209: {
                msg = "Erro de sintaxe na instrução SQL. \n \n"
                        + "Mensagem do Driver:  \n" + sqle.getMessage()
                        + "\n" + "Causa : " + sqle.getCause()
                        + "\n" + "SQL Status: " + sqle.getSQLState()
                        + "\n" + "Codigo do Erro: " + sqle.getErrorCode()
                        + "\n" + "Stack: \n" + stack;
                break;
            }
            case 515: {
                msg = "Um ou mais campos obrigatórios não foram preenchidos.\n \n"
                        + "Mensagem do Driver:  \n" + sqle.getMessage()
                        + "\n" + "Causa : " + sqle.getCause()
                        + "\n" + "SQL Status: " + sqle.getSQLState()
                        + "\n" + "Codigo do Erro: " + sqle.getErrorCode()
                        + "\n" + "Stack: \n" + stack;
                break;
            }

            case 2601: {
                msg = "O registro que você está tentando inserir ja está cadastrado."
                        + "Em alguns casos como nomes, login, e documentos de "
                        + "identificação não são permitidos valores duplicados. \n \n"
                        + "Mensagem do Driver:  \n" + sqle.getMessage()
                        + "\n" + "Causa : " + sqle.getCause()
                        + "\n" + "SQL Status: " + sqle.getSQLState()
                        + "\n" + "Codigo do Erro: " + sqle.getErrorCode()
                        + "\n" + "Stack: \n" + stack;
                break;
            }

            case 2627: {
                msg = "O registro que você está tentando inserir ja está cadastrado. \n \n"
                        + "Mensagem do Driver:  \n" + sqle.getMessage()
                        + "\n" + "Causa : " + sqle.getCause()
                        + "\n" + "SQL Status: " + sqle.getSQLState()
                        + "\n" + "Codigo do Erro: " + sqle.getErrorCode()
                        + "\n" + "Stack: \n" + stack;
                break;
            }
            case 3726: {
                msg = "O registro não pôde ser excluído pois existem informações que depende do mesmo. \n \n"
                        + "Mensagem do Driver:  \n" + sqle.getMessage()
                        + "\n" + "Causa : " + sqle.getCause()
                        + "\n" + "SQL Status: " + sqle.getSQLState()
                        + "\n" + "Codigo do Erro: " + sqle.getErrorCode()
                        + "\n" + "Stack: \n" + stack;
                break;
            }
            case 8152: {
                msg = "O valor informado para um ou mais campos é maior que o permitido. \n \n"
                        + "Mensagem do Driver:  \n" + sqle.getMessage()
                        + "\n" + "Causa : " + sqle.getCause()
                        + "\n" + "SQL Status: " + sqle.getSQLState()
                        + "\n" + "Codigo do Erro: " + sqle.getErrorCode()
                        + "\n" + "Stack: \n" + stack;
                break;

            }
            case 18456: {
                msg = "Usuário ou Senha de conexão com o banco de dados incorretos. \n \n"
                        + "Mensagem do Driver:  \n" + sqle.getMessage()
                        + "\n" + "Causa : " + sqle.getCause()
                        + "\n" + "SQL Status: " + sqle.getSQLState()
                        + "\n" + "Codigo do Erro: " + sqle.getErrorCode()
                        + "\n" + "Stack: \n" + stack;
                break;
            }

            default: {
                msg = "Ocorreu um erro não previsto na conexão ou com o banco de dados. \n \n"
                        + "Mensagem do Driver:  \n" + sqle.getMessage()
                        + "\n" + "Causa : " + sqle.getCause()
                        + "\n" + "SQL Status: " + sqle.getSQLState()
                        + "\n" + "Codigo do Erro: " + sqle.getErrorCode()
                        + "\n" + "Stack: \n" + stack;
                break;
            }
        }

//        if (sqle.getErrorCode() == 0) {
//            msg = "Ocorreu um erro não previsto na conexão ou com o banco de dados. \n"
//                    + sqle.getMessage();
//        } else {
//            msg = sqle.getMessage();
//        }

        return msg;
    }
}
