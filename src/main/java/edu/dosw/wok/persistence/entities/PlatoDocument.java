package edu.dosw.wok.persistence.entities;

import edu.dosw.wok.core.models.CategoriaPlato;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/*
 * =====================================================================
 *  DOCUMENT  (persistence/entities)  —  cómo se guarda Plato en MongoDB
 * =====================================================================
 *  QUÉ ES
 *    Representa la estructura en la base de datos. NO tiene lógica de negocio
 *    (eso está en core/models/Plato). Es el equivalente Mongo de @Entity.
 *    (S09 diapos 61-62 · S07 diapo 50)
 *
 *  ¿POR QUÉ NO GUARDAR Plato DIRECTAMENTE? (S09 diapo 44)
 *    Responsabilidades distintas: Plato tiene comportamiento (activar, ...);
 *    PlatoDocument tiene anotaciones de persistencia. Si se mezclan, cambiar la
 *    BD obliga a tocar la lógica de negocio.
 *
 *  CÓMO SE CREA
 *    1. Clase en persistence/entities con Lombok (@Data, constructores, @Builder).
 *    2. @Document(collection = "nombre_coleccion")  -> la colección en Mongo.
 *    3. @Id sobre el id (org.springframework.data.annotation.Id).
 *       Si es String y llega null, Mongo genera un ObjectId al guardar
 *       (S09 diapo 76).
 *    4. Los demás atributos se guardan con el mismo nombre que tengan aquí.
 * =====================================================================
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "platos")
public class PlatoDocument {

    @Id
    private String id;
    private String nombre;
    private String descripcion;
    private Double precio;
    private CategoriaPlato categoria;   // Mongo lo guarda como texto: "WOK", "BEBIDA", ...
    private Boolean disponible;
}
