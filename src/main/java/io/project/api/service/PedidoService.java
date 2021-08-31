package io.project.api.service;

import io.project.api.api.dto.PedidoDTO;
import io.project.api.domain.enums.StatusPedido;
import io.project.api.domain.model.Pedido;

import java.util.Optional;

public interface PedidoService {

    Pedido salvarPedido(PedidoDTO pedidoDTO);

    Optional<Pedido> obterPedidoCompleto(Integer id);

    void atualizaStatus(Integer id, StatusPedido statusPedido);
}
