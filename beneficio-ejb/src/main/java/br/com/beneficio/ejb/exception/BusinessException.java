package br.com.beneficio.ejb.exception;

import jakarta.ejb.ApplicationException;

@ApplicationException(rollback = true)
public class BusinessException extends Exception {

    public BusinessException(String message) {
        super(message);
    }
}