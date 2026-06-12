package br.pucminas.hospedagem.exception;

/**
 * Exceção lançada quando a capacidade máxima de hóspedes é excedida.
 */
public class CapacidadeExcedidaException extends RuntimeException {

    public CapacidadeExcedidaException(String message) {
        super(message);
    }

    public CapacidadeExcedidaException(String message, Throwable cause) {
        super(message, cause);
    }
}
