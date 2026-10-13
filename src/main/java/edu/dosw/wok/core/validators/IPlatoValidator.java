package edu.dosw.wok.core.validators;

/*
 * =====================================================================
 *  VALIDATOR  (core/validators)  —  reglas de NEGOCIO de Plato
 * =====================================================================
 *  QUÉ ES
 *    Verifica reglas que dependen del estado del sistema ("¿ya existe un plato
 *    con ese nombre?"). Las de formato van en el DTO. Si una regla falla,
 *    lanza una excepción propia. (S07 diapo 52 · S08 diapo 57 · guía Paso 05)
 *
 *  CÓMO SE CREA
 *    1. Interfaz IXxxValidator con un método void por regla: validarAlgo(...).
 *    2. Clase XxxValidator implements IXxxValidator con @Component.
 *    3. Se inyecta en el ServiceImpl por la INTERFAZ.
 *    ¿Por qué interfaz si hay una sola implementación? Para poder mockearla en
 *    las pruebas del Service (@Mock IPlatoValidator).
 *    Cada dominio tiene su propio Validator (PedidoValidator, MesaValidator...).
 * =====================================================================
 */
public interface IPlatoValidator {

    // Lanza ConflictoException (409) si ya existe un plato con ese nombre
    void validarNombreUnico(String nombre);
}
