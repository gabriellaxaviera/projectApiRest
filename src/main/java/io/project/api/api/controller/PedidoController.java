package io.project.api.api.controller;

import io.project.api.api.dto.*;
import io.project.api.domain.enums.StatusPedido;
import io.project.api.domain.model.ItemPedido;
import io.project.api.domain.model.Pedido;
import io.project.api.exception.RegraNegocioException;
import io.project.api.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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

    @GetMapping("{id}")
    public InfoPedidoDTO getInfosPedidoById(@PathVariable Integer id){
        return pedidoService.obterPedidoCompleto(id)
                .map( p -> converter(p))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "PEDIDO NÃO ENCONTRADO"));
    }

    @PatchMapping("{id}") //para update parcial
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateStatusPedido(@PathVariable Integer id ,
                                   @RequestBody AtualizacaoStatusPedidoDTO dto) {
        String novoStatus = dto.getNovoStatus();
        pedidoService.atualizaStatus(id, StatusPedido.valueOf(novoStatus));
    }

    private InfoPedidoDTO converter(Pedido pedido){
        return InfoPedidoDTO
                .builder()
                .codigo(pedido.getId())
                .dataPedido(pedido.getDataPedido().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                .cpf(pedido.getCliente().getCpf())
                .nomeCliente(pedido.getCliente().getNome())
                .total(pedido.getTotal())
                .itens(converter(pedido.getItens()))
                .status(pedido.getStatus().name())
                .build();
    }

    private List<InfoItemPedidoDTO> converter(List<ItemPedido> itens){
        if(itens.isEmpty()){
            return Collections.emptyList();
        }

        List<InfoItemPedidoDTO> listaItemPedido = new ArrayList<>();
        for (ItemPedido item: itens) {
            listaItemPedido.add(InfoItemPedidoDTO
                    .builder().descricaoProduto(item.getProduto().getDescricao())
                    .precoUnitario(item.getProduto().getPreco())
                    .quantidade(item.getQuantidade())
                    .build());
        }
        /*return itens.stream().map(
                item -> InfoItemPedidoDTO
                        .builder().descricaoProduto(item.getProduto().getDescricao())
                        .precoUnitario(item.getProduto().getPreco())
                        .quantidade(item.getQuantidade())
                        .build()
        ).collect(Collectors.toList());*/

        return listaItemPedido;
    }

}
