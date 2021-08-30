package io.project.api.api.controller;

import io.project.api.api.dto.PedidoDTO;
import io.project.api.domain.model.Pedido;
import io.project.api.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/pedidos/")
public class PedidoController {

    @Autowired
    PedidoService pedidoService;

    @PostMapping
    public ResponseEntity<?> savePedido(@RequestBody PedidoDTO pedidoDTO) {

        Pedido salvarPedido = pedidoService.salvarPedido(pedidoDTO);
        return ResponseEntity.status(200).body(salvarPedido.getId());
    }

}
