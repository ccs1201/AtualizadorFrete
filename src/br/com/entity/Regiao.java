/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.entity;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;

/**
 *
 * @author ti
 */
@Entity
public class Regiao implements Serializable {

    @Id
    private int pk;
    private String nome;
    @OneToMany
    @JoinTable(name = "municipioregiao")
    @JoinColumn(name = "cdmunicipio")
    private List<Municipio> municipios;

    public int getPk() {
        return pk;
    }

    public void setPk(int pk) {
        this.pk = pk;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Municipio> getMunicipios() {
        return municipios;
    }

    public void setMunicipios(List<Municipio> municipios) {
        this.municipios = municipios;
    }

    public void addMunicipio(Municipio m) {
        municipios.add(m);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + this.pk;
        hash = 47 * hash + Objects.hashCode(this.nome);
        hash = 47 * hash + Objects.hashCode(this.municipios);
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
        final Regiao other = (Regiao) obj;
        if (this.pk != other.pk) {
            return false;
        }
        if (!Objects.equals(this.nome, other.nome)) {
            return false;
        }
        if (!Objects.equals(this.municipios, other.municipios)) {
            return false;
        }
        return true;
    }
    
    @Override
    public  String toString(){
        return nome;
    }

}
