package edu.dosw.wok.core.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * =====================================================================
 *  DOMINIO  (core/models)  —  ItemPedido: una línea del pedido
 * =====================================================================
 *  Regla (S08 diapo 8): el ítem CONGELA el nombre y el precio del plato en el
 *  momento del pedido. Si mañana el plato sube de precio, los pedidos viejos
 *  no cambian. Por eso guarda nombrePlato y precioUnitario, no solo platoId.
 *
 *  getSubtotal() es comportamiento propio. Se llama "get..." para que Spring
 *  lo muestre en el JSON y MapStruct lo copie como la propiedad "subtotal".
 * =====================================================================
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemPedido {

    private String platoId;
    private String nombrePlato;
    private Double precioUnitario;
    private Integer cantidad;

    public double getSubtotal() {
        return precioUnitario * cantidad;
    }
}
