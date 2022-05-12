/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author ti
 */
@Entity
@Table(name = "MUNICIPIO")
@NamedQueries({
    @NamedQuery(name = "Municipio.findAll", query = "SELECT m FROM Municipio m")})
public class Municipio implements Serializable {
    
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "PK")
    private Integer pk;
    @Basic(optional = false)
    @Column(name = "NOME")
    private String nome;
    @Basic(optional = false)
    @Column(name = "UF")
    private String uf;
    @Basic(optional = false)
    @Column(name = "ORDEMENTREGA")
    private int ordementrega;
    @Basic(optional = false)
    @Column(name = "DISTANCIASEDEKM")
    private int distanciasedekm;
    @Column(name = "VLFRETE")
    private BigDecimal vlfrete;
    @Column(name = "PERCENTUALFRETE")
    private BigDecimal percentualfrete;
    @Column(name = "LIMITEVLFRETE")
    private BigDecimal limitevlfrete;
    @ManyToOne
    @JoinTable(name = "municipioregiao")
    @JoinColumn(name = "cdregiao")
    private Regiao regiao;
    
    public Municipio() {
    }
    
    public Municipio(Integer pk) {
        this.pk = pk;
    }
    
    public Municipio(Integer pk, String nome, String uf, int ordementrega, int distanciasedekm) {
        this.pk = pk;
        this.nome = nome;
        this.uf = uf;
        this.ordementrega = ordementrega;
        this.distanciasedekm = distanciasedekm;
    }
    
    public Integer getPk() {
        return pk;
    }
    
    public void setPk(Integer pk) {
        this.pk = pk;
    }
    
    public String getNome() {
        return nome;
    }
    
    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public String getUf() {
        return uf;
    }
    
    public void setUf(String uf) {
        this.uf = uf;
    }
    
    public int getOrdementrega() {
        return ordementrega;
    }
    
    public void setOrdementrega(int ordementrega) {
        this.ordementrega = ordementrega;
    }
    
    public int getDistanciasedekm() {
        return distanciasedekm;
    }
    
    public void setDistanciasedekm(int distanciasedekm) {
        this.distanciasedekm = distanciasedekm;
    }
    
    public BigDecimal getVlfrete() {
        return vlfrete;
    }
    
    public void setVlfrete(BigDecimal vlfrete) {
        this.vlfrete = vlfrete;
    }
    
    public BigDecimal getPercentualfrete() {
        return percentualfrete;
    }
    
    public void setPercentualfrete(BigDecimal percentualfrete) {
        this.percentualfrete = percentualfrete;
    }
    
    public BigDecimal getLimitevlfrete() {
        return limitevlfrete;
    }
    
    public void setLimitevlfrete(BigDecimal limitevlfrete) {
        this.limitevlfrete = limitevlfrete;
    }
    
    public Regiao getRegiao() {
        return regiao;
    }
    
    public void setRegiao(Regiao regiao) {
        this.regiao = regiao;
        regiao.addMunicipio(this);
    }
    
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 47 * hash + Objects.hashCode(this.pk);
        hash = 47 * hash + Objects.hashCode(this.nome);
        hash = 47 * hash + Objects.hashCode(this.uf);
        hash = 47 * hash + this.ordementrega;
        hash = 47 * hash + this.distanciasedekm;
        hash = 47 * hash + Objects.hashCode(this.vlfrete);
        hash = 47 * hash + Objects.hashCode(this.percentualfrete);
        hash = 47 * hash + Objects.hashCode(this.limitevlfrete);
        hash = 47 * hash + Objects.hashCode(this.regiao);
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
        final Municipio other = (Municipio) obj;
        if (!Objects.equals(this.pk, other.pk)) {
            return false;
        }
        if (!Objects.equals(this.nome, other.nome)) {
            return false;
        }
        if (!Objects.equals(this.uf, other.uf)) {
            return false;
        }
        if (this.ordementrega != other.ordementrega) {
            return false;
        }
        if (this.distanciasedekm != other.distanciasedekm) {
            return false;
        }
        if (!Objects.equals(this.vlfrete, other.vlfrete)) {
            return false;
        }
        if (!Objects.equals(this.percentualfrete, other.percentualfrete)) {
            return false;
        }
        if (!Objects.equals(this.limitevlfrete, other.limitevlfrete)) {
            return false;
        }
        if (!Objects.equals(this.regiao, other.regiao)) {
            return false;
        }
        return true;
    }
    
    @Override
    public String toString() {
        return nome;
    }
    
}
