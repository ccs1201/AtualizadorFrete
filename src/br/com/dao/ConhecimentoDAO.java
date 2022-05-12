package br.com.dao;

import br.com.dto.ClienteDTO;
import br.com.dto.ConhecimentoDTO;
import br.com.entity.Regiao;
import br.com.model.ConhecimentoCarga;
import br.com.model.DetalheCC;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Cleber
 */
public interface ConhecimentoDAO {

    List<ConhecimentoDTO> atualizarFretePadrão(Date DataInicio, Date dataFim, ClienteDTO cliente, double vlrFixo, double vlrLImite, double percentual) throws Exception;

    List<ConhecimentoDTO> atualizarFretePadrão(Date DataInicio, Date dataFim, ClienteDTO cliente, double vlrFixo, double vlrLImite, double percentual, String cnpjDestinatario) throws Exception;

    List<ConhecimentoDTO> findByData(Date inicio, Date fim, ClienteDTO clienteDTO) throws Exception;

    List<ConhecimentoDTO> findByData(Date inicio, Date fim, ClienteDTO clienteDTO, String destinatario) throws Exception;

    void salvar(List<ConhecimentoCarga> conhecimentosCarga) throws Exception;

    List<ConhecimentoDTO> simularFretePorDestinatarioFixoIntervaloValor(Date inicio, Date fim, ClienteDTO clienteDTO, String destinatario, double vlFrete, double vlrInicial, double vlrFinal) throws Exception;

    List<ConhecimentoDTO> simularFretePorDestinatarioFixoLimite(Date inicio, Date fim, ClienteDTO clienteDTO, String destinatario, double vlFrete, double vlrLimite) throws Exception;

    List<ConhecimentoDTO> simularFretePorDestinatarioPercentualIntervaloValor(Date inicio, Date fim, ClienteDTO clienteDTO, String destinatario, double percentualFrete, double vlrInicial, double vlrFinal) throws Exception;

    List<ConhecimentoDTO> simularFretePorDestinatarioPercentualLimiteValor(Date inicio, Date fim, ClienteDTO clienteDTO, String destinatario, double percentualFrete, double vlrLimite) throws Exception;

    void updateFretes(List<ConhecimentoDTO> conhecimentos) throws Exception;

    boolean isNFJaExiste(Date data, int idCliente, DetalheCC detalheCC) throws Exception;

    ConhecimentoCarga verificaConhecimentoDestinatarioDia(ConhecimentoCarga cc) throws Exception;

    void salvar(ConhecimentoCarga cc) throws Exception;

    /**
     * Receuprar lista de conhecimentosDTO pelo cliente expecifico.
     * @param regiao
     * @param cliente
     * @param dataInicio
     * @param dataFim
     * @return
     * @throws Exception
     */
    public List<ConhecimentoDTO> getConhecimentosByRegiao(Regiao regiao, ClienteDTO cliente,
            Date dataInicio, Date dataFim) throws Exception;

    /**
     * Recupera lista de ConhecimentoDTO da região idenpendente do cliete.
     * @param regiao
     * @param dataInicio
     * @param dataFim
     * @return
     * @throws Exception
     */
    public List<ConhecimentoDTO> getConhecimentosByRegiao(Regiao regiao,
            Date dataInicio, Date dataFim) throws Exception;

}
