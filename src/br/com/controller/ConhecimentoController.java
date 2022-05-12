/*
 * Classe de regra de negocio reponsavel por
 * todos o processos que envolvem o conhecimento
 * de carga.
 */
package br.com.controller;

import br.com.dao.impl.ConhecimentoDaoImpl;
import br.com.dto.ClienteDTO;
import br.com.dto.ConhecimentoDTO;
import br.com.dto.DestinatarioDTO;
import br.com.dao.ConhecimentoDAO;
import br.com.dao.LeitorXML;
import br.com.entity.Regiao;
import br.com.model.ConhecimentoCarga;
import br.com.model.DetalheCC;
import br.com.utils.exceptions.ConhecimentoException;
import br.com.utils.exceptions.LeitorXmlException;
import br.com.utils.FormatadorDouble;
import br.com.utils.LeitorXMLImpl;
import br.com.utils.TrataErro;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

/**
 *
 * @author Cleber
 */
public class ConhecimentoController {

    private ConhecimentoDTO conhecimento = new ConhecimentoDTO();
    private List<ConhecimentoDTO> conhecimentosDTO;
    private List<ConhecimentoCarga> conhecimentosImportados;
    private List<ConhecimentoCarga> naoImportados;

    public ConhecimentoController() {
    }

    /**
     * Construtor utlizado para inicializar a lista de conhecimentosImportados.
     * Evita perda de tempo e processamento para redimensionar o array padrão
     * que tem size 10.
     *
     * @param tamanhoListaConhecimentosImportados
     */
    public ConhecimentoController(int tamanhoListaConhecimentosImportados) {
        conhecimentosImportados = new ArrayList<>(tamanhoListaConhecimentosImportados);
    }

    public List<ConhecimentoCarga> getConhecimentosImportados() {
        return conhecimentosImportados;
    }

    public void setConhecimentosImportados(List<ConhecimentoCarga> conhecimentosImportados) {
        this.conhecimentosImportados = conhecimentosImportados;
    }

    public List<ConhecimentoCarga> getNaoImportados() {
        return naoImportados;
    }

    public void setNaoImportados(List<ConhecimentoCarga> naoImportados) {
        this.naoImportados = naoImportados;
    }

    public ConhecimentoDTO getConhecimento() {
        return conhecimento;
    }

    public void setConhecimento(ConhecimentoDTO cc) {
        this.conhecimento = cc;
    }

    /**
     * Recupera um arrary list de conhecimentos durante um periodo entre inicio
     * e fim, e pelo cliente setado na variavel conhecimento.
     *
     * @param inicio Date
     * @param fim Date
     * @return List<ConhecimentoDTO>
     * @throws Exception
     */
    public List<ConhecimentoDTO> getConhecimentos(Date inicio, Date fim) throws Exception {
        findConhecimentosByData(inicio, fim);
        return conhecimentosDTO;
    }

    public List<ConhecimentoDTO> getConhecimentos(Date inicio, Date fim, String CnpjDestinatario) throws Exception {
        findConhecimentosByData(inicio, fim, CnpjDestinatario);
        return conhecimentosDTO;
    }

    public void setConhecimentos(List<ConhecimentoDTO> conhecimentos) {
        this.conhecimentosDTO = conhecimentos;
    }

    //busca conhecimentos pela data e cliente
    private void findConhecimentosByData(Date inicio, Date fim) throws Exception {
        ConhecimentoDAO dao = new ConhecimentoDaoImpl();
        conhecimentosDTO = dao.findByData(inicio, fim, conhecimento.getCliente());
    }

    //busca conhecimentos por data, cliente e destinatario
    private void findConhecimentosByData(Date inicio, Date fim, String destinatario) throws Exception {
        ConhecimentoDAO dao = new ConhecimentoDaoImpl();
        conhecimentosDTO = dao.findByData(inicio, fim, conhecimento.getCliente(), destinatario);
    }

