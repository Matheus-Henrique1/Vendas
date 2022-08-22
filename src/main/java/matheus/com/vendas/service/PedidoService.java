package matheus.com.vendas.service;

import matheus.com.vendas.dto.PedidoDTO;
import matheus.com.vendas.entity.Pedido;
import matheus.com.vendas.enums.StatusPedido;

import java.util.Optional;

public interface PedidoService {

    Pedido salvar(PedidoDTO dto);

    Optional<Pedido> obterPedidoCompleto(Integer id);

    void atualizarStatus(Integer id, StatusPedido statusPedido);

}
