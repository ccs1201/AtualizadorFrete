package br.com.model;

import br.com.dto.ClienteDTO;
import br.com.dto.DestinatarioDTO;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

/**
 *
 * @author Cleber
 */
public class ConhecimentoCarga {

    private int id;
    private Date dataCC;
    private double vlcc;
    private double vlFrete;
    private int qtVol;
    private String notas;
    private boolean importado;
    private String horaCC;
    private ClienteDTO clienteDTO;
    private DestinatarioDTO destinatarioDTO;
    private Collection<DetalheCC> detalhesCC;
    private String obs;
    private String motivoImportacao;
    private boolean podeImportar;

    /**
     * Nunca alterar o ID no construtor da classe pois irá gerar problema com o
     * metodo insert do DAO que, o usa para determinar se a operação deve ser de
     * INSERT ou UPDATE.
     */
    public ConhecimentoCarga() {
        this.detalhesCC = new HashSet<DetalheCC>();
        this.importado = false;
        this.id = 0;
        this.podeImportar = true;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ClienteDTO getClienteDTO() {
        return clienteDTO;
    }

    public void setClienteDTO(ClienteDTO clienteDTO) {
        this.clienteDTO = clienteDTO;
    }

    public Date getDataCC() {
        return dataCC;
    }

    public void setDataCC(Date dataCC) {
        this.dataCC = dataCC;
    }

    public DestinatarioDTO getDestinatarioDTO() {
        return destinatarioDTO;
    }

    public void setDestinatarioDTO(DestinatarioDTO destinatarioDTO) {
        this.destinatarioDTO = destinatarioDTO;
    }

    public String getHoraCC() {
        return horaCC;
    }

    public void setHoraCC(String horaCC) {
        this.horaCC = horaCC;
    }

    public boolean isImportado() {
        return importado;
    }

    public void setImportado(boolean importado) {
        this.importado = importado;
    }

    public String getNotas() {
        return notas;
    }

    public void setNotas(String notas) {
        this.notas = notas;
    }

    public int getQtVol() {
        return qtVol;
    }

    public void setQtVol(int qtVol) {
        this.qtVol = qtVol;
    }

    public double getVlFrete() {
        return vlFrete;
    }

    public void setVlFrete(double vlFrete) {
        this.vlFrete = vlFrete;
    }

    public double getVlcc() {
        return vlcc;
    }

    public void setVlcc(double vlcc) {
        this.vlcc = vlcc;
    }

    public Collection<DetalheCC> getDetalhesCC() {
        return detalhesCC;
    }

    public void setDetalhesCC(List<DetalheCC> detalheCCs) {
        this.detalhesCC = detalheCCs;
    }

    public String getObs() {
        return obs;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }

    public String getMotivoImportacao() {
        return motivoImportacao;
    }

    public void setMotivoImportacao(String motivoImportacao) {
        this.motivoImportacao = motivoImportacao;
    }

    public boolean isPodeImportar() {
        return podeImportar;
    }

    public void setPodeImportar(boolean podeImportar) {
        this.podeImportar = podeImportar;
    }

    /**
     * Adicona um detalhe ao conhecimento e cria a String com o numero das notas
     * para setar e na variavel notas.
     *
     * @param detalhe O detalhe que será adiconado a lista.
     */
    public void setDetalheCC(DetalheCC detalhe) {
        this.detalhesCC.add(detalhe);
        if (this.detalhesCC.size() > 1) {
            this.notas = notas.concat("/ " + detalhe.getNuNF());
        } else {
            this.notas = "" + detalhe.getNuNF();
        }
    }

    /**
     * Recebe um novo conhecimento e une seus dados com o do conhecimento
     * existente.
     * <p>
     * <b>Sempre deve receber o conhecimento novo como parametro e nunca o
     * conhecimento existente.</b>
     * <p>
     *
     * @param novoCC O conhecimento que sera unido ao existente.
     */
    public void mergeConhecimentos(ConhecimentoCarga novoCC) {

        Collection<DetalheCC> detalhes = novoCC.getDetalhesCC();

        for (DetalheCC detalhe : detalhes) {

            this.setDetalheCC(detalhe);
        }
    }

    public void removerDetalhe(DetalheCC detalheCC) {
        this.detalhesCC.remove(detalheCC);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + this.id;
        hash = 31 * hash + (this.dataCC != null ? this.dataCC.hashCode() : 0);
        hash = 31 * hash + (int) (Double.doubleToLongBits(this.vlcc) ^ (Double.doubleToLongBits(this.vlcc) >>> 32));
        hash = 31 * hash + (int) (Double.doubleToLongBits(this.vlFrete) ^ (Double.doubleToLongBits(this.vlFrete) >>> 32));
        hash = 31 * hash + this.qtVol;
        hash = 31 * hash + (this.notas != null ? this.notas.hashCode() : 0);
        hash = 31 * hash + (this.importado ? 1 : 0);
        hash = 31 * hash + (this.horaCC != null ? this.horaCC.hashCode() : 0);
        hash = 31 * hash + (this.clienteDTO != null ? this.clienteDTO.hashCode() : 0);
        hash = 31 * hash + (this.destinatarioDTO != null ? this.destinatarioDTO.hashCode() : 0);
        hash = 31 * hash + (this.detalhesCC != null ? this.detalhesCC.hashCode() : 0);
        hash = 31 * hash + (this.obs != null ? this.obs.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ConhecimentoCarga other = (ConhecimentoCarga) obj;
        if (this.id != other.id) {
            return false;
        }
        if (this.dataCC != other.dataCC && (this.dataCC == null || !this.dataCC.equals(other.dataCC))) {
            return false;
        }
        if (Double.doubleToLongBits(this.vlcc) != Double.doubleToLongBits(other.vlcc)) {
            return false;
        }
        if (Double.doubleToLongBits(this.vlFrete) != Double.doubleToLongBits(other.vlFrete)) {
            return false;
        }
        if (this.qtVol != other.qtVol) {
            return false;
        }
        if ((this.notas == null) ? (other.notas != null) : !this.notas.equals(other.notas)) {
            return false;
        }
        if (this.importado != other.importado) {
            return false;
        }
        if ((this.horaCC == null) ? (other.horaCC != null) : !this.horaCC.equals(other.horaCC)) {
            return false;
        }
        if (this.clienteDTO != other.clienteDTO && (this.clienteDTO == null || !this.clienteDTO.equals(other.clienteDTO))) {
            return false;
        }
        if (this.destinatarioDTO != other.destinatarioDTO && (this.destinatarioDTO == null || !this.destinatarioDTO.equals(other.destinatarioDTO))) {
            return false;
        }
        if (this.detalhesCC != other.detalhesCC && (this.detalhesCC == null || !this.detalhesCC.equals(other.detalhesCC))) {
            return false;
        }
        if ((this.obs == null) ? (other.obs != null) : !this.obs.equals(other.obs)) {
            return false;
        }
        return true;
    }
}