    //simula frete para valor fixo e com limite
    public List<ConhecimentoDTO> simularFixoLimite(String vlrLimite, String novoFrete,
            Date inicio, Date fim) throws Exception {

        if (this.conhecimentosDTO == null) {
            this.findConhecimentosByData(inicio, fim);
        }

        double limite = FormatadorDouble.formatar(vlrLimite);
        double frete = FormatadorDouble.formatar(novoFrete);

        for (ConhecimentoDTO dto : conhecimentosDTO) {
            if (dto.getVlCC() <= limite) {
                dto.setNovoFrete(frete);
                dto.setVaiAlterar(true);
            } else {
                dto.setNovoFrete(dto.getVlFrete());
            }
        }
        return conhecimentosDTO;
    }

    //simula frete para Percentual com limite
    public List<ConhecimentoDTO> simularPercentualLimite(String vlrLimite, String percentual,
            Date inicio, Date fim) throws Exception {

        if (this.conhecimentosDTO == null) {
            this.findConhecimentosByData(inicio, fim);
        }

        double limite = FormatadorDouble.formatar(vlrLimite);
        double percentualD = FormatadorDouble.formatar(percentual);

        for (ConhecimentoDTO dto : conhecimentosDTO) {
            if (dto.getVlCC() <= limite) {
                double vlCC = dto.getVlCC();
                double novoFrete = (percentualD / 100) * vlCC;

                dto.setNovoFrete(novoFrete);
                dto.setVaiAlterar(true);
            } else {
                dto.setNovoFrete(dto.getVlFrete());
            }
        }
        return conhecimentosDTO;
    }

    //simula o frete com valor fixo entre um intervalo de valores
    public List<ConhecimentoDTO> simularFixoIntervalo(String vlrInicial, String vlrFinal,
            String novoFrete, Date inicio, Date fim) throws Exception {

        if (this.conhecimentosDTO == null) {
            this.findConhecimentosByData(inicio, fim);
        }

        double vlrInicialD = FormatadorDouble.formatar(vlrInicial);
        double vlrFinalD = FormatadorDouble.formatar(vlrFinal);
        double frete = FormatadorDouble.formatar(novoFrete);

        for (ConhecimentoDTO dto : conhecimentosDTO) {
            if (dto.getVlCC() > vlrInicialD && dto.getVlCC() < vlrFinalD) {
                dto.setNovoFrete(frete);
                dto.setVaiAlterar(true);
            } else {
                dto.setNovoFrete(dto.getVlFrete());
            }
        }
        return conhecimentosDTO;
    }

    //simula o frete com Percentual entre um intervalo de valores
    public List<ConhecimentoDTO> simularPercentualIntervalo(String vlrInicial, String vlrFinal,
            String percentual, Date inicio, Date fim) throws Exception {

        if (this.conhecimentosDTO == null) {
            this.findConhecimentosByData(inicio, fim);
        }

        double vlrInicialD = FormatadorDouble.formatar(vlrInicial);
        double vlrFinalD = FormatadorDouble.formatar(vlrFinal);
        double percentualD = FormatadorDouble.formatar(percentual);

        double vlCC;
        double novoFrete;

        for (ConhecimentoDTO dto : conhecimentosDTO) {
            if (dto.getVlCC() > vlrInicialD && dto.getVlCC() < vlrFinalD) {
                vlCC = dto.getVlCC();
                novoFrete = (percentualD / 100) * vlCC;

                dto.setNovoFrete(novoFrete);
                dto.setVaiAlterar(true);
            } else {
                dto.setNovoFrete(dto.getVlFrete());
            }
        }
        return conhecimentosDTO;
    }

    public List<ConhecimentoDTO> atualizarFrete(Date inicio, Date fim) throws Exception {

        if (this.conhecimentosDTO == null) {
            return null;
        }

        ConhecimentoDAO dao = new ConhecimentoDaoImpl();
        dao.updateFretes(conhecimentosDTO);

        return this.getConhecimentos(inicio, fim);
    }

    public List<ConhecimentoDTO> atualizarFrete(Date inicio, Date fim, String cnpjDestinatario) throws Exception {

        if (this.conhecimentosDTO == null) {
            return null;
        }

        ConhecimentoDAO dao = new ConhecimentoDaoImpl();
        dao.updateFretes(conhecimentosDTO);

        return dao.findByData(inicio, fim, conhecimento.getCliente(), cnpjDestinatario);
    }

