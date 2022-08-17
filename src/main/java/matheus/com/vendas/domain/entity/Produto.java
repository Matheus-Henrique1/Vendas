package matheus.com.vendas.domain.entity;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class Produto {

    private Integer id;
    private String descricao;
    private BigDecimal preco;
}
