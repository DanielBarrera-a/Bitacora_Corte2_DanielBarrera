package edu.dosw.wok.controller.dtos.request;

import edu.dosw.wok.core.models.CategoriaPlato;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * =====================================================================
 *  REQUEST DTO  (controller/dtos/request)  —  lo que ENTRA a la API
 * =====================================================================
 *  QUÉ ES
 *    El JSON que envía el cliente para crear o actualizar un plato.
 *    Aquí van las VALIDACIONES DE INPUT (formato). Las de negocio van en el
 *    Service/Validator. (guía controller, Pasos 02 y 05 · S08 diapos 54-56)
 *
 *  CÓMO SE CREA
 *    1. Clase en controller/dtos/request + Lombok (@Data, constructores, @Builder).
 *    2. Solo los campos que el cliente DEBE enviar. Aquí no van:
 *         id         -> lo asigna la base de datos
 *         disponible -> empieza en true (lo pone el Mapper con constant = "true")
 *    3. Encima de cada atributo, una anotación por regla (se apilan, NO se unen
 *       con &&), cada una con message = "..." (el texto que verá el cliente):
 *
 *         Anotación              Para qué                     Tipo
 *         @NotBlank              no null, no vacío            solo String
 *         @NotNull               no null                      cualquier tipo (números, enums)
 *         @NotEmpty              lista/texto no vacío         List, String
 *         @Positive              mayor que 0                  números
 *         @Min(1) / @Max(50)     rango                        números
 *         @Size(max = 100)       largo máximo                 String, List
 *         @Email / @Pattern      formato                      String
 *         @Future                fecha en el futuro           fechas
 *         (ninguna)              campo OPCIONAL
 *
 *    4. Imports: jakarta.validation.constraints.* (Alt + Enter en IntelliJ).
 *    5. Se ACTIVAN con @Valid en el Controller:
 *         public ResponseEntity<...> crear(@RequestBody @Valid PlatoRequestDTO dto)
 *       Si una regla falla, Spring lanza MethodArgumentNotValidException y el
 *       GlobalExceptionHandler responde 400 con los mensajes.
 * =====================================================================
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlatoRequestDTO {

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 100, message = "El nombre tiene máximo 100 caracteres")
    private String nombre;

    @NotNull(message = "El precio es obligatorio")
    @Positive(message = "El precio debe ser mayor a 0")
    private Double precio;

    // Opcional: sin anotaciones
    private String descripcion;

    // Enum -> @NotNull (no @NotBlank)
    @NotNull(message = "La categoría es obligatoria")
    private CategoriaPlato categoria;
}