    public List<ConhecimentoDTO> simularFreteFixoLimiteDestinatario(Date inicio, Date fim,
            String strVlrNovoFrete, String cnpjDestinatario, String strVlrLimite) throws Exception {

        double valor = FormatadorDouble.formatar(strVlrNovoFrete);
        double limite = FormatadorDouble.formatar(strVlrLimite);

        ConhecimentoDAO dao = new ConhecimentoDaoImpl();
        conhecimentosDTO = dao.simularFretePorDestinatarioFixoLimite(inicio, fim, conhecimento.getCliente(), cnpjDestinatario, valor, limite);

        return conhecimentosDTO;
    }

    public List<ConhecimentoDTO> simularFreteFixoIntervaloDestinatario(Date inicio, Date fim,
            String strVlrNovoFrete, String cnpjDestinatario, String strVlrInicial, String strVlrFinal) throws Exception {

        double valor = FormatadorDouble.formatar(strVlrNovoFrete);
        double vlrInicial = FormatadorDouble.formatar(strVlrInicial);
        double vlrFinal = FormatadorDouble.formatar(strVlrFinal);

        ConhecimentoDAO dao = new ConhecimentoDaoImpl();
        conhecimentosDTO = dao.simularFretePorDestinatarioFixoIntervaloValor(inicio, fim, conhecimento.getCliente(),
                cnpjDestinatario, valor, vlrInicial, vlrFinal);

        return conhecimentosDTO;
    }

    public List<ConhecimentoDTO> simularFretePercentualIntervaloDestinatario(Date inicio, Date fim,
            String strVlrNovoFrete, String cnpjDestinatario, String strVlrInicial, String strVlrFinal) throws Exception {

        double valor = FormatadorDouble.formatar(strVlrNovoFrete);
        double vlrInicial = FormatadorDouble.formatar(strVlrInicial);
        double vlrFinal = FormatadorDouble.formatar(strVlrFinal);

        valor = valor / 100;

        ConhecimentoDAO dao = new ConhecimentoDaoImpl();
        conhecimentosDTO = dao.simularFretePorDestinatarioPercentualIntervaloValor(inicio, fim, conhecimento.getCliente(),
                cnpjDestinatario, valor, vlrInicial, vlrFinal);

        return conhecimentosDTO;
    }

    public List<ConhecimentoDTO> simularFretePercentualLimiteDestinatario(Date inicio, Date fim,
            String strVlrNovoFrete, String cnpjDestinatario, String strVlrLimite) throws Exception {

        double valor = FormatadorDouble.formatar(strVlrNovoFrete);
        double vlrLimite = FormatadorDouble.formatar(strVlrLimite);
        valor = valor / 100;

        ConhecimentoDAO dao = new ConhecimentoDaoImpl();
        conhecimentosDTO = dao.simularFretePorDestinatarioPercentualLimiteValor(inicio, fim, conhecimento.getCliente(),
                cnpjDestinatario, valor, vlrLimite);

        return conhecimentosDTO;
    }

    public List<ConhecimentoDTO> simularFretePadrao(Date DataInicio, Date dataFim,
            String strVlrFixo, String strVlrLImite, String strPercentual, String cnpjDestinatario) throws Exception {

        double vlrfixo = FormatadorDouble.formatar(strVlrFixo);
        double vlrLimite = FormatadorDouble.formatar(strVlrLImite);
        double percentual = FormatadorDouble.formatar(strPercentual);

        percentual = percentual / 100;

        ConhecimentoDAO dao = new ConhecimentoDaoImpl();
        if (cnpjDestinatario == null || cnpjDestinatario.isEmpty()) {
            conhecimentosDTO = dao.atualizarFretePadrão(DataInicio, dataFim,
                    conhecimento.getCliente(), vlrfixo, vlrLimite, percentual);
        } else {
            conhecimentosDTO = dao.atualizarFretePadrão(DataInicio, dataFim,
                    conhecimento.getCliente(), vlrfixo, vlrLimite, percentual, cnpjDestinatario);
        }

        for (ConhecimentoDTO dto : conhecimentosDTO) {
            if (dto.getNovoFrete() != dto.getVlFrete()) {
                dto.setVaiAlterar(true);
            }
        }

        return conhecimentosDTO;
    }

