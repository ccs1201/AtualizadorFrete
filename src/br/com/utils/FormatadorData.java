
package br.com.utils;

import br.com.utils.exceptions.FormatadorDataException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Cleber
 */
public class FormatadorData {
    
    /**
     * Classe estatica para tratamente de datas, converte
     * String em DATE e DATE em String.
     */

    /**
     * Recebe um objeto do tipo Date e converte em String.
     * @param data
     * @return String dd/mm/aaaa
     * @throws TrataErro 
     */
    public static String toString(Date data) throws FormatadorDataException {

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String strData = null;

        if (data != null) {
            try {
                strData = sdf.format(data);
                return strData;
            } catch (Exception e) {
                throw new FormatadorDataException("Erro na conversão da Data em String \n"
                        + "Classe: FormatadorData \n"
                        + "Método: toString \n", e.getMessage());
            }
        } else {
            return null;
        }
    }
    
    /**
     * Recebe um objeto do tipo String e converte em Date.
     * @param data String
     * @return java.util.date
     * @throws TrataErro Se se string não for uma data  no formato dd/mm/aaaa
     */
    public static Date toDate(String data) throws FormatadorDataException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date dataFormatada;

        if (data != null || !data.isEmpty()) {
            try {
                dataFormatada = sdf.parse(data);
                
               // System.out.println(dataFormatada);
                
                return dataFormatada;
                
            } catch (ParseException e) {
                throw new FormatadorDataException("Erro na conversão de String em Data \n"
                        + "Classe: FormatadorData \n"
                        + "Método: toDate \n", e.getMessage());
            }
        } else {
            return null;
        }
    }
//    public static Date toDate(String data) throws Exception {
//
//        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
//        try {
//            Date dataformatada = sdf.parse(data);
//            return dataformatada;
//        } catch (ParseException e) {
//            throw new TrataErro("Formato de data inválido. \n"
//                    + "Digite no formato DD/MM/AAAA \n"
//                    + "(Ex. 01/01/2012).", e.getMessage());
//        }
//    }
//
//    public static String toString(Date data) {
//        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
//        return sdf.format(data.toString());
//    }
}
