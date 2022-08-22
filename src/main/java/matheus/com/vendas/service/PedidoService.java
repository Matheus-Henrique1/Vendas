package matheus.com.vendas.service;

import matheus.com.vendas.dto.PedidoDTO;
import matheus.com.vendas.entity.Pedido;

public interface PedidoService {

    Pedido salvar(PedidoDTO dto);

}