    /**
     * Método que faz a importação do dados do arquivo XML para objetos
     * ConhecimentoCarga o procedimento para importação dos arquivos deve
     * obedecer o seguintes critérios:
     * <p>
     * O arquivo não pode ser importado duas vezes, então é precisso garantir
     * que a nota importada entre apenas uma vez na lista.
     * <p>
     * Devemos verificar na lista se já existe um conhecimento para o mesmo
     * destinátario e cliente. Se houver devemos fazer o merge dos detlahes com
     * a conhecimento existente.
     * <p>
     * Se já existir um conhecimento no banco para o mesmo cliente e
     * destinatário no mesmo dia, devemos recuperar o conhecimento que já existe
     * e efetuar o merge com os novos dados importados. Os dados importados não
     * podem ser iguais ao do conhecimento existente (o DetalheCC).
     * <p>
     * Ao final da importação chama o método calcularFrete passando a lista de
     * conhecimentoXML para que seja calculado o valor do frete.
     *
     * @param paths Lista contendo o caminho dos arquivos
     * @param dataCC Date - data que será setada no conhecimento
     * @throws LeitorXmlException
     * @throws Exception
     */
    public void importarXML(List<String> paths, Date dataCC) throws LeitorXmlException, Exception {

        ClienteController clienteController = new ClienteController();
        DestinatarioController destinatarioController = new DestinatarioController();
        ClienteDTO cliente = null;
        LeitorXML leitor = new LeitorXMLImpl();
//        leitor.carregarXML(paths.get(0));

        String cnpjAtual = null;
        String novoCnpj = null;

        for (String path : paths) {
            System.out.println("XML Atual: " + path);
            leitor.carregarXML(path);
            novoCnpj = leitor.getEmitente();

            //Verifica se o emitente ainda é o mesmo.
            //Evita objetos desnecessários.
            if (cliente == null || isNovoEmitente(cnpjAtual, novoCnpj)) {
//                cliente = new ClienteDTO();
                cliente = clienteController.findByCNPJ(novoCnpj);
                cnpjAtual = cliente.getCnpj();
            }

            DestinatarioDTO destinatario;
            destinatario = this.getDestinatario(leitor.getDestinatario());

            DetalheCC detalheCC = new DetalheCC();
            detalheCC.setNuNF(leitor.getNumeroDanfe());
            detalheCC.setVlNF(leitor.getValorDanfe());
            detalheCC.setQtdVlumes(leitor.getVolumes());

            this.addConhecimentoImportado(cliente, destinatario, detalheCC, dataCC);

        }
        this.calcularFrete();

//        System.out.println("#####################################");
//        System.out.println("####### Imprimindo importados #######");
//        System.out.println("#####################################");
//        this.imprimir(conhecimentosImportados);
//
//        System.out.println("#####################################");
//        System.out.println("##### Imprimindo não importados #####");
//        System.out.println("#####################################");
//        this.imprimir(naoImportados);
    }

    /**
     * Método responsável pela controle da inserção de conhecimentos na lista de
     * conhecimentosImportados.
     *
     * @param cliente
     * @param destinatario
     * @param detalhe
     * @throws Exception
     */
    private void addConhecimentoImportado(ClienteDTO cliente, DestinatarioDTO destinatario,
            DetalheCC detalhe, Date dataCC) throws Exception {

        ConhecimentoCarga cc = new ConhecimentoCarga();
        //Seta uma hora no conhecimento para não gerar problemas
        //com relatorios e consultas no SisTrans.

        GregorianCalendar cal;
        cal = new GregorianCalendar(TimeZone.getTimeZone("GMT-3"), new Locale("pt_BR"));

        String hora = "" + cal.get(Calendar.HOUR_OF_DAY) + ":" + cal.get(Calendar.MINUTE) + ":" + cal.get(Calendar.SECOND);
        cc.setHoraCC(hora);
        cc.setObs("Importado pelo atualizador de Fretes.");
        cc.setDataCC(dataCC);
        cc.setClienteDTO(cliente);
        cc.setDestinatarioDTO(destinatario);
        cc.setDetalheCC(detalhe);
        if (this.isValido(cc)) {

            //Se a nota não estiver cadastrada continua a validação.
            if (!this.nfJaExiste(cc, detalhe)) {

                //verifica se ja existe conhecimento na lista para
                //o mesmo cliente e destinatario.
                cc = this.verificarConhecimentoNaLista(cc);

                //Verifica se existe conhecimento no banco para 
                //cliente e destinatario no dia.
                cc = this.verificarConhecimentoNoBanco(cc);
            }
        }
        //Verifica se o conhecimento pode ser importado.
        if (cc.isPodeImportar()) {
            cc.setImportado(true);
            conhecimentosImportados.add(cc);
        } else {
            this.addNaoImportado(cc);
        }

    }

