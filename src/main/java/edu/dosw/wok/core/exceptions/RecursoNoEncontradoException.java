package edu.dosw.wok.core.exceptions;

/*
 * =====================================================================
 *  EXCEPCIONES PROPIAS  (core/exceptions)
 * =====================================================================
 *  QUÉ SON
 *    Errores de NEGOCIO con nombre propio. El Service/Validator las lanza y el
 *    GlobalExceptionHandler las convierte en una respuesta HTTP uniforme.
 *    (S08 diapo 60 · guía controller, Paso 7.1)
 *
 *  CÓMO SE CREAN
 *    1. Clase que extiende RuntimeException (no obliga a try/catch).
 *    2. Constructor que arma el mensaje y llama a super(mensaje).
 *    3. Una clase por tipo de error, cada una con su código HTTP en el handler:
 *         RecursoNoEncontradoException -> 404  no existe
 *         ConflictoException           -> 409  duplicado / conflicto de estado
 *         ReglaDeNegocioException      -> 422  el formato está bien pero el negocio lo rechaza
 *    4. Uso: throw new RecursoNoEncontradoException("Plato", id);
 * =====================================================================
 */
public class RecursoNoEncontradoException extends RuntimeException {

    public RecursoNoEncontradoException(String recurso, String id) {
        super("No existe " + recurso + " con id=" + id);
    }
}
