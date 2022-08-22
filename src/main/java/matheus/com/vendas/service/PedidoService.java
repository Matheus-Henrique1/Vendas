package matheus.com.vendas.service;

import matheus.com.vendas.dto.PedidoDTO;
import matheus.com.vendas.entity.Pedido;

import java.util.Optional;

public interface PedidoService {

    Pedido salvar(PedidoDTO dto);

    Optional<Pedido> obterPedidoCompleto(Integer id);

}
