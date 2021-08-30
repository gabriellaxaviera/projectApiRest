package io.project.api.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class PedidoDTO {

    @JsonProperty("cliente")
    private Integer idCliente;

    private float total;

    private List<ItemPedidoDTO> items;
}
