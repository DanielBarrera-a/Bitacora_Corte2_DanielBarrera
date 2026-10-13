package edu.dosw.wok.controller.handlers;

import edu.dosw.wok.controller.dtos.response.ErrorResponseDTO;
import edu.dosw.wok.core.exceptions.ConflictoException;
import edu.dosw.wok.core.exceptions.RecursoNoEncontradoException;
import edu.dosw.wok.core.exceptions.ReglaDeNegocioException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

/*
 * =====================================================================
 *  GLOBAL EXCEPTION HANDLER  (controller/handlers)  —  "el gerente"
 * =====================================================================
 *  QUÉ ES
 *    Intercepta TODAS las excepciones de TODOS los controllers y responde con
 *    el mismo formato (ErrorResponseDTO) y el código HTTP correcto. Evita llenar
 *    los controllers de try/catch. (S08 diapos 58-64 · guía controller, Paso 7.3)
 *
 *  CÓMO SE CREA
 *    1. Clase con @RestControllerAdvice.
 *    2. Un método por tipo de excepción, anotado con @ExceptionHandler(Xxx.class),
 *       que devuelve ResponseEntity<ErrorResponseDTO> con el status adecuado.
 *    3. El de Exception.class (500) va AL FINAL: Spring elige el handler más
 *       específico, el genérico solo atrapa lo que nadie más atrapó.
 *
 *  TABLA DE CÓDIGOS (S08 diapo 18)
 *    400 Bad Request          formato mal (falló @Valid o JSON inválido)
 *    404 Not Found            el recurso no existe
 *    409 Conflict             duplicado / conflicto de estado
 *    422 Unprocessable Entity formato bien, el negocio lo rechaza
 *    500 Internal Server Err  error inesperado
 * =====================================================================
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(RecursoNoEncontradoException.class)
    public ResponseEntity<ErrorResponseDTO> handleNotFound(RecursoNoEncontradoException ex,
                                                           HttpServletRequest request) {
        log.warn("No encontrado: {}", ex.getMessage());
        return build(HttpStatus.NOT_FOUND, ex.getMessage(), request);
    }

    @ExceptionHandler(ConflictoException.class)
    public ResponseEntity<ErrorResponseDTO> handleConflicto(ConflictoException ex,
                                                            HttpServletRequest request) {
        return build(HttpStatus.CONFLICT, ex.getMessage(), request);
    }

    @ExceptionHandler(ReglaDeNegocioException.class)
    public ResponseEntity<ErrorResponseDTO> handleNegocio(ReglaDeNegocioException ex,
                                                          HttpServletRequest request) {
        return build(HttpStatus.UNPROCESSABLE_ENTITY, ex.getMessage(), request);
    }

    // 400: falló alguna validación del RequestDTO (@NotBlank, @Positive...)
    // Junta los mensajes de cada campo: "nombre: El nombre es obligatorio, precio: ..."
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDTO> handleValidacion(MethodArgumentNotValidException ex,
                                                             HttpServletRequest request) {
        String mensaje = ex.getBindingResult().getFieldErrors().stream()
                .map(e -> e.getField() + ": " + e.getDefaultMessage())
                .collect(Collectors.joining(", "));
        return build(HttpStatus.BAD_REQUEST, mensaje, request);
    }

    // 400: el JSON está mal escrito o trae un valor de enum que no existe ("categoria": "PIZZA")
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponseDTO> handleJsonInvalido(HttpMessageNotReadableException ex,
                                                               HttpServletRequest request) {
        return build(HttpStatus.BAD_REQUEST, "El cuerpo de la petición no es válido", request);
    }

    // 500: SIEMPRE al final
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> handleGenerico(Exception ex, HttpServletRequest request) {
        log.error("Error inesperado: {}", ex.getMessage(), ex);
        return build(HttpStatus.INTERNAL_SERVER_ERROR, "Error inesperado del servidor", request);
    }

    private ResponseEntity<ErrorResponseDTO> build(HttpStatus status, String mensaje,
                                                   HttpServletRequest request) {
        ErrorResponseDTO body = ErrorResponseDTO.builder()
                .timestamp(LocalDateTime.now())
                .status(status.value())
                .error(status.getReasonPhrase())
                .message(mensaje)
                .path(request.getRequestURI())
                .build();
        return ResponseEntity.status(status).body(body);
    }
}
