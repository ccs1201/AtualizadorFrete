
package br.com.utils;

import br.com.utils.exceptions.FormatadorDataException;
import br.com.utils.exceptions.ValidaDataException;
import java.util.Date;

/**
 *
 * @author Cleber
 */
public class ValidaData {

    /*
     * Classe ValidaData
     * Metodo Estatico Validar
     * @Param Date dataInicio
     * @Param Date dataFim
     * Compara duas datas e verifica se dataFim
     * é menor que DataInicio.
     * 
     * Retorna True se dataFim for maior ou
     * igual a dataIncio ou se alguma das
     * datas for null
     * 
     */
    public static boolean validar(Date dataIncio, Date dataFim) throws ValidaDataException, FormatadorDataException {

        if (dataFim == null || dataIncio == null) {
            throw new ValidaDataException("Uma ou mais datas informadas não estão preenchidas. Verique! ",
                      "Classe: ValidaData \n"
                    + "Método: validar(Date dataIncio, Date dataFim )");

        } else if (dataFim.getTime() < dataIncio.getTime()) {
            String dataI = FormatadorData.toString(dataIncio);
            String dataF = FormatadorData.toString(dataFim);

            throw new ValidaDataException("A data Final deve ser Maior ou Igual a data Inicial",
                      "Erro ao validar datas \n"
                    + "Classe: ValidaData \n"
                    + "Método: validar(Date dataIncio, Date dataFim )\n"
                    + "Os valores informados foram: \n"
                    + "dataInicio: " + dataI + "\n"
                    + "dataFim: " + dataF);

        } else {
            return true;
        }
    }
}
