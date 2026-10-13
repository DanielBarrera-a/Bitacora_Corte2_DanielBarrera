package edu.dosw.wok.persistence.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * =====================================================================
 *  SUBDOCUMENTO EMBEBIDO  —  ItemPedidoDocument
 * =====================================================================
 *  NO lleva @Document ni @Id: no es una colección propia, vive DENTRO de
 *  cada PedidoDocument (en el arreglo "items"). Ver PedidoDocument para la
 *  explicación de embebido vs referenciado.
 *
 *  platoId es una REFERENCIA (guarda el _id del plato como texto) y
 *  nombrePlato/precioUnitario son una COPIA (precio congelado).
 * =====================================================================
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemPedidoDocument {

    private String platoId;
    private String nombrePlato;
    private Double precioUnitario;
    private Integer cantidad;
}
