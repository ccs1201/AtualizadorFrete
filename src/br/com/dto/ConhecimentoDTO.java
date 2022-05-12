package br.com.dto;

import br.com.entity.Regiao;
import java.util.Date;

/**
 *
 * @author Cleber
 */
public class ConhecimentoDTO {

    private int id;
    private Date dataCC;
    private ClienteDTO cliente;
    private double vlCC;
    private double vlFrete;
    private boolean alterouFrete;
    private boolean vaiAlterar;
    private double novoFrete;
    private DestinatarioDTO destinatario;
    private Regiao regiao;

    public boolean isVaiAlterar() {
        return vaiAlterar;
    }

    public void setVaiAlterar(boolean vaiAlterar) {
        this.vaiAlterar = vaiAlterar;
    }

    public boolean isAlterouFrete() {
        return alterouFrete;
    }

    public void setAlterouFrete(boolean alterouFrete) {
        this.alterouFrete = alterouFrete;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDataCC() {
        return dataCC;
    }

    public void setDataCC(Date dataCC) {
        this.dataCC = dataCC;
    }

    public ClienteDTO getCliente() {
        if (this.cliente == null) {
            cliente = new ClienteDTO();
        }
        return cliente;
    }

    public void setCliente(ClienteDTO cliente) {
        this.cliente = cliente;
    }

    public double getVlCC() {
        return vlCC;
    }

    public void setVlCC(double vlCC) {
        this.vlCC = vlCC;
    }

    public double getVlFrete() {
        return vlFrete;
    }

    public void setVlFrete(double vlFrete) {
        this.vlFrete = vlFrete;
    }

    public double getNovoFrete() {
        return novoFrete;
    }

    public void setNovoFrete(double novoFrete) {
        this.novoFrete = novoFrete;
    }

    public DestinatarioDTO getDestinatario() {
        if (this.destinatario == null) {
            this.destinatario = new DestinatarioDTO();
        }
        return destinatario;

    }

    public void setDestinatario(DestinatarioDTO destinatario) {
        this.destinatario = destinatario;
    }

    public Regiao getRegiao() {
        return regiao;
    }

    public void setRegiao(Regiao regiao) {
        this.regiao = regiao;
    }

    public String toString() {

        String str = id + " | " + cliente.getId() + " | " + vlCC + " | " + vlFrete;
        return str;
    }
}
