package io.project.api.service;

import io.project.api.api.dto.PedidoDTO;
import io.project.api.domain.model.Pedido;

public interface PedidoService {

    Pedido salvarPedido(PedidoDTO pedidoDTO);
}
