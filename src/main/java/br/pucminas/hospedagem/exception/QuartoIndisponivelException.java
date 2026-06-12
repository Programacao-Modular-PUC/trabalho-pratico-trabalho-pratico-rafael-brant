package br.pucminas.hospedagem.exception;

/**
 * Exceção lançada quando um quarto não está disponível para as datas solicitadas.
 */
public class QuartoIndisponivelException extends RuntimeException {

    public QuartoIndisponivelException(String message) {
        super(message);
    }

    public QuartoIndisponivelException(String message, Throwable cause) {
        super(message, cause);
    }
}
