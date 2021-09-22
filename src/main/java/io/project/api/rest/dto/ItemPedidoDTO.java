package io.project.api.rest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ItemPedidoDTO {

    @JsonProperty("produto")
    private Integer codProduto;

    private Integer quantidade;
}
