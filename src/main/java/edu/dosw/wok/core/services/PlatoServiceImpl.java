package edu.dosw.wok.core.services;

import edu.dosw.wok.core.exceptions.RecursoNoEncontradoException;
import edu.dosw.wok.core.models.CategoriaPlato;
import edu.dosw.wok.core.models.Plato;
import edu.dosw.wok.core.validators.IPlatoValidator;
import edu.dosw.wok.persistence.mappers.PlatoDocumentMapper;
import edu.dosw.wok.persistence.repositories.PlatoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/*
 * =====================================================================
 *  SERVICE — IMPLEMENTACIÓN  (core/services)  —  lógica de Plato con MongoDB
 * =====================================================================
 *  CÓMO SE CREA
 *    1. @Service              -> Spring la crea y la inyecta donde se pida IPlatoService.
 *    2. @RequiredArgsConstructor + campos "private final" -> inyección por constructor
 *       (IoC: no se usa "new", Spring entrega las dependencias. S07 diapo 84).
 *    3. @Slf4j                -> crea la variable "log" (S08 diapos 43-48).
 *         log.info  flujo normal · log.warn algo raro · log.error fallo
 *    4. Dependencias que usa:
 *         PlatoRepository       -> hablar con Mongo (trabaja con PlatoDocument)
 *         PlatoDocumentMapper   -> Mapper OUT: Plato <-> PlatoDocument
 *         IPlatoValidator       -> reglas de negocio (por interfaz, para mockear)
 *
 *  PATRÓN DE CADA MÉTODO
 *    Leer:     repository.findXxx() -> documentMapper.toDomain(...) -> Plato
 *    Guardar:  validar -> documentMapper.toDocument(plato) -> repository.save(doc)
 *              -> documentMapper.toDomain(guardado)   (el id lo asigna Mongo)
 *
 *  STREAMS (S08 diapos 28-31) — siguen sirviendo con BD:
 *    .stream().map(...).toList()                       transformar
 *    .stream().filter(...).toList()                    filtrar
 *    Optional.map(...).orElseThrow(() -> excepción)    buscar uno o fallar (404)
 * =====================================================================
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class PlatoServiceImpl implements IPlatoService {

    private final PlatoRepository platoRepository;
    private final PlatoDocumentMapper documentMapper;
    private final IPlatoValidator validator;

    @Override
    public List<Plato> obtenerTodos() {
        List<Plato> platos = platoRepository.findAll().stream()
                .map(documentMapper::toDomain)          // PlatoDocument -> Plato
                .toList();
        log.info("Obteniendo todos los platos. Total: {}", platos.size());
        return platos;
    }

    @Override
    public List<Plato> obtenerDisponibles() {
        // RF-01: la carta solo muestra los platos disponibles
        return platoRepository.findByDisponibleTrue().stream()
                .map(documentMapper::toDomain)
                .toList();
    }

    @Override
    public List<Plato> obtenerPorCategoria(CategoriaPlato categoria) {
        return platoRepository.findByCategoria(categoria).stream()
                .map(documentMapper::toDomain)
                .toList();
    }

    @Override
    public Plato obtenerPorId(String id) {
        // findById devuelve Optional: si está vacío -> 404
        return platoRepository.findById(id)
                .map(documentMapper::toDomain)
                .orElseThrow(() -> {
                    log.warn("Plato no encontrado: id={}", id);
                    return new RecursoNoEncontradoException("Plato", id);
                });
    }

    @Override
    public Plato crear(Plato plato) {
        validator.validarNombreUnico(plato.getNombre());                 // 409 si está repetido
        var guardado = platoRepository.save(documentMapper.toDocument(plato));
        log.info("Plato creado: id={}, nombre={}", guardado.getId(), guardado.getNombre());
        return documentMapper.toDomain(guardado);                        // ya trae el id de Mongo
    }

    @Override
    public Plato actualizar(String id, Plato nuevosDatos) {
        Plato existente = obtenerPorId(id);                              // 404 si no existe
        // Solo valida el nombre si cambió (si no, chocaría consigo mismo)
        if (!existente.getNombre().equalsIgnoreCase(nuevosDatos.getNombre())) {
            validator.validarNombreUnico(nuevosDatos.getNombre());
        }
        existente.setNombre(nuevosDatos.getNombre());
        existente.setDescripcion(nuevosDatos.getDescripcion());
        existente.setPrecio(nuevosDatos.getPrecio());
        existente.setCategoria(nuevosDatos.getCategoria());
        // save con un id que ya existe = actualizar
        var guardado = platoRepository.save(documentMapper.toDocument(existente));
        log.info("Plato actualizado: id={}", id);
        return documentMapper.toDomain(guardado);
    }

    @Override
    public Plato cambiarDisponibilidad(String id, boolean disponible) {
        Plato plato = obtenerPorId(id);
        // Usa el comportamiento del dominio en vez de tocar el atributo
        if (disponible) {
            plato.activar();
        } else {
            plato.desactivar();
        }
        var guardado = platoRepository.save(documentMapper.toDocument(plato));
        log.info("Plato id={} -> disponible={}", id, disponible);
        return documentMapper.toDomain(guardado);
    }

    @Override
    public void eliminar(String id) {
        obtenerPorId(id);                                                // 404 si no existe
        platoRepository.deleteById(id);
        log.info("Plato eliminado: id={}", id);
    }
}