    private DestinatarioDTO getDestinatario(String cnpj) throws Exception {

        DestinatarioController controller = new DestinatarioController();
        return controller.findByCNPJ(cnpj);

    }

    private void calcularFrete() throws TrataErro {

        for (ConhecimentoCarga cc : conhecimentosImportados) {
            double percentual = cc.getClienteDTO().getPercentual() / 100;
            double limite = cc.getClienteDTO().getLimite();
            double fixo = cc.getClienteDTO().getFixo();

            double vlCC = 0;
            int qtdVols = 0;

            /* Percorre os detalhes para obter 
             * o valor total do conhecimento e a
             * quantidade de volumes
             */
            for (DetalheCC dcc : cc.getDetalhesCC()) {
                vlCC += dcc.getVlNF();
                qtdVols += dcc.getQtdVolumes();
            }

            //Seta o valor do conhecimento
            cc.setVlcc(vlCC);

            //Seta a quantidade de Volumes
            cc.setQtVol(qtdVols);

            /*
             * Se o valor do cc estiver dentro do limite
             * aplica o frete fixo. Caso contrario aplica
             * a regra de frete fixo + percentual.
             */
            if (vlCC <= limite) {
                cc.setVlFrete(fixo);
            } else {
                double vlFrete = (((cc.getVlcc() - limite) * percentual) + fixo);
                cc.setVlFrete(FormatadorDouble.duasCasasDecimais(vlFrete));
            }
        }
    }

    /**
     * Verifica se já existe um conhecimento para o mesmo cliente e destinatario
     * na lista de Conhecimentos comparando os <b>IDs</b> de cliente e
     * destinatário da lista com os do conhecimento passado no paramentro teste.
     * <p>
     * <b>Se já existir</b> conhecimento na lista, faz o merge do existente com
     * o conhecimento passado no parametro teste e remove o existente da lista.
     * <p>
     * <b>Se não</b> retorna o próprio conhecimento passado no parametro teste.
     *
     * @param teste ConhecimentoCarga
     * @return ConhecimentoCarga
     */
    private ConhecimentoCarga verificarConhecimentoNaLista(ConhecimentoCarga teste) {

        int idClienteNovo = teste.getClienteDTO().getId();
        int idDestinatarioNovo = teste.getDestinatarioDTO().getId();

        for (ConhecimentoCarga cc : conhecimentosImportados) {
            int idClienteLista = cc.getClienteDTO().getId();
            int idDestinatarioLista = cc.getDestinatarioDTO().getId();

            //Verifica se o cliente e o destinatario são iguais
            //Se forem iguais faz o merge com o existente e remove o existente da lista.
            if (idClienteLista == idClienteNovo && idDestinatarioLista == idDestinatarioNovo) {

                conhecimentosImportados.remove(cc);
                cc.mergeConhecimentos(teste);
                return cc;
            }
        }
        return teste;
    }

    /**
     * Verifica se ainda é o mesmo emitente para não gerar objetos
     * desnecessarios. Retorna True quando cnpjAtual for diferente do novoCnpj
     * ou quando cnpjAtual for null.
     *
     * @param cnpjAtual String
     * @param novoCnpj String
     * @return boolean
     */
    private boolean isNovoEmitente(String cnpjAtual, String novoCnpj) {

        if (cnpjAtual == null || cnpjAtual.length() == 0) {
            return true;

        } else if (!cnpjAtual.equals(novoCnpj)) {
            return true;

        } else {
            return false;
        }
    }

