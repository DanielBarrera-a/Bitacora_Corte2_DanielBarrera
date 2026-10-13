package edu.dosw.wok.persistence.mappers;

import edu.dosw.wok.core.models.Plato;
import edu.dosw.wok.persistence.entities.PlatoDocument;
import org.mapstruct.Mapper;

import java.util.List;

/*
 * =====================================================================
 *  MAPPER OUT  (persistence/mappers)  —  Dominio  <->  Document
 * =====================================================================
 *  QUÉ ES
 *    Traduce entre el objeto de negocio (Plato) y su forma en MongoDB
 *    (PlatoDocument). Lo usa el SERVICE (o la capa de persistencia) al hablar
 *    con el Repository:
 *        Service:  Plato --toDocument--> PlatoDocument --repository.save--> Mongo
 *                  Mongo --repository.find--> PlatoDocument --toDomain--> Plato
 *    (S09 diapos 36-37 y 65 · prerrequisitos del parcial, sección 2)
 *
 *  DIFERENCIA CON EL MAPPER IN
 *    - Mapper IN  (controller/mappers):  DTO      <-> Dominio  (lado del cliente)
 *    - Mapper OUT (persistence/mappers): Document <-> Dominio  (lado de la BD)
 *    Así el Controller nunca ve un Document y el Service nunca ve un DTO.
 *
 *  CÓMO SE CREA
 *    Igual que el Mapper IN: interfaz + @Mapper(componentModel = "spring")
 *    y solo las firmas. Aquí los dos lados tienen exactamente los mismos
 *    campos (incluido id), así que no hace falta ningún @Mapping.
 * =====================================================================
 */
@Mapper(componentModel = "spring")
public interface PlatoDocumentMapper {

    // Plato -> PlatoDocument (antes de guardar)
    PlatoDocument toDocument(Plato plato);

    // PlatoDocument -> Plato (después de leer)
    Plato toDomain(PlatoDocument document);

    List<Plato> toDomainList(List<PlatoDocument> documents);
}
