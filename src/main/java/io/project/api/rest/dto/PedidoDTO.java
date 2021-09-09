package io.project.api.rest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.project.api.validation.NotEmptyList;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class PedidoDTO {

    @JsonProperty("cliente")
    @NotNull(message = "{campo.codigo-cliente.obrigatorio}")
    private Integer idCliente;

    @NotNull(message = "{campo.total-pedido.obrigatorio}")
    private float total;

    @NotEmptyList(message = "{campo.items-pedido.obrigatorio}")
    private List<ItemPedidoDTO> itens;
}
