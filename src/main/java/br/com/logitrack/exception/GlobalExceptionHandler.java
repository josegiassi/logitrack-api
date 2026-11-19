package br.com.logitrack.exception;

import br.com.logitrack.dto.common.ErrorResponseDTO;
import br.com.logitrack.dto.common.ErrorResponseDTO.FieldErrorDTO;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

/**
 * Centraliza o tratamento de exceções e converte cada uma no código HTTP adequado,
 * sempre no mesmo formato (ErrorResponseDTO).
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    // ---------- 400 Bad Request ----------

    /** Falha nas anotações de validação do DTO (@NotBlank, @Size, @CPF...). */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDTO> handleValidation(MethodArgumentNotValidException ex, HttpServletRequest req) {
        List<FieldErrorDTO> campos = ex.getBindingResult().getFieldErrors().stream()
                .map(fe -> new FieldErrorDTO(fe.getField(), fe.getDefaultMessage()))
                .toList();
        return build(HttpStatus.BAD_REQUEST, "Dados de entrada inválidos", req, campos);
    }

    /** Validação de parâmetros de query/path (@Positive, @Min...). */
    @ExceptionHandler(HandlerMethodValidationException.class)
    public ResponseEntity<ErrorResponseDTO> handleMethodValidation(HandlerMethodValidationException ex, HttpServletRequest req) {
        List<FieldErrorDTO> campos = ex.getParameterValidationResults().stream()
                .flatMap(r -> r.getResolvableErrors().stream()
                        .map(e -> new FieldErrorDTO(r.getMethodParameter().getParameterName(), e.getDefaultMessage())))
                .toList();
        return build(HttpStatus.BAD_REQUEST, "Parâmetros inválidos", req, campos);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponseDTO> handleConstraint(ConstraintViolationException ex, HttpServletRequest req) {
        List<FieldErrorDTO> campos = ex.getConstraintViolations().stream()
                .map(v -> new FieldErrorDTO(v.getPropertyPath().toString(), v.getMessage()))
                .toList();
        return build(HttpStatus.BAD_REQUEST, "Parâmetros inválidos", req, campos);
    }

    /** JSON malformado ou valor incompatível com o tipo (ex.: enum inexistente, data inválida). */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponseDTO> handleNotReadable(HttpMessageNotReadableException ex, HttpServletRequest req) {
        String msg = "Corpo da requisição inválido ou malformado";
        if (ex.getCause() instanceof InvalidFormatException ife && !ife.getPath().isEmpty()) {
            String campo = ife.getPath().get(ife.getPath().size() - 1).getFieldName();
            msg = "Valor '" + ife.getValue() + "' inválido para o campo '" + campo + "'";
            if (ife.getTargetType() != null && ife.getTargetType().isEnum()) {
                msg += ". Valores aceitos: " + Arrays.toString(ife.getTargetType().getEnumConstants());
            }
        }
        return build(HttpStatus.BAD_REQUEST, msg, req, null);
    }

    /** Tipo errado em parâmetro de path/query (ex.: /motoristas/abc ou status=XPTO). */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponseDTO> handleTypeMismatch(MethodArgumentTypeMismatchException ex, HttpServletRequest req) {
        String msg = "Valor '" + ex.getValue() + "' inválido para o parâmetro '" + ex.getName() + "'";
        Class<?> tipo = ex.getRequiredType();
        if (tipo != null && tipo.isEnum()) {
            msg += ". Valores aceitos: " + Arrays.toString(tipo.getEnumConstants());
        }
        return build(HttpStatus.BAD_REQUEST, msg, req, null);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ErrorResponseDTO> handleMissingParam(MissingServletRequestParameterException ex, HttpServletRequest req) {
        return build(HttpStatus.BAD_REQUEST, "Parâmetro obrigatório ausente: " + ex.getParameterName(), req, null);
    }

    /** Campo de ordenação inexistente na entidade (?sort=campoQueNaoExiste). */
    @ExceptionHandler(PropertyReferenceException.class)
    public ResponseEntity<ErrorResponseDTO> handlePropertyReference(PropertyReferenceException ex, HttpServletRequest req) {
        return build(HttpStatus.BAD_REQUEST, "Campo de ordenação inválido: " + ex.getPropertyName(), req, null);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponseDTO> handleBadRequest(BadRequestException ex, HttpServletRequest req) {
        return build(HttpStatus.BAD_REQUEST, ex.getMessage(), req, null);
    }

    // ---------- 404 Not Found ----------

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleNotFound(ResourceNotFoundException ex, HttpServletRequest req) {
        return build(HttpStatus.NOT_FOUND, ex.getMessage(), req, null);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleNoResource(NoResourceFoundException ex, HttpServletRequest req) {
        return build(HttpStatus.NOT_FOUND, "Rota não encontrada: " + req.getRequestURI(), req, null);
    }

    // ---------- 405 / 415 ----------

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorResponseDTO> handleMethodNotSupported(HttpRequestMethodNotSupportedException ex, HttpServletRequest req) {
        return build(HttpStatus.METHOD_NOT_ALLOWED, "Método " + ex.getMethod() + " não suportado nesta rota", req, null);
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<ErrorResponseDTO> handleMediaType(HttpMediaTypeNotSupportedException ex, HttpServletRequest req) {
        return build(HttpStatus.UNSUPPORTED_MEDIA_TYPE, "Content-Type não suportado. Use application/json", req, null);
    }

    // ---------- 409 Conflict ----------

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ErrorResponseDTO> handleConflict(ConflictException ex, HttpServletRequest req) {
        return build(HttpStatus.CONFLICT, ex.getMessage(), req, null);
    }

    /** Última barreira: violação de UNIQUE/FK detectada pelo próprio banco. */
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponseDTO> handleDataIntegrity(DataIntegrityViolationException ex, HttpServletRequest req) {
        log.warn("Violação de integridade: {}", ex.getMostSpecificCause().getMessage());
        return build(HttpStatus.CONFLICT, "Operação viola uma restrição de integridade do banco de dados", req, null);
    }

    // ---------- 422 Unprocessable Entity ----------

    @ExceptionHandler(BusinessRuleException.class)
    public ResponseEntity<ErrorResponseDTO> handleBusinessRule(BusinessRuleException ex, HttpServletRequest req) {
        return build(HttpStatus.UNPROCESSABLE_ENTITY, ex.getMessage(), req, null);
    }

    // ---------- 500 Internal Server Error ----------

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> handleGeneric(Exception ex, HttpServletRequest req) {
        log.error("Erro inesperado em {} {}", req.getMethod(), req.getRequestURI(), ex);
        return build(HttpStatus.INTERNAL_SERVER_ERROR, "Erro interno no servidor. Tente novamente mais tarde.", req, null);
    }

    private ResponseEntity<ErrorResponseDTO> build(HttpStatus status, String message, HttpServletRequest req,
                                                   List<FieldErrorDTO> fieldErrors) {
        ErrorResponseDTO body = new ErrorResponseDTO(
                LocalDateTime.now(),
                status.value(),
                status.getReasonPhrase(),
                message,
                req.getRequestURI(),
                fieldErrors
        );
        return ResponseEntity.status(status).body(body);
    }
}
