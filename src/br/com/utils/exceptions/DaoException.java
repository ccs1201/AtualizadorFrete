/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.utils.exceptions;

/**
 *
 * @author ti
 */
public class DaoException extends Exception {

    public DaoException(Exception detailException, String customMessage) {

        super(customMessage + "\n" + "Detalhes do erro: " + detailException.getMessage());

    }
}
