package matheus.com.vendas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PedidoDTO implements Serializable {

    private Integer cliente;
    private BigDecimal total;
    private List<ItemPedidoDTO> itens;

}
