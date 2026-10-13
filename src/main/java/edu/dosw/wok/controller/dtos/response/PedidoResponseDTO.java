package edu.dosw.wok.controller.dtos.response;

import edu.dosw.wok.core.models.EstadoPedido;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

// Lo que sale: el pedido con sus ítems, el estado y el total calculado.
// total viene de Pedido.getTotal() (MapStruct lo copia como propiedad "total").
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PedidoResponseDTO {

    private String id;
    private Integer numeroMesa;
    private List<ItemPedidoResponseDTO> items;
    private EstadoPedido estado;
    private Double total;
    private LocalDateTime fechaCreacion;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ItemPedidoResponseDTO {
        private String platoId;
        private String nombrePlato;
        private Double precioUnitario;
        private Integer cantidad;
        private Double subtotal;
    }
}
