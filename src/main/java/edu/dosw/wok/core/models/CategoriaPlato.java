package edu.dosw.wok.core.models;

/*
 * =====================================================================
 *  ENUM  (core/models)  —  CategoriaPlato
 * =====================================================================
 *  QUÉ ES
 *    Un tipo especial con un conjunto FIJO de constantes. Se usa para estados,
 *    roles o categorías (S07 diapo 47).
 *
 *  CÓMO SE USA
 *    - Como tipo de atributo: private CategoriaPlato categoria;
 *    - En JSON viaja como texto: "categoria": "WOK"
 *    - Si el cliente manda un valor que no existe ("PIZZA"), Spring responde 400.
 *    - Validación en el DTO: @NotNull (NO @NotBlank, que es solo para String).
 *
 *  ENUM CON COMPORTAMIENTO (se usa en EstadoPedido, etapa 4)
 *    Un enum puede tener métodos, por ejemplo la regla de transición de estados:
 *      public boolean puedeTransicionarA(EstadoPedido siguiente) { ... }
 *    (guía controller, Paso 01)
 * =====================================================================
 */
public enum CategoriaPlato {
    ENTRADA,
    WOK,
    BEBIDA,
    POSTRE
}
