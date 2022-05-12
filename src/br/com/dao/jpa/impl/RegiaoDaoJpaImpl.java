/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.dao.jpa.impl;

import br.com.dao.jpa.RegiaoDao;
import br.com.entity.Regiao;
import br.com.utils.exceptions.DaoException;
import br.com.utils.JPAUtil;
import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author ti
 */
public class RegiaoDaoJpaImpl extends DaoJpaImpl<Regiao, Integer> implements RegiaoDao {

    
    public List<Regiao> getAllEager(Class<Regiao> calasse) throws DaoException {
        
        EntityManager em = JPAUtil.getEntityManager();
        List<Regiao> list = null;
        try {
            list = em.createQuery(
                    "select o from " + Regiao.class.getSimpleName() + " o join fetch o.municipios")
                    .getResultList();
        } catch (Exception e) {
            throw new DaoException(e, "Erro ao perquisar. Tipo de Objeto: "
                    + Regiao.class.getSimpleName());
        } finally {
            em.close();
        }

        return list;
    }
    
    @Override
    public List<Regiao> getAll(Class<Regiao> classe) throws DaoException {
        EntityManager em = JPAUtil.getEntityManager();
        List<Regiao> list = null;
        try {
            list = em.createQuery(
                    "select o from " + classe.getSimpleName() + " o ORDER BY o.nome")
                    .getResultList();
        } catch (Exception e) {
            throw new DaoException(e, "Erro ao perquisar. Tipo de Objeto: "
                    + classe.getSimpleName());
        } finally {
            em.close();
        }

        return list;

    }
    
}
