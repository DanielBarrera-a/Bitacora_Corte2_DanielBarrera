package edu.dosw.wok.core.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/*
 * =====================================================================
 *  DOMINIO  (core/models)  —  Pedido  (RF-02, RF-03, RF-04)
 * =====================================================================
 *  Un pedido de una mesa con varios ítems y un estado de cocina.
 *
 *  COMPORTAMIENTO PROPIO
 *    getTotal()       -> suma de subtotales con Streams (mapToDouble + sum)
 *    esModificable()  -> solo en RECIBIDO se pueden cambiar ítems
 *                        (análisis crítico del Lab 3: después de EN_PREPARACION no)
 *    cambiarEstado()  -> asigna el nuevo estado (la VALIDACIÓN la hace el Service
 *                        con estado.puedeTransicionarA)
 *
 *  @Builder.Default: con @Builder, los valores iniciales de los atributos se
 *  pierden si no se usa esta anotación. Así items empieza como lista vacía.
 * =====================================================================
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Pedido {

    private String id;
    private Integer numeroMesa;
    @Builder.Default
    private List<ItemPedido> items = new ArrayList<>();
    private EstadoPedido estado;
    private LocalDateTime fechaCreacion;

    public double getTotal() {
        return items.stream()
                .mapToDouble(ItemPedido::getSubtotal)
                .sum();
    }

    public boolean esModificable() {
        return estado == EstadoPedido.RECIBIDO;
    }

    public void cambiarEstado(EstadoPedido nuevo) {
        this.estado = nuevo;
    }
}