    /**
     * Verifica se cliente e Destinatario do Conhecimento são validos. Se um
     * deles tiver "id == -1" ou cliente estiver inativo o conhecimento é
     * inválido. Seta <b>cc.setPodeImportar(false)</b>
     * <p>
     * Todo conhecimento inválido não deve ser persistido.
     *
     * @param cc Conhecimento a ser validado.
     * @return boolean
     */
    private boolean isValido(ConhecimentoCarga cc) throws Exception {

        if (!cc.getClienteDTO().isAtivo()) {
            cc.setMotivoImportacao("Cliente inativo.");
            cc.setPodeImportar(false);
//            this.addNaoImportado(cc);
            return false;
        } else if (cc.getClienteDTO().getId() == -1) {
            cc.getClienteDTO().setNome(cc.getClienteDTO().getCnpj());
            cc.setMotivoImportacao("Cliente não cadastrado.");
            cc.setPodeImportar(false);
            return false;
        } else if (cc.getDestinatarioDTO().getId() == -1) {
            cc.getDestinatarioDTO().setNome("CNPJ/CPF " + cc.getDestinatarioDTO().getCpnj());
            cc.setMotivoImportacao("Destinatário não cadastrado.");
            cc.setPodeImportar(false);
//            this.addNaoImportado(cc);
            return false;
        } else {
            return true;
        }
    }

    private void imprimir(List<ConhecimentoCarga> ccs) {
        //Garante que a lista não esta vazia se não
        //lança NullPoiterException
        if (ccs != null) {

            System.out.println("Qtd de registros: " + ccs.size());

            for (ConhecimentoCarga cc : ccs) {
                System.out.println("###################################################################");
                System.out.println("Motivo: " + cc.getMotivoImportacao());
                System.out.println("Nome Cliente: " + cc.getClienteDTO().getNome());
                System.out.println("CNPJ Cliente: " + cc.getClienteDTO().getCnpj());
                System.out.println("ID Cliente: " + cc.getClienteDTO().getId());
                System.out.println("Nome Destinatario: " + cc.getDestinatarioDTO().getNome());
                System.out.println("CNPJ Destinatario: " + cc.getDestinatarioDTO().getCpnj());
                System.out.println("ID Destinatario: " + cc.getDestinatarioDTO().getId());
                System.out.println("Vl CC: " + cc.getVlcc());
                System.out.println("Vl Frete: " + cc.getVlFrete());
                System.out.println("Qtd Vols: " + cc.getQtVol());
                System.out.println("Notas: " + cc.getNotas());

            }
        }
    }

    public void insertConhecimentos(List<ConhecimentoCarga> conhecimentos) throws ConhecimentoException, Exception {

        if (conhecimentos.isEmpty()) {
            throw new ConhecimentoException("Nehum conhecimento válido foi localizado nos arquivos de NFe.",
                    "Não é possivel salvar uma lista de conhecimetnos vazia. \n"
                    + "As notas que você esta tentando importar podem "
                    + "já ter sido importadas.Verifque a listagem de "
                    + "notas não importadas.");
        } else {
            ConhecimentoDAO dao = new ConhecimentoDaoImpl();
            dao.salvar(conhecimentos);
        }
    }

    /**
     * Adiciona um conhecimento que não será importado a lista de conhecimentos
     * não importados Lis<ConhecimentoCarga> naoImportados.
     *
     * @param cc
     * @return void
     */
    private void addNaoImportado(ConhecimentoCarga cc) {
        if (this.naoImportados != null) {
            this.naoImportados.add(cc);
        } else {
            this.naoImportados = new ArrayList<ConhecimentoCarga>();
            this.naoImportados.add(cc);
        }
    }

