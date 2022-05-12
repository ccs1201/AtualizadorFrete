/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.dao.jpa;

import br.com.entity.Regiao;
import br.com.utils.exceptions.DaoException;
import java.util.List;

/**
 *
 * @author ti
 */
public interface RegiaoDao extends DaoJpa<Regiao, Integer>{
    
    public List<Regiao> getAllEager(Class<Regiao> calasse) throws DaoException;
    
}
