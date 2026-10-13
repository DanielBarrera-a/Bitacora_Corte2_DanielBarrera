package edu.dosw.wok.controller.dtos.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/*
 *  REQUEST DTO con una LISTA de otros DTOs
 *    @NotEmpty -> la lista no puede venir vacía (un pedido sin ítems no tiene sentido)
 *    @Valid    -> SOBRE la lista: hace que también se validen las reglas de cada
 *                 ItemPedidoRequestDTO. Sin @Valid aquí, las anotaciones de los
 *                 ítems se ignoran.
 *  Ejemplo de JSON:
 *    { "numeroMesa": 5, "items": [ { "platoId": "6ace...", "cantidad": 2 } ] }
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PedidoRequestDTO {

    @NotNull(message = "El número de mesa es obligatorio")
    @Positive(message = "El número de mesa debe ser mayor a 0")
    private Integer numeroMesa;

    @NotEmpty(message = "El pedido debe tener al menos un ítem")
    @Valid
    private List<ItemPedidoRequestDTO> items;
}
