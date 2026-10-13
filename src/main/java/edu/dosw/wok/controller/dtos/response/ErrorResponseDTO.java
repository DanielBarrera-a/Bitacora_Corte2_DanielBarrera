package edu.dosw.wok.controller.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/*
 *  ERROR RESPONSE DTO  —  formato UNIFORME de todos los errores de la API
 *  (S08 diapo 61 · guía controller, Paso 7.2)
 *  Ejemplo:
 *    { "timestamp": "...", "status": 404, "error": "Not Found",
 *      "message": "No existe Plato con id=99", "path": "/api/v1/platos/99" }
 *  Lo construye el GlobalExceptionHandler.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ErrorResponseDTO {

    private LocalDateTime timestamp;
    private int status;
    private String error;
    private String message;
    private String path;
}
