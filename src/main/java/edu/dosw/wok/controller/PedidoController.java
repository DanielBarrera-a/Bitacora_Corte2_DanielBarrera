package edu.dosw.wok.controller;

import edu.dosw.wok.controller.dtos.request.PedidoRequestDTO;
import edu.dosw.wok.controller.dtos.response.PedidoResponseDTO;
import edu.dosw.wok.controller.mappers.PedidoMapper;
import edu.dosw.wok.core.models.EstadoPedido;
import edu.dosw.wok.core.services.IPedidoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

// Endpoints de pedidos (misma receta que PlatoController)
@RestController
@RequestMapping("/api/v1/pedidos")
@RequiredArgsConstructor
@Tag(name = "Pedidos", description = "Armar pedidos y gestionarlos en cocina")
public class PedidoController {

    private final IPedidoService pedidoService;
    private final PedidoMapper pedidoMapper;

    // POST /api/v1/pedidos  -> 201 (RF-02, RF-03)
    @PostMapping
    @Operation(summary = "Crear un pedido y enviarlo a cocina")
    public ResponseEntity<PedidoResponseDTO> crear(@RequestBody @Valid PedidoRequestDTO dto) {
        var creado = pedidoService.crear(pedidoMapper.toDomain(dto));
        return ResponseEntity.status(HttpStatus.CREATED).body(pedidoMapper.toResponse(creado));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener un pedido por id")
    public ResponseEntity<PedidoResponseDTO> obtenerPorId(@PathVariable String id) {
        return ResponseEntity.ok(pedidoMapper.toResponse(pedidoService.obtenerPorId(id)));
    }

    // GET /api/v1/pedidos?estado=RECIBIDO  -> tablero de cocina (query param para filtrar)
    @GetMapping
    @Operation(summary = "Tablero de cocina: pedidos por estado")
    public ResponseEntity<List<PedidoResponseDTO>> obtenerPorEstado(@RequestParam EstadoPedido estado) {
        return ResponseEntity.ok(pedidoMapper.toResponseList(pedidoService.obtenerPorEstado(estado)));
    }

    // GET /api/v1/pedidos/mesa/5/activos
    @GetMapping("/mesa/{numeroMesa}/activos")
    @Operation(summary = "Pedidos activos de una mesa")
    public ResponseEntity<List<PedidoResponseDTO>> activosDeMesa(@PathVariable Integer numeroMesa) {
        return ResponseEntity.ok(pedidoMapper.toResponseList(pedidoService.obtenerActivosDeMesa(numeroMesa)));
    }

    // PATCH /api/v1/pedidos/{id}/estado?nuevo=EN_PREPARACION  -> RF-04 (422 si la transición no es válida)
    @PatchMapping("/{id}/estado")
    @Operation(summary = "Cambiar el estado de un pedido en cocina")
    public ResponseEntity<PedidoResponseDTO> cambiarEstado(@PathVariable String id,
                                                           @RequestParam EstadoPedido nuevo) {
        return ResponseEntity.ok(pedidoMapper.toResponse(pedidoService.cambiarEstado(id, nuevo)));
    }
}
