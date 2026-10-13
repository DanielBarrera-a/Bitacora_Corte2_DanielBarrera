package edu.dosw.wok.controller.mappers;

import edu.dosw.wok.controller.dtos.request.PlatoRequestDTO;
import edu.dosw.wok.controller.dtos.response.PlatoResponseDTO;
import edu.dosw.wok.core.models.Plato;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/*
 * =====================================================================
 *  MAPPER IN  (controller/mappers)  —  DTO  <->  Dominio
 * =====================================================================
 *  QUÉ ES
 *    Traduce entre lo que viaja por HTTP (RequestDTO / ResponseDTO) y el
 *    objeto de negocio (Plato). Lo usa el CONTROLLER, nunca el Service:
 *        Controller:  dto --toDomain--> Plato --service--> Plato --toResponse--> dto
 *    (guía controller, Paso 03 · S08 diapo 32 · S07 diapo 54)
 *
 *  CÓMO SE CREA (receta para cualquier entidad: Pedido, Mesa, ...)
 *    1. Crear una INTERFAZ (no clase) en controller/mappers: XxxMapper.
 *    2. Anotarla con @Mapper(componentModel = "spring").
 *         -> "spring" hace que MapStruct genere un @Component que se puede
 *            inyectar en el Controller con private final XxxMapper xxxMapper;
 *    3. Declarar SOLO las firmas de los métodos (sin cuerpo):
 *         Xxx toDomain(XxxRequestDTO dto);            // entra
 *         XxxResponseDTO toResponse(Xxx xxx);         // sale
 *         List<XxxResponseDTO> toResponseList(List<Xxx> lista);
 *    4. Los campos con el MISMO nombre y tipo se copian solos.
 *       Solo se anota con @Mapping lo que es distinto:
 *         @Mapping(target = "campo", ignore = true)          -> el origen no lo trae
 *         @Mapping(target = "campo", constant = "valor")     -> valor fijo
 *         @Mapping(source = "origen", target = "destino")    -> nombres distintos
 *         @Mapping(source = "mesa.numero", target = "numeroMesa") -> campo anidado
 *    5. Compilar (mvn compile). MapStruct genera XxxMapperImpl en
 *       target/generated-sources/annotations/. Si algo mapea raro, abrir ese
 *       archivo: es Java normal y se puede leer.
 *
 *  REQUISITOS EN EL pom.xml
 *    - Dependencia org.mapstruct:mapstruct.
 *    - annotationProcessorPaths en este ORDEN: lombok -> lombok-mapstruct-binding
 *      -> mapstruct-processor. MapStruct usa los getters/setters/builder que
 *      genera Lombok; si Lombok no corre primero, falla la compilación.
 *
 *  ERRORES TÍPICOS
 *    - "Unmapped target property: id": el destino tiene un campo que el origen
 *      no trae. Solución: @Mapping(target = "id", ignore = true).
 *    - "No property named X exists": el nombre en @Mapping está mal escrito.
 *    - No se genera el Impl: revisar el orden de los procesadores en el pom.
 * =====================================================================
 */
@Mapper(componentModel = "spring")
public interface PlatoMapper {

    // RequestDTO -> Plato
    // El Request no trae id (lo asigna la base de datos) ni disponible
    // (todo plato nuevo empieza disponible), por eso se anotan.
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "disponible", constant = "true")
    Plato toDomain(PlatoRequestDTO dto);

    // Plato -> ResponseDTO
    // Todos los campos tienen el mismo nombre y tipo: no necesita @Mapping.
    PlatoResponseDTO toResponse(Plato plato);

    // Lista: MapStruct genera el recorrido y llama a toResponse por cada elemento.
    List<PlatoResponseDTO> toResponseList(List<Plato> platos);
}
