/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.dao.jpa;

import br.com.utils.exceptions.DaoException;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author ti
 * @param <T> Entity
 * @param <I> ID
 */
public interface DaoJpa<T, I extends Serializable> {

    public T persist(T entity) throws DaoException;

    public void remove(T entity) throws DaoException;

    public T getById(Class<T> classe, I pk) throws DaoException;

    public List<T> getAll(Class<T> classe) throws DaoException;

}
