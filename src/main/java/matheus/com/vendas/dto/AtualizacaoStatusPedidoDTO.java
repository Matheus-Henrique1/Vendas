package matheus.com.vendas.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class AtualizacaoStatusPedidoDTO implements Serializable {

    private String novoStatus;
}
