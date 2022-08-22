package matheus.com.vendas.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SaidaPedidoDTO {

    private Integer codigo;
    private String cpf;
    private String nomeCliente;
    private BigDecimal totalPedido;
    private String dataPedido;
    private List<SaidaItemPedidoDTO> itens;
}
