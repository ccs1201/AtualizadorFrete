/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.utils.exceptions;

import br.com.utils.TrataErro;

/**
 *
 * @author ti
 */
public final class ValidaDataException extends TrataErro{

    public ValidaDataException(String msgPersonalizada, String error) {
        super(msgPersonalizada, error);
    }
    
}
