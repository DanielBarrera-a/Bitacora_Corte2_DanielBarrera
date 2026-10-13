package edu.dosw.wok.core.models;

/*
 * =====================================================================
 *  ENUM CON COMPORTAMIENTO  (core/models)  —  EstadoPedido  (RF-04)
 * =====================================================================
 *  Flujo de cocina:  RECIBIDO -> EN_PREPARACION -> LISTO -> ENTREGADO
 *                    RECIBIDO -> CANCELADO
 *  La REGLA de transición vive en el enum (es parte del dominio): nadie más
 *  la conoce. El Service solo pregunta puedeTransicionarA(...) y, si es false,
 *  lanza ReglaDeNegocioException (422). (guía controller, Paso 01)
 * =====================================================================
 */
public enum EstadoPedido {
    RECIBIDO, EN_PREPARACION, LISTO, ENTREGADO, CANCELADO;

    public boolean puedeTransicionarA(EstadoPedido siguiente) {
        return switch (this) {
            case RECIBIDO       -> siguiente == EN_PREPARACION || siguiente == CANCELADO;
            case EN_PREPARACION -> siguiente == LISTO;
            case LISTO          -> siguiente == ENTREGADO;
            default             -> false;   // ENTREGADO y CANCELADO son estados finales
        };
    }
}
