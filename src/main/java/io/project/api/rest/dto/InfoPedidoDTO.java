package io.project.api.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InfoPedidoDTO {

    private Integer codigo;
    private String cpf;
    private String nomeCliente;
    private float total;
    private String dataPedido;
    private String status;
    private List<InfoItemPedidoDTO> itens;

}
