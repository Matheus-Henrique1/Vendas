package matheus.com.vendas.service.impl;

import matheus.com.vendas.dto.ItemPedidoDTO;
import matheus.com.vendas.dto.PedidoDTO;
import matheus.com.vendas.entity.Cliente;
import matheus.com.vendas.entity.ItemPedido;
import matheus.com.vendas.entity.Pedido;
import matheus.com.vendas.entity.Produto;
import matheus.com.vendas.exception.RegraNegocioException;
import matheus.com.vendas.repository.ClienteRepository;
import matheus.com.vendas.repository.ItemPedidoRepository;
import matheus.com.vendas.repository.PedidoRepository;
import matheus.com.vendas.repository.ProdutoRepository;
import matheus.com.vendas.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PedidoServiceImpl implements PedidoService {

    private PedidoRepository pedidoRepository;
    private ClienteRepository clienteRepository;
    private ProdutoRepository produtoRepository;
    private ItemPedidoRepository itemPedidoRepository;


    @Autowired
    public PedidoServiceImpl(PedidoRepository pedidoRepository,
                             ClienteRepository clienteRepository,
                             ProdutoRepository produtoRepository,
                             ItemPedidoRepository itemPedidoRepository) {
        this.pedidoRepository = pedidoRepository;
        this.clienteRepository = clienteRepository;
        this.produtoRepository = produtoRepository;
        this.itemPedidoRepository = itemPedidoRepository;
    }

    @Override
    @Transactional
    public Pedido salvar(PedidoDTO dto) {
        Integer idCliente = dto.getCliente();
        Cliente cliente = clienteRepository
                .findById(idCliente)
                .orElseThrow(() -> new RegraNegocioException("Código de cliente inválido"));

        Pedido pedido = new Pedido();
        pedido.setTotal(dto.getTotal());
        pedido.setDataPedido(LocalDate.now());
        pedido.setCliente(cliente);

        List<ItemPedido> itensPedido = converterItens(pedido, dto.getItens());
        pedidoRepository.save(pedido);
        itemPedidoRepository.saveAll(itensPedido);
        pedido.setItens(itensPedido);

        return pedido;
    }

    private List<ItemPedido> converterItens(Pedido pedido, List<ItemPedidoDTO> itens) {
        if (itens.isEmpty()) {
            throw new RegraNegocioException("Não é possível realizar um pedido sem itens. ");
        }

        return itens.stream().map(dto -> {
            Integer idProduto = dto.getProduto();
            Produto produto = produtoRepository
                    .findById(idProduto)
                    .orElseThrow(() -> new RegraNegocioException("Código de produto inválido: " + idProduto
                    ));
            ItemPedido itemPedido = new ItemPedido();
            itemPedido.setQuantidade(dto.getQuantidade());
            itemPedido.setPedido(pedido);
            itemPedido.setProduto(produto);
            return itemPedido;
        }).collect(Collectors.toList());
    }

    @Override
    public Optional<Pedido> obterPedidoCompleto(Integer id){
        return pedidoRepository.findyByIdFetchItens(id);
    }

}
