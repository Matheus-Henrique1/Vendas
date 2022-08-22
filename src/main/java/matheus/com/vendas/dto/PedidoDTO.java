package matheus.com.vendas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import matheus.com.vendas.validation.NotEmptyList;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PedidoDTO implements Serializable {

    @NotNull(message = "Informe o código do cliente. ")
    private Integer cliente;

    @NotNull(message = "Campo Total do pedido é obrigatório. ")
    private BigDecimal total;

    @NotEmptyList(message = "Pedido não pode ser realizado sem itens. ")
    private List<ItemPedidoDTO> itens;

}
