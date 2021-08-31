package io.project.api.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InfoItemPedidoDTO {

    private String descricaoProduto;
    private float precoUnitario;
    private Integer quantidade;
}
