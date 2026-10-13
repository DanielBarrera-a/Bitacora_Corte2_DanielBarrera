package edu.dosw.wok.persistence.repositories;

import edu.dosw.wok.core.models.CategoriaPlato;
import edu.dosw.wok.persistence.entities.PlatoDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/*
 * =====================================================================
 *  REPOSITORY  (persistence/repositories)  —  acceso a MongoDB
 * =====================================================================
 *  QUÉ ES
 *    "La despensa": guarda y recupera datos. No tiene lógica de negocio.
 *    (S07 diapos 35 y 43 · S09 diapos 31-32 y 63-64)
 *
 *  CÓMO SE CREA
 *    1. INTERFAZ (no clase) en persistence/repositories.
 *    2. extends MongoRepository<TipoDocumento, TipoDelId>
 *         -> primero el Document, luego el tipo de su @Id (aquí String).
 *       (En SQL sería JpaRepository<XxxEntity, Long>.)
 *    3. Spring genera la implementación. Ya trae, sin escribir nada:
 *         save(doc)        guarda o actualiza (si el id existe, actualiza)
 *         findById(id)     Optional<Doc>
 *         findAll()        List<Doc>
 *         existsById(id)   boolean
 *         deleteById(id)   void
 *         count()          long
 *    4. Métodos extra por CONVENCIÓN DE NOMBRES (Spring arma la consulta):
 *         findBy<Campo>, existsBy<Campo>, countBy<Campo>, deleteBy<Campo>
 *         + IgnoreCase, And, Or, GreaterThan, LessThan, OrderBy<Campo>Asc ...
 *       El <Campo> debe llamarse IGUAL que el atributo del Document.
 *    5. Un repositorio por Document (S09 diapo 64).
 *    6. Spring lo encuentra gracias a @EnableMongoRepositories en WokApplication.
 *       Error típico si el paquete está mal: "No qualifying bean of type ...Repository".
 * =====================================================================
 */
public interface PlatoRepository extends MongoRepository<PlatoDocument, String> {

    // Consulta generada: { nombre: <nombre> } sin distinguir mayúsculas
    boolean existsByNombreIgnoreCase(String nombre);

    // Consulta generada: { disponible: true }
    List<PlatoDocument> findByDisponibleTrue();

    // Consulta generada: { categoria: <categoria> }
    List<PlatoDocument> findByCategoria(CategoriaPlato categoria);
}
