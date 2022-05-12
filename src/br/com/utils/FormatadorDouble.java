package br.com.utils;

import br.com.utils.exceptions.FormatadorDoubleException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

/**
 * Classe utilitaria para formatações de double
 *
 * @author Cleber
 */
public class FormatadorDouble {

    /**
     * Recebe um Valor (String) e converte em double.
     *
     * @param valor
     * @return double
     * @throws Exception
     */
    public static double formatar(String valor) throws FormatadorDoubleException {

        try {
            String str = valor.replaceAll("\\.", "").replace(',', '.').replace("R$", "");
            //str = str.replace("R$", "");
            double valorFormatado = Double.parseDouble(str);
            return valorFormatado;
        } catch (NumberFormatException e) {
            throw new FormatadorDoubleException("Erro ao converter String em Double.", e.getMessage());
        }
    }

    /**
     * Recebe um double e formata com duas casas decimais.
     *
     * @param valor
     * @return double com duas casas decimais
     * @throws Exception
     */
    public static double duasCasasDecimais(double valor) throws FormatadorDoubleException {

        DecimalFormat df = new DecimalFormat("0.00");
        String valorFormatado = df.format(valor);
        valorFormatado = valorFormatado.replace(",", ".");
        double d;

        try {
            d = Double.parseDouble(valorFormatado);

            return d;
        } catch (NumberFormatException e) {
            throw new FormatadorDoubleException("Impossivel formatar numero para duas casas decimais.", e.getMessage());
        }

    }

    /**
     * Converte double em String paternizado para pt-BR.
     *
     * @param valor
     * @return String formatada como x.xxx,xx
     */
    public static String toString(double valor) {
//        DecimalFormat df = new DecimalFormat("#,###.00");
//        valor = df.format(valor);
        NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
        String vlrFormatado = nf.format(valor);
        return vlrFormatado;
    }

    /**
     * Converte String em double.
     *
     * @param valor
     * @return double valor formatado
     * @throws TrataErro
     */
    public static Double toDouble(String valor) throws FormatadorDoubleException {
        NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
        double valorFormatado;
        try {
            //String str = valor.replaceAll("\\.", "").replace(',', '.').replace("R$", "");
            valorFormatado = nf.parse(valor).doubleValue();
            return valorFormatado;
        } catch (ParseException ex) {
            throw new FormatadorDoubleException("Erro ao converter Sting " + valor + " em Double", ex.getMessage());
        }

    }
}
