package edu.dosw.wok.core.services;

import edu.dosw.wok.core.exceptions.RecursoNoEncontradoException;
import edu.dosw.wok.core.exceptions.ReglaDeNegocioException;
import edu.dosw.wok.core.models.EstadoPedido;
import edu.dosw.wok.core.models.ItemPedido;
import edu.dosw.wok.core.models.Pedido;
import edu.dosw.wok.core.models.Plato;
import edu.dosw.wok.persistence.mappers.PedidoDocumentMapper;
import edu.dosw.wok.persistence.repositories.PedidoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/*
 * =====================================================================
 *  SERVICE  —  lógica de Pedido con MongoDB
 * =====================================================================
 *  PUNTOS CLAVE PARA EL PARCIAL
 *    - Un Service puede usar OTRO Service, siempre por su INTERFAZ:
 *      aquí se inyecta IPlatoService para validar los platos (S08 diapo 26).
 *      Cuidado con dependencias circulares (A usa B y B usa A).
 *    - Reglas de negocio -> excepciones propias:
 *        plato no disponible            -> ReglaDeNegocioException (422)
 *        transición de estado inválida  -> ReglaDeNegocioException (422)
 *        pedido o plato inexistente     -> RecursoNoEncontradoException (404)
 *    - Lo que asigna el SISTEMA (no el cliente): estado inicial RECIBIDO,
 *      fechaCreacion, nombre y precio congelados del plato, id (Mongo).
 * =====================================================================
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class PedidoServiceImpl implements IPedidoService {

    private final PedidoRepository pedidoRepository;
    private final PedidoDocumentMapper documentMapper;
    private final IPlatoService platoService;

    @Override
    public Pedido crear(Pedido pedido) {
        // Completa cada ítem con los datos del plato y valida disponibilidad (Streams)
        List<ItemPedido> items = pedido.getItems().stream()
                .map(this::completarItem)
                .toList();
        pedido.setItems(items);
        pedido.setEstado(EstadoPedido.RECIBIDO);          // RF-03: entra al tablero de cocina
        pedido.setFechaCreacion(LocalDateTime.now());

        var guardado = pedidoRepository.save(documentMapper.toDocument(pedido));
        log.info("Pedido creado: id={}, mesa={}, total={}", guardado.getId(),
                guardado.getNumeroMesa(), pedido.getTotal());
        return documentMapper.toDomain(guardado);
    }

    // Busca el plato (404 si no existe), exige que esté disponible (422) y congela nombre y precio
    private ItemPedido completarItem(ItemPedido item) {
        Plato plato = platoService.obtenerPorId(item.getPlatoId());
        if (!plato.estaDisponible()) {
            throw new ReglaDeNegocioException("El plato '" + plato.getNombre() + "' no está disponible");
        }
        item.setNombrePlato(plato.getNombre());
        item.setPrecioUnitario(plato.getPrecio());
        return item;
    }

    @Override
    public Pedido obtenerPorId(String id) {
        return pedidoRepository.findById(id)
                .map(documentMapper::toDomain)
                .orElseThrow(() -> new RecursoNoEncontradoException("Pedido", id));
    }

    @Override
    public List<Pedido> obtenerPorEstado(EstadoPedido estado) {
        return documentMapper.toDomainList(pedidoRepository.findByEstado(estado));
    }

    @Override
    public List<Pedido> obtenerActivosDeMesa(Integer numeroMesa) {
        return documentMapper.toDomainList(pedidoRepository.buscarActivosDeMesa(numeroMesa));
    }

    @Override
    public Pedido cambiarEstado(String id, EstadoPedido nuevo) {
        Pedido pedido = obtenerPorId(id);
        // La regla está en el enum; el Service decide qué hacer si no se cumple
        if (!pedido.getEstado().puedeTransicionarA(nuevo)) {
            throw new ReglaDeNegocioException(
                    "No se puede pasar de " + pedido.getEstado() + " a " + nuevo);
        }
        pedido.cambiarEstado(nuevo);
        var guardado = pedidoRepository.save(documentMapper.toDocument(pedido));
        log.info("Pedido id={} -> {}", id, nuevo);
        return documentMapper.toDomain(guardado);
    }
}
