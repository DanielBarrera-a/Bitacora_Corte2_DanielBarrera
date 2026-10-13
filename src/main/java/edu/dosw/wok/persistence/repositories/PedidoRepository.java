package edu.dosw.wok.persistence.repositories;

import edu.dosw.wok.core.models.EstadoPedido;
import edu.dosw.wok.persistence.entities.PedidoDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

/*
 * =====================================================================
 *  REPOSITORY  —  consultas de Pedido en MongoDB
 * =====================================================================
 *  DOS FORMAS DE CONSULTAR (ver también PlatoRepository)
 *
 *  1) POR NOMBRE DE MÉTODO (derived queries): Spring traduce el nombre.
 *       findByEstado(e)                 -> { estado: e }
 *       findByEstadoIn(lista)           -> { estado: { $in: lista } }
 *       countByEstado(e)                -> cuenta
 *       findByItemsPlatoId(id)          -> { "items.platoId": id }   (campo de un subdocumento)
 *       findByNumeroMesaOrderByFechaCreacionDesc(n) -> filtra y ordena
 *     Palabras: And, Or, In, Not, GreaterThan, LessThan, Between, True/False,
 *               IgnoreCase, OrderBy<Campo>Asc/Desc
 *     OJO: se usa el nombre del ATRIBUTO Java (numeroMesa), no el de @Field("mesa").
 *
 *  2) @Query con JSON de Mongo, cuando el nombre se vuelve muy largo o la
 *     consulta no es trivial (S09 diapo 33). ?0, ?1... son los parámetros en orden.
 *       @Query("{ 'mesa': ?0, 'estado': { $ne: 'ENTREGADO' } }")
 *     Aquí SÍ se usa el nombre real en Mongo ("mesa").
 *     Operadores: $eq $ne $gt $gte $lt $lte $in $nin $and $or
 * =====================================================================
 */
public interface PedidoRepository extends MongoRepository<PedidoDocument, String> {

    // Tablero de cocina (RF-03/RF-04): pedidos en un estado
    List<PedidoDocument> findByEstado(EstadoPedido estado);

    // Pedidos en varios estados a la vez (ej. RECIBIDO y EN_PREPARACION)
    List<PedidoDocument> findByEstadoIn(List<EstadoPedido> estados);

    long countByEstado(EstadoPedido estado);

    // Consulta dentro del arreglo embebido: pedidos que incluyen un plato
    List<PedidoDocument> findByItemsPlatoId(String platoId);

    // Historial de una mesa, del más reciente al más antiguo
    List<PedidoDocument> findByNumeroMesaOrderByFechaCreacionDesc(Integer numeroMesa);

    // @Query: pedidos activos (no entregados ni cancelados) de una mesa
    @Query("{ 'mesa': ?0, 'estado': { $nin: ['ENTREGADO', 'CANCELADO'] } }")
    List<PedidoDocument> buscarActivosDeMesa(Integer numeroMesa);
}
