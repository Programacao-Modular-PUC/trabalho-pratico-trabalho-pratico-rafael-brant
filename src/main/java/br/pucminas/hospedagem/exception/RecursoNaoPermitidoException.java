package br.pucminas.hospedagem.exception;

/**
 * Exceção lançada quando um recurso não é permitido para determinado tipo de quarto.
 * Exemplo: berço em quarto individual.
 */
public class RecursoNaoPermitidoException extends RuntimeException {

    public RecursoNaoPermitidoException(String message) {
        super(message);
    }

    public RecursoNaoPermitidoException(String message, Throwable cause) {
        super(message, cause);
    }
}
