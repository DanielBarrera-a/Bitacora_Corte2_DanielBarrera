package edu.dosw.wok.core.exceptions;

// 422 Unprocessable Entity: el request está bien formado pero una regla del
// negocio lo rechaza (pedir un plato no disponible, transición de estado inválida,
// más de 6 ingredientes en un wok). Ver la explicación en RecursoNoEncontradoException.
public class ReglaDeNegocioException extends RuntimeException {

    public ReglaDeNegocioException(String mensaje) {
        super(mensaje);
    }
}
