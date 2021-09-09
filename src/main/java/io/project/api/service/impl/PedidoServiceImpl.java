package io.project.api.service.impl;

import io.project.api.rest.dto.ItemPedidoDTO;
import io.project.api.rest.dto.PedidoDTO;
import io.project.api.domain.enums.StatusPedido;
import io.project.api.domain.model.Cliente;
import io.project.api.domain.model.ItemPedido;
import io.project.api.domain.model.Pedido;
import io.project.api.domain.model.Produto;
import io.project.api.domain.repository.ClienteRepository;
import io.project.api.domain.repository.ItemPedidoRepository;
import io.project.api.domain.repository.ProdutoRepository;
import io.project.api.exception.PedidoNaoEncontradoException;
import io.project.api.exception.RegraNegocioException;
import io.project.api.service.PedidoService;
import io.project.api.domain.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PedidoServiceImpl implements PedidoService {

    @Autowired
    PedidoRepository pedidoRepository;

    @Autowired
    ItemPedidoRepository itemPedidoRepository;

    @Autowired
    ClienteRepository clienteRepository;

    @Autowired
    ProdutoRepository produtoRepository;

    @Override
    @Transactional
    public Pedido salvarPedido(PedidoDTO pedidoDTO) {
        //populando o pedido com alguns dados do dto

        Pedido pedido = new Pedido();
        pedido.setTotal(pedidoDTO.getTotal());
        pedido.setDataPedido(LocalDate.now());
        pedido.setStatus(StatusPedido.REALIZADO);

        Integer idCliente = pedidoDTO.getIdCliente();
        Cliente cliente = clienteRepository.findById(idCliente)
                .orElseThrow(() -> new RegraNegocioException("Código de cliente inválido"));

        pedido.setCliente(cliente);

        List<ItemPedido> itemPedido = converterItens(pedido, pedidoDTO.getItens());

        pedidoRepository.save(pedido);

        itemPedidoRepository.saveAll(itemPedido);

        pedido.setItens(itemPedido);

        return pedido;
    }

    @Override
    public Optional<Pedido> obterPedidoCompleto(Integer id) {
        return pedidoRepository.findByIdFetchItens(id);
    }

    @Override
    @Transactional
    public void atualizaStatus(Integer id, StatusPedido statusPedido) {
        pedidoRepository
                .findById(id)
                .map( pedido -> {
                    pedido.setStatus(statusPedido);
                    return pedidoRepository.save(pedido);
                }).orElseThrow(() -> new PedidoNaoEncontradoException() );
    }

    private List<ItemPedido> converterItens(Pedido pedido, List<ItemPedidoDTO> itens){

        if(itens.isEmpty()){
           throw new RegraNegocioException("Lista vazia");
       }

        return itens.stream().map(dto -> {
            Integer codProduto = dto.getCodProduto();
            Produto product = produtoRepository.findById(codProduto)
                    .orElseThrow(() -> new RuntimeException("Código de produto inválido"));

            ItemPedido itemPedido = new ItemPedido();
            itemPedido.setPedido(pedido);
            itemPedido.setQuantidade(dto.getQuantidade());
            itemPedido.setProduto(product);
            return itemPedido;
        }).collect(Collectors.toList());
    }

}
