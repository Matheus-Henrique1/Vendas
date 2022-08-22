package matheus.com.vendas.controller;

import matheus.com.vendas.dto.AtualizacaoStatusPedidoDTO;
import matheus.com.vendas.dto.PedidoDTO;
import matheus.com.vendas.dto.SaidaItemPedidoDTO;
import matheus.com.vendas.dto.SaidaPedidoDTO;
import matheus.com.vendas.entity.ItemPedido;
import matheus.com.vendas.entity.Pedido;
import matheus.com.vendas.enums.StatusPedido;
import matheus.com.vendas.exception.RegraNegocioException;
import matheus.com.vendas.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    private PedidoService service;

    @Autowired
    public PedidoController(PedidoService service) {
        this.service = service;
    }

    @PostMapping("/cadastrar")
    @ResponseStatus(CREATED)
    public Integer cadastrar(@RequestBody PedidoDTO dto) {
        Pedido pedido = service.salvar(dto);
        return pedido.getId();
    }

    @GetMapping("/buscar-por-id/{id}")
    public SaidaPedidoDTO buscarPedidoPorId(@PathVariable("id") Integer id) {
        return service
                .obterPedidoCompleto(id)
                .map(p -> converter(p))
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Pedido não encontrado. "));
    }

    private SaidaPedidoDTO converter(Pedido pedido) {
        return SaidaPedidoDTO
                .builder().codigo(pedido.getId())
                .dataPedido(pedido.getDataPedido().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                .cpf(pedido.getCliente().getCpf())
                .nomeCliente(pedido.getCliente().getNome())
                .totalPedido(pedido.getTotal())
                .status(pedido.getStatus().name())
                .itens(converterItemPedido(pedido.getItens()))
                .build();

    }

    private List<SaidaItemPedidoDTO> converterItemPedido(List<ItemPedido> itens) {
        if (CollectionUtils.isEmpty(itens)) {
            return Collections.emptyList();
        }
        return itens.stream().map(
                item -> SaidaItemPedidoDTO
                        .builder()
                        .descricaoProduto(item.getProduto().getDescricao())
                        .precoUnitario(item.getProduto().getPreco())
                        .quantidade(item.getQuantidade())
                        .build()
        ).collect(Collectors.toList());
    }

    @PatchMapping("/atualizar-status/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void atualizarStatus(@PathVariable("id") Integer id, @RequestBody AtualizacaoStatusPedidoDTO dto) {
        String novoStatus = dto.getNovoStatus();
        service.atualizarStatus(id, StatusPedido.valueOf(novoStatus));
    }

}