    /**
     * Verifica se já existe um conhecimento cadastrado para o cliente e
     * destinatario na data do conhecimento no banco de dados.
     * <p>
     * Se existir faz o merge do conhecimento passado por parametro com o
     * conhecimento recuperado do banco.
     * <p>
     * <b>Sempre retorna o conhecimento passado no parametro cc para evitar
     * NullPointerException.</b>
     * <p>
     *
     * @param cc
     * @return ConhecimentoCarga
     * @throws Exception Apenas repassa a exception lançada pelo DAO
     */
    private ConhecimentoCarga verificarConhecimentoNoBanco(ConhecimentoCarga cc) throws Exception {

        ConhecimentoDAO dao = new ConhecimentoDaoImpl();
        ConhecimentoCarga ccBanco = dao.verificaConhecimentoDestinatarioDia(cc);

        //Verifica o objeto não veio null do DAO
        //e faz o merge
        if (ccBanco != null) {
            ccBanco.mergeConhecimentos(cc);
            return ccBanco;
        } else {
            return cc;
        }
    }

    /**
     * Verifica se nota fiscal já está cadastrada para o cliente na data do
     * conhecimento.
     * <p>
     * Se a nota ja estiver cadastrada seta o motivo da não importação como NF
     * já cadastrada e adiciona a lista de naoImportados.
     *
     * @param cc ConhecimentoCarga Conhecimento que esta senha criado.
     * @param detalheCC Detalhe que esta sendo adicionado ao conhecimento.
     * @return true Se a nota ja estiver cadastrada.
     * @throws Exception
     */
    private boolean nfJaExiste(ConhecimentoCarga cc, DetalheCC detalheCC) throws Exception {

        ConhecimentoDAO dao = new ConhecimentoDaoImpl();

        if (dao.isNFJaExiste(cc.getDataCC(), cc.getClienteDTO().getId(), detalheCC)) {
            cc.setMotivoImportacao("Nota fiscal: " + detalheCC.getNuNF() + " já cadastrada para o cliente na data informada.");
            cc.setPodeImportar(false);
//            this.addNaoImportado(cc);
            return true;
        }
        return false;
    }

    /**
     * Verifica se a nota já foi inserida no conhecimento do destinatario. Caso
     * exista seta o motivo da não importação e seta o podeImportar como false;
     *
     * @param cc
     * @param detalheCC
     * @return true
     */
//    private boolean verificarNotaDetalhe(ConhecimentoCarga cc, Collection<DetalheCC> detalheCC) {
//        boolean b = true;
//
//        for (DetalheCC d : cc.getDetalhesCC()) {
//            for (DetalheCC det : detalheCC) {
//                if (d.getNuNF().equals(det.getNuNF())) {
//                    cc.setMotivoImportacao("Nota " + det.getNuNF() + " já importada");
//                    cc.setPodeImportar(false);
//                    b = false;
//                }
//            }
//        }
//        return b;
//    }
    public List<ConhecimentoDTO> getByRegiao(Regiao regiao, ClienteDTO cliente, Date dataInicio, Date dataFim) throws Exception {

        ConhecimentoDAO dao = new ConhecimentoDaoImpl();

        List<ConhecimentoDTO> conhecimentos = dao.getConhecimentosByRegiao(regiao, cliente, dataInicio, dataFim);

        return conhecimentos;
    }

    public List<ConhecimentoDTO> getByRegiao(Regiao regiao, Date dataInicio, Date dataFim) throws Exception {

        ConhecimentoDAO dao = new ConhecimentoDaoImpl();

        List<ConhecimentoDTO> conhecimentos = dao.getConhecimentosByRegiao(regiao, dataInicio, dataFim);

        return conhecimentos;
    }

    public List<ConhecimentoDTO> simularFreteFixo(List<ConhecimentoDTO> conhecimentos, double vlFrete) {

        for (ConhecimentoDTO cc : conhecimentos) {
            cc.setNovoFrete(vlFrete);
            cc.setVaiAlterar(true);
            
        }

        return conhecimentos;
    }
    
    public void updateFrete(List<ConhecimentoDTO> conhecimentoDTOs) throws Exception{
        
        ConhecimentoDAO dao = new ConhecimentoDaoImpl();
        
        dao.updateFretes(conhecimentoDTOs);
        
    }

}
