package io.project.api.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class PedidoDTO {

    @JsonProperty("cliente")
    @NotNull(message = "Informe o código do cliente")
    private Integer idCliente;
    @NotNull(message = "O campo total é obrigatório.")
    private float total;

    private List<ItemPedidoDTO> itens;
}
