package edu.dosw.wok.core.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * =====================================================================
 *  DOMINIO  (core/models)  —  Plato
 * =====================================================================
 *  QUÉ ES
 *    Objeto de NEGOCIO. Representa un plato de la carta de Wok System.
 *    No depende de Spring, de la BD ni de los DTOs: Java puro + Lombok.
 *    (guía controller, Paso 01 · S07 diapo 49 · S08 diapo 10)
 *
 *  CÓMO SE CREA
 *    1. Clase en core/models.
 *    2. Lombok:
 *         @Data               -> getters, setters, equals, hashCode, toString
 *         @NoArgsConstructor  -> new Plato()
 *         @AllArgsConstructor -> constructor con todos los campos
 *         @Builder            -> Plato.builder().nombre("X").precio(1.0).build()
 *       (Importar no basta: las anotaciones van ENCIMA de la clase.)
 *    3. Atributos SIEMPRE private (encapsulamiento).
 *       id es String porque en Mongo el id es un ObjectId (S09 diapo 76).
 *    4. Métodos de COMPORTAMIENTO: lo que el objeto sabe hacer sobre su propio
 *       estado (estaDisponible, activar, desactivar). No reciben parámetros
 *       que no necesitan.
 *
 *  QUÉ NO VA AQUÍ
 *    - Validaciones de formato (precio > 0, nombre no vacío): van en el
 *      RequestDTO con @Positive / @NotBlank. Repetirlas crea dos fuentes de verdad.
 *    - Reglas que consultan otros datos ("¿el nombre ya existe?"): van en el
 *      Validator (core/validators).
 * =====================================================================
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Plato {

    private String id;
    private String nombre;
    private String descripcion;
    private Double precio;
    private CategoriaPlato categoria;
    private Boolean disponible;

    // Boolean (objeto) puede ser null: Boolean.TRUE.equals(...) evita NullPointerException
    public boolean estaDisponible(){
        return Boolean.TRUE.equals(disponible);
    }

    // RF-01: la carta muestra si el plato se puede pedir
    public void activar(){
        this.disponible = true;
    }

    public void desactivar(){
        this.disponible = false;
    }
}
