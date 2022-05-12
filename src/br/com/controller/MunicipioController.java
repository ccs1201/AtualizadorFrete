/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.controller;

import br.com.entity.Municipio;
import br.com.utils.exceptions.DaoException;
import java.util.List;
import br.com.dao.jpa.MunicipioDAO;
import br.com.dao.jpa.impl.MunicipioDaoJpaImpl;

/**
 *
 * @author Cleber C. de Souza
 */
public class MunicipioController {
    
    private Municipio municipio;
    
    public MunicipioController() {
        municipio = new Municipio();
    }
    
    public Municipio getMunicipio() {
        return municipio;
    }
    
    public List<Municipio> getAll() throws DaoException {
        
        List<Municipio> municipios;
        
        MunicipioDAO dao = new MunicipioDaoJpaImpl();
        
        municipios = dao.getAll(Municipio.class);
        
        return municipios;
        
    }
    
    public void persist(Municipio municipio) throws DaoException {
        
        MunicipioDAO dao = new MunicipioDaoJpaImpl();
        dao.persist(municipio);
    }
    
    public Municipio getByID(int id) throws DaoException {
        
        MunicipioDAO dao = new MunicipioDaoJpaImpl();
        
        this.municipio = dao.getById(Municipio.class, id);
        
        return municipio;
    }
    
    public void remove(Municipio municipio) throws DaoException {
        
        MunicipioDAO dao = new MunicipioDaoJpaImpl();
        
        dao.remove(municipio);
        
    }
}
