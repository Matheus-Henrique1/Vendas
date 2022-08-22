package matheus.com.vendas.repository;

import matheus.com.vendas.entity.Cliente;
import matheus.com.vendas.entity.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PedidoRepository extends JpaRepository<Pedido, Integer> {

    List<Pedido> findByCliente(Cliente cliente);

}
