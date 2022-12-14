package matheus.com.vendas.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import matheus.com.vendas.dto.AtualizacaoStatusPedidoDTO;
import matheus.com.vendas.dto.PedidoDTO;
import matheus.com.vendas.dto.SaidaItemPedidoDTO;
import matheus.com.vendas.dto.SaidaPedidoDTO;
import matheus.com.vendas.entity.ItemPedido;
import matheus.com.vendas.entity.Pedido;
import matheus.com.vendas.enums.StatusPedido;
import matheus.com.vendas.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("/pedidos")
@Api("Api de Pedidos")
public class PedidoController {

    private PedidoService service;

    @Autowired
    public PedidoController(PedidoService service) {
        this.service = service;
    }

    @PostMapping("/cadastrar")
    @ResponseStatus(CREATED)
    @ApiOperation("Api responsável por cadastrar pedido.")
    public Integer cadastrar(@Valid @RequestBody PedidoDTO dto) {
        Pedido pedido = service.salvar(dto);
        return pedido.getId();
    }

    @GetMapping("/buscar-por-id/{id}")
    @ApiOperation("Api responsável por buscar pedido por id.")
    public SaidaPedidoDTO buscarPedidoPorId(@ApiParam("id do pedido") @PathVariable("id") Integer id) {
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
    @ApiOperation("Api responsável por atualizar status do pedido.")
    public void atualizarStatus(@ApiParam("id do pedido") @PathVariable("id") Integer id, @RequestBody AtualizacaoStatusPedidoDTO dto) {
        String novoStatus = dto.getNovoStatus();
        service.atualizarStatus(id, StatusPedido.valueOf(novoStatus));
    }

}
