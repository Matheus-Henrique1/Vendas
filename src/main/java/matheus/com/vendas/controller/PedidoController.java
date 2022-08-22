package matheus.com.vendas.controller;

import matheus.com.vendas.dto.PedidoDTO;
import matheus.com.vendas.entity.Pedido;
import matheus.com.vendas.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    private PedidoService service;

    @Autowired
    public PedidoController(PedidoService service){
        this.service = service;
    }

    @PostMapping("/cadastrar")
    @ResponseStatus(CREATED)
    public Integer cadastrar(@RequestBody PedidoDTO dto){
        Pedido pedido = service.salvar(dto);
        return pedido.getId();
    }

}
