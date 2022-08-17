package matheus.com.vendas.domain.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemPedido {

    private Integer id;
    private Pedido pedido;
    private Produto produto;
    private Integer quantidade;
}
