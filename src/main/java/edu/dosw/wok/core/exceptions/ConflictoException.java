package edu.dosw.wok.core.exceptions;

// 409 Conflict: el recurso ya existe (nombre duplicado) o hay conflicto de estado
// (mesa con cuenta abierta). Ver la explicación en RecursoNoEncontradoException.
public class ConflictoException extends RuntimeException {

    public ConflictoException(String mensaje) {
        super(mensaje);
    }
}
