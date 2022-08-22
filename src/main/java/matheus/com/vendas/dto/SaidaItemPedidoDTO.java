package matheus.com.vendas.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SaidaItemPedidoDTO implements Serializable {

    private String descricaoProduto;
    private BigDecimal precoUnitario;
    private Integer quantidade;

}
