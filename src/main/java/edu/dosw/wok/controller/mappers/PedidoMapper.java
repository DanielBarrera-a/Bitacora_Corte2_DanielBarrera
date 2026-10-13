package edu.dosw.wok.controller.mappers;

import edu.dosw.wok.controller.dtos.request.ItemPedidoRequestDTO;
import edu.dosw.wok.controller.dtos.request.PedidoRequestDTO;
import edu.dosw.wok.controller.dtos.response.PedidoResponseDTO;
import edu.dosw.wok.core.models.ItemPedido;
import edu.dosw.wok.core.models.Pedido;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/*
 *  MAPPER IN de Pedido (ver la receta completa en PlatoMapper)
 *  Lo nuevo aquí: el Request no trae varios campos que pone el sistema,
 *  así que se ignoran con @Mapping(..., ignore = true).
 *  Para las listas de ítems, MapStruct usa los métodos toItem / toItemResponse.
 */
@Mapper(componentModel = "spring")
public interface PedidoMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "estado", ignore = true)
    @Mapping(target = "fechaCreacion", ignore = true)
    Pedido toDomain(PedidoRequestDTO dto);

    @Mapping(target = "nombrePlato", ignore = true)       // lo pone el Service
    @Mapping(target = "precioUnitario", ignore = true)    // lo pone el Service (precio congelado)
    ItemPedido toItem(ItemPedidoRequestDTO dto);

    PedidoResponseDTO toResponse(Pedido pedido);

    PedidoResponseDTO.ItemPedidoResponseDTO toItemResponse(ItemPedido item);

    List<PedidoResponseDTO> toResponseList(List<Pedido> pedidos);
}
