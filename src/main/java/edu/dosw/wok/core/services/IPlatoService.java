package edu.dosw.wok.core.services;

import edu.dosw.wok.core.models.CategoriaPlato;
import edu.dosw.wok.core.models.Plato;

import java.util.List;

/*
 * =====================================================================
 *  SERVICE — INTERFAZ  (core/services)  —  el CONTRATO de la lógica de Plato
 * =====================================================================
 *  QUÉ ES
 *    "La cocina": aquí vive la lógica de negocio. La interfaz dice QUÉ se puede
 *    hacer; la implementación (PlatoServiceImpl) dice CÓMO.
 *    (S07 diapo 42 · S08 diapos 26-27 · guía controller, Paso 04)
 *
 *  REGLAS
 *    - Recibe y devuelve objetos de DOMINIO (Plato). NUNCA DTOs ni Documents.
 *    - No conoce HTTP (nada de ResponseEntity, códigos, @RequestBody).
 *    - El Controller depende de esta INTERFAZ, no de la implementación
 *      (inversión de dependencias, S07 diapo 76).
 *    - Si otro Service (PedidoService) necesita platos, inyecta IPlatoService.
 *
 *  CÓMO SE CREA
 *    1. Interfaz IXxxService en core/services con las firmas de cada funcionalidad.
 *    2. Clase XxxServiceImpl implements IXxxService con @Service.
 * =====================================================================
 */
public interface IPlatoService {

    List<Plato> obtenerTodos();

    List<Plato> obtenerDisponibles();

    List<Plato> obtenerPorCategoria(CategoriaPlato categoria);

    Plato obtenerPorId(String id);

    Plato crear(Plato plato);

    Plato actualizar(String id, Plato nuevosDatos);

    Plato cambiarDisponibilidad(String id, boolean disponible);

    void eliminar(String id);
}
