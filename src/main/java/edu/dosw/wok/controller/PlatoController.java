package edu.dosw.wok.controller;

import edu.dosw.wok.controller.dtos.request.PlatoRequestDTO;
import edu.dosw.wok.controller.dtos.response.ErrorResponseDTO;
import edu.dosw.wok.controller.dtos.response.PlatoResponseDTO;
import edu.dosw.wok.controller.mappers.PlatoMapper;
import edu.dosw.wok.core.models.CategoriaPlato;
import edu.dosw.wok.core.models.Plato;
import edu.dosw.wok.core.services.IPlatoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/*
 * =====================================================================
 *  CONTROLLER  (controller)  —  endpoints REST de Plato  —  "el mesero"
 * =====================================================================
 *  QUÉ ES
 *    Recibe la petición HTTP, traduce con el Mapper, delega al Service y
 *    responde con el código correcto. NO tiene lógica de negocio.
 *    (S07 diapo 40 · S08 diapos 13-18 y 67-72 · guía controller, Paso 06)
 *
 *  CÓMO SE CREA
 *    1. @RestController            -> lo que devuelve se convierte en JSON.
 *    2. @RequestMapping("/api/v1/<recurso-en-plural>")   -> siempre con versión.
 *    3. @RequiredArgsConstructor + private final IXxxService y XxxMapper.
 *       (Se inyecta la INTERFAZ del Service.)
 *    4. Un método por endpoint, SIEMPRE con la misma forma:
 *         a) recibir   (@RequestBody @Valid dto / @PathVariable / @RequestParam)
 *         b) mapper.toDomain(dto)
 *         c) service.metodo(dominio)
 *         d) mapper.toResponse(resultado)
 *         e) ResponseEntity con el código correcto
 *
 *  ANOTACIONES DE ENDPOINT
 *    @GetMapping            leer                 -> 200 ResponseEntity.ok(body)
 *    @PostMapping           crear                -> 201 ResponseEntity.status(CREATED).body(...)
 *    @PutMapping("/{id}")   reemplazar completo  -> 200
 *    @PatchMapping(...)     cambio parcial       -> 200
 *    @DeleteMapping("/{id}") eliminar            -> 204 ResponseEntity.noContent().build()
 *    @PathVariable String id      -> dato de la ruta:   /platos/{id}
 *    @RequestParam boolean x      -> query param:       /platos/1/disponibilidad?disponible=false
 *    @RequestBody @Valid Dto dto  -> JSON del body, validado (si falla -> 400)
 *
 *  SWAGGER (S08 diapos 35-42 · guía Paso 08)
 *    @Tag(name, description)          agrupa los endpoints en la UI
 *    @Operation(summary = "...")      describe un endpoint
 *    @ApiResponses({@ApiResponse(responseCode = "201", description = "...") , ...})
 *                                     documenta los códigos que puede devolver
 *    UI: http://localhost:8080/swagger-ui/index.html   (springdoc lo genera solo)
 * =====================================================================
 */
@RestController
@RequestMapping("/api/v1/platos")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Platos", description = "Carta de Wok System: crear, consultar y administrar platos")
public class PlatoController {

    private final IPlatoService platoService;
    private final PlatoMapper platoMapper;

    // GET /api/v1/platos  -> todos
    @GetMapping
    @Operation(summary = "Listar todos los platos")
    public ResponseEntity<List<PlatoResponseDTO>> obtenerTodos() {
        return ResponseEntity.ok(platoMapper.toResponseList(platoService.obtenerTodos()));
    }

    // GET /api/v1/platos/disponibles  -> RF-01, la carta que ve el cliente
    @GetMapping("/disponibles")
    @Operation(summary = "Consultar la carta (solo platos disponibles)")
    public ResponseEntity<List<PlatoResponseDTO>> obtenerDisponibles() {
        return ResponseEntity.ok(platoMapper.toResponseList(platoService.obtenerDisponibles()));
    }

    // GET /api/v1/platos/categoria/WOK
    @GetMapping("/categoria/{categoria}")
    @Operation(summary = "Listar platos por categoría")
    public ResponseEntity<List<PlatoResponseDTO>> obtenerPorCategoria(@PathVariable CategoriaPlato categoria) {
        return ResponseEntity.ok(platoMapper.toResponseList(platoService.obtenerPorCategoria(categoria)));
    }

    // GET /api/v1/platos/{id}
    @GetMapping("/{id}")
    @Operation(summary = "Obtener un plato por id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Plato encontrado"),
            @ApiResponse(responseCode = "404", description = "No existe el plato",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    public ResponseEntity<PlatoResponseDTO> obtenerPorId(@PathVariable String id) {
        return ResponseEntity.ok(platoMapper.toResponse(platoService.obtenerPorId(id)));
    }

    // POST /api/v1/platos  -> 201
    @PostMapping
    @Operation(summary = "Crear un plato")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Plato creado"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))),
            @ApiResponse(responseCode = "409", description = "Ya existe un plato con ese nombre",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    public ResponseEntity<PlatoResponseDTO> crear(@RequestBody @Valid PlatoRequestDTO dto) {
        log.info("POST /api/v1/platos nombre={}", dto.getNombre());
        Plato plato = platoMapper.toDomain(dto);            // a-b) DTO -> dominio
        Plato creado = platoService.crear(plato);           // c)   lógica
        return ResponseEntity.status(HttpStatus.CREATED)    // e)   201, no 200
                .body(platoMapper.toResponse(creado));      // d)   dominio -> DTO
    }

    // PUT /api/v1/platos/{id}
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un plato")
    public ResponseEntity<PlatoResponseDTO> actualizar(@PathVariable String id,
                                                       @RequestBody @Valid PlatoRequestDTO dto) {
        Plato actualizado = platoService.actualizar(id, platoMapper.toDomain(dto));
        return ResponseEntity.ok(platoMapper.toResponse(actualizado));
    }

    // PATCH /api/v1/platos/{id}/disponibilidad?disponible=false
    @PatchMapping("/{id}/disponibilidad")
    @Operation(summary = "Marcar un plato como disponible o agotado")
    public ResponseEntity<PlatoResponseDTO> cambiarDisponibilidad(@PathVariable String id,
                                                                  @RequestParam boolean disponible) {
        return ResponseEntity.ok(platoMapper.toResponse(platoService.cambiarDisponibilidad(id, disponible)));
    }

    // DELETE /api/v1/platos/{id}  -> 204 sin body
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un plato")
    public ResponseEntity<Void> eliminar(@PathVariable String id) {
        platoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
