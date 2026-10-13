package edu.dosw.wok.controller.dtos.response;

import edu.dosw.wok.core.models.CategoriaPlato;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * =====================================================================
 *  RESPONSE DTO  (controller/dtos/response)  —  lo que SALE de la API
 * =====================================================================
 *  QUÉ ES
 *    El JSON que la API devuelve. Controla exactamente qué ve el cliente:
 *    nunca se devuelve el objeto de dominio directamente.
 *    (guía controller, Paso 02 · S07 diapo 45)
 *
 *  CÓMO SE CREA
 *    1. Clase en controller/dtos/response + Lombok.
 *       @Data es necesario: Spring usa los getters para convertir a JSON.
 *    2. Solo los campos que el cliente necesita ver (aquí sí va el id, para
 *       poder consultar, editar o borrar después).
 *    3. SIN validaciones: estos datos los produce el sistema, no el cliente.
 *    4. Se llena con el Mapper IN: platoMapper.toResponse(plato).
 * =====================================================================
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlatoResponseDTO {

    private String id;
    private String nombre;
    private String descripcion;
    private Double precio;
    private CategoriaPlato categoria;
    private Boolean disponible;      // RF-01: la carta muestra la disponibilidad
}
