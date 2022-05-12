
package br.com.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 *
 * @author Cleber
 */
public class LeitorProperties {
    /**
     * Classe responsavel por ler os parametros de conexão
     * com banco de dados do arquivo config.properties
     * localizado na raiz do diretorio da aplicação.
     */

    private static final File file = new File("config.properties");
    private static final Properties props = new Properties();
    private static final LeitorProperties _INSTANCE = new LeitorProperties();

    public static LeitorProperties getINSTANCE() {
        try {
            ler();
        } catch (Exception ex) {
            MostraErro.show(null, ex);
        }
        return _INSTANCE;
    }

    public Properties getProps() {
        return props;
    }

    public static void ler() throws Exception {
        FileInputStream fis = null;

        if (!file.exists()) {
            criaArquivo();
            ler();
        } else {
            try {
                fis = new FileInputStream(file);
                props.load(fis);
            } catch (FileNotFoundException e) {
                throw new TrataErro("Arquivo de configuração do sistema não encontrado.", e.getMessage());
            } catch (IOException e) {
                throw new TrataErro("Erro ao ler arquivos de configuração do sistema.", e.getMessage());
            } finally {
                if (fis != null) {
                    fis.close();
                }
            }
        }
    }

    public void gravar() throws Exception {
        FileOutputStream fos = null;

        if (!file.exists()) {
            LeitorProperties.criaArquivo();
            gravar();
        }

        try {
            fos = new FileOutputStream(file);
            props.store(fos, "#Configuração da conexao com banco de dados");
        } catch (FileNotFoundException e) {
            throw new TrataErro("Arquivo de configuração do sistema não encontrado.", e.getMessage());
        } catch (IOException e) {
            throw new TrataErro("Erro ao gravar arquivo de configuração do sistema.", e.getMessage());
        } finally {
            if (fos != null) {
                fos.close();
            }
        }
    }

    private static void criaArquivo() throws Exception {
        try {
            file.createNewFile();
        } catch (IOException ex) {
            throw new TrataErro("Erro ao criar arquivo de configuração do sistema. \n"
                    + "Tente executar como administrador.", ex.getMessage() + "\n " + ex.getCause());
        }
    }
}
