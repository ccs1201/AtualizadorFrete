/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.controller;

import br.com.dao.jpa.RegiaoDao;
import br.com.dao.jpa.impl.RegiaoDaoJpaImpl;
import br.com.entity.Regiao;
import br.com.utils.exceptions.DaoException;
import java.util.List;

/**
 *
 * @author ti
 */
public class RegiaoController {

    private Regiao regiao;
    private List<Regiao> regioes;

    public RegiaoController() {

        regiao = new Regiao();

    }

    public Regiao getRegiao() {
        return regiao;
    }

    public List<Regiao> getRegioes() throws DaoException {

        this.getAll();

        return regioes;
    }

    private void getAll() throws DaoException {

        RegiaoDao dao = new RegiaoDaoJpaImpl();
        regioes = dao.getAll(Regiao.class);

    }
    
    public List<Regiao> getAllEager() throws DaoException{
        RegiaoDao dao = new RegiaoDaoJpaImpl();
        
        return dao.getAllEager(Regiao.class);
        
    }
    
    
}
