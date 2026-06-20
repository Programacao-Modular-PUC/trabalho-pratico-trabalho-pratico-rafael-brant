package br.pucminas.hospedagem.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DataInvalidaException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleDataInvalida(DataInvalidaException e) {
        return Map.of("erro", e.getMessage());
    }

    @ExceptionHandler(CapacidadeExcedidaException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleCapacidadeExcedida(CapacidadeExcedidaException e) {
        return Map.of("erro", e.getMessage());
    }

    @ExceptionHandler(QuartoIndisponivelException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public Map<String, String> handleQuartoIndisponivel(QuartoIndisponivelException e) {
        return Map.of("erro", e.getMessage());
    }

    @ExceptionHandler(RecursoNaoPermitidoException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleRecursoNaoPermitido(RecursoNaoPermitidoException e) {
        return Map.of("erro", e.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleIllegalArgument(IllegalArgumentException e) {
        return Map.of("erro", e.getMessage());
    }

    @ExceptionHandler(IllegalStateException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleIllegalState(IllegalStateException e) {
        return Map.of("erro", e.getMessage());
    }
}