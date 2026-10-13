package edu.dosw.wok.persistence.mappers;

import edu.dosw.wok.core.models.ItemPedido;
import edu.dosw.wok.core.models.Pedido;
import edu.dosw.wok.persistence.entities.ItemPedidoDocument;
import edu.dosw.wok.persistence.entities.PedidoDocument;
import org.mapstruct.Mapper;

import java.util.List;

/*
 * =====================================================================
 *  MAPPER OUT con LISTAS ANIDADAS  —  Pedido <-> PedidoDocument
 * =====================================================================
 *  MapStruct ve que Pedido.items es List<ItemPedido> y PedidoDocument.items
 *  es List<ItemPedidoDocument>. Para convertir cada elemento busca en esta
 *  misma interfaz un método ItemPedido -> ItemPedidoDocument (y al revés),
 *  por eso se declaran toItemDocument / toItemDomain. Sin ellos, MapStruct
 *  intenta generarlos solo, pero declararlos deja claro el mapeo.
 *
 *  getTotal() y getSubtotal() del dominio se ven como propiedades "total" y
 *  "subtotal"; el Document no las tiene, así que se ignoran (no se guardan:
 *  se calculan cada vez).
 * =====================================================================
 */
@Mapper(componentModel = "spring")
public interface PedidoDocumentMapper {

    PedidoDocument toDocument(Pedido pedido);

    Pedido toDomain(PedidoDocument document);

    ItemPedidoDocument toItemDocument(ItemPedido item);

    ItemPedido toItemDomain(ItemPedidoDocument item);

    List<Pedido> toDomainList(List<PedidoDocument> documents);
}
