/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.dao.jpa.impl;

import br.com.utils.exceptions.DaoException;
import br.com.utils.JPAUtil;
import br.com.dao.jpa.DaoJpa;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

/**
 *
 * @author ti
 * @param <T>
 * @param <I>
 */
public abstract class DaoJpaImpl<T, I extends Serializable> implements DaoJpa<T, I> {

    @Override
    public T persist(T entity) throws DaoException {
        T saved = null;
        EntityManager em = JPAUtil.getEntityManager();
        try {

            em.getTransaction().begin();
            em.merge(entity);
            em.getTransaction().commit();

        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new DaoException(e,
                    "Erro ao gravar dados no banco de dados. Tipo de Objeto: "
                    + entity.getClass().getSimpleName());
        } finally {
            em.close();
        }

        return saved;
    }

    @Override
    public void remove(T entity) throws DaoException {

        EntityManager em = JPAUtil.getEntityManager();

        try {

            em.getTransaction().begin();
            entity = em.merge(entity);
            em.remove(entity);
            em.getTransaction().commit();

        } catch (Exception e) {

            em.getTransaction().rollback();
            throw new DaoException(e, "Erro ao excluir registro do banco de dados. Tipo de Obejto: "
                    + entity.getClass().getSimpleName());

        } finally {

            em.close();
        }
    }

    @Override
    public T getById(Class<T> classe, I pk) throws DaoException {
        EntityManager em = JPAUtil.getEntityManager();

        try {

            return em.find(classe, pk);

        } catch (NoResultException e) {

            throw new DaoException(e, "Nehum registro localizado");

        } finally {

            em.close();
        }

    }

    @SuppressWarnings("unchecked")
    @Override
    public List<T> getAll(Class<T> classe) throws DaoException {
        EntityManager em = JPAUtil.getEntityManager();
        List<T> list = null;
        try {
            list = em.createQuery(
                    "select o from " + classe.getSimpleName() + " o")
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
