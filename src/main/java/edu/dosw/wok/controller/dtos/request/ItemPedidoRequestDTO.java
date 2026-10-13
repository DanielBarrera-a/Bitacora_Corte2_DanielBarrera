package edu.dosw.wok.controller.dtos.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// Un ítem que envía el cliente: solo qué plato y cuántos.
// Nombre y precio NO los manda el cliente: el Service los toma del plato (precio congelado).
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemPedidoRequestDTO {

    @NotBlank(message = "El id del plato es obligatorio")
    private String platoId;

    @NotNull(message = "La cantidad es obligatoria")
    @Min(value = 1, message = "La cantidad mínima es 1")
    private Integer cantidad;
}
