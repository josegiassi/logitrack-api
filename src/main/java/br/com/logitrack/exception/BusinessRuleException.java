package br.com.logitrack.exception;

/**
 * Dados sintaticamente válidos, mas que violam uma regra de negócio -> HTTP 422.
 * Ex.: peso acima da capacidade do veículo, CNH incompatível com o veículo.
 */
public class BusinessRuleException extends RuntimeException {

    public BusinessRuleException(String message) {
        super(message);
    }
}
