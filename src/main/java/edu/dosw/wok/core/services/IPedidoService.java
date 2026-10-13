package edu.dosw.wok.core.services;

import edu.dosw.wok.core.models.EstadoPedido;
import edu.dosw.wok.core.models.Pedido;

import java.util.List;

// Contrato de la lógica de pedidos (ver la explicación general en IPlatoService)
public interface IPedidoService {

    Pedido crear(Pedido pedido);                          // RF-02 + RF-03: arma y envía a cocina

    Pedido obtenerPorId(String id);

    List<Pedido> obtenerPorEstado(EstadoPedido estado);   // tablero de cocina

    List<Pedido> obtenerActivosDeMesa(Integer numeroMesa);

    Pedido cambiarEstado(String id, EstadoPedido nuevo);  // RF-04
}
