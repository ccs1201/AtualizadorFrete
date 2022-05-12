
package br.com.utils;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

/**
 *
 * @author Cleber
 */
public class FormatadorMoeda {

    public static String toString(double valor) {
//        DecimalFormat df = new DecimalFormat("#,###.00");
//        valor = df.format(valor);
        NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
        String vlrFormatado = nf.format(valor);
        return vlrFormatado;
    }

    public static Double toDouble(String valor) throws TrataErro {
        NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
        double d;
        try {
            d = nf.parse(valor).doubleValue();
        } catch (ParseException ex) {
            throw new TrataErro("Erro ao converter String em Double", ex.getMessage());
        }
        return d;
    }
}
