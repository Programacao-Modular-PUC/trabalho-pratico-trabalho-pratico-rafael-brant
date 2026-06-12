package br.pucminas.hospedagem.exception;

/**
 * Exceção lançada quando as datas de entrada e saída são inválidas.
 */
public class DataInvalidaException extends RuntimeException {

    public DataInvalidaException(String message) {
        super(message);
    }

    public DataInvalidaException(String message, Throwable cause) {
        super(message, cause);
    }
}
