package edu.dosw.wok.persistence.entities;

import edu.dosw.wok.core.models.EstadoPedido;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/*
 * =====================================================================
 *  DOCUMENT CON SUBDOCUMENTOS EMBEBIDOS  —  colección "pedidos"
 * =====================================================================
 *  ASÍ SE VE EN MONGO
 *    {
 *      _id: ObjectId("..."),
 *      mesa: 5,                                   <- @Field("mesa")
 *      estado: "RECIBIDO",                        <- enum como texto
 *      fechaCreacion: ISODate("..."),
 *      items: [                                   <- EMBEBIDO: arreglo dentro del documento
 *        { platoId: "6ace...", nombrePlato: "Wok de pollo", precioUnitario: 28000, cantidad: 2 }
 *      ]
 *    }
 *
 *  EMBEBIDO vs REFERENCIADO  (S09 diapos 51-53) — pregunta típica de parcial
 *    EMBEBIDO: los datos relacionados van DENTRO del documento.
 *      Usar si: relación 1 a pocos, se consultan siempre juntos, no cambian.
 *      Ventaja: una sola lectura trae todo.  Desventaja: puede duplicar datos.
 *      -> Los ítems de un pedido: siempre se leen con el pedido y no cambian.
 *    REFERENCIADO: documentos separados unidos por un id.
 *      Usar si: muchos a muchos, datos grandes o que se reutilizan/cambian.
 *      Ventaja: sin duplicación.  Desventaja: varias consultas.
 *      -> El plato: vive en su colección "platos"; el ítem guarda solo su platoId.
 *    Este diseño es HÍBRIDO: ítems embebidos + referencia al plato.
 *
 *  ANOTACIONES
 *    @Document(collection = "pedidos")  nombre de la colección
 *    @Id                                 se guarda como _id (ObjectId si es String y llega null)
 *    @Field("mesa")                      el atributo se llama distinto en Mongo
 *    (sin anotación)                     se guarda con el mismo nombre del atributo
 *    Listas de otras clases (sin @Document) -> se guardan embebidas automáticamente.
 * =====================================================================
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "pedidos")
public class PedidoDocument {

    @Id
    private String id;

    @Field("mesa")
    private Integer numeroMesa;

    @Builder.Default
    private List<ItemPedidoDocument> items = new ArrayList<>();

    private EstadoPedido estado;
    private LocalDateTime fechaCreacion;
}
