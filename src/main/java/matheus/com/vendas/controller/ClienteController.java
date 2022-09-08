package matheus.com.vendas.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import matheus.com.vendas.entity.Cliente;
import matheus.com.vendas.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/clientes")
@Api("Api de Clientes")
public class ClienteController {

    private ClienteRepository clienteRepository;

    @Autowired
    public ClienteController(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @GetMapping("/buscar-por-id/{id}")
    @ApiOperation("Api responsável por buscar cliente pelo id.")
    public Cliente buscarClientePorId(@ApiParam("id do cliente") @PathVariable("id") Integer id) {
        return clienteRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado!"));
    }

    @PostMapping("/cadastrar")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation("Api responsável por cadastrar cliente.")
    public Cliente cadastrar(@Valid @RequestBody Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    @DeleteMapping("/deletar/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation("Api responsável por deletar cliente pelo id.")
    public void deletar(@ApiParam("id do cliente") @PathVariable("id") Integer id) {
        clienteRepository.findById(id)
                .map(cliente -> {
                    clienteRepository.delete(cliente);
                    return Void.TYPE;
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado!"));
    }

    @PutMapping("/atualizar/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation("Api responsável por atualizar cliente pelo id.")
    public void atualizar(@ApiParam("id do cliente") @Valid @PathVariable("id") Integer id, @RequestBody Cliente cliente) {
        clienteRepository
                .findById(id)
                .map(clienteExistente -> {
                    cliente.setId(clienteExistente.getId());
                    clienteRepository.save(cliente);
                    return clienteExistente;
                }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado!"));
    }

    @GetMapping("/buscar-por-filtro")
    @ApiOperation("Api responsável por buscar cliente por filtro.")
    public List<Cliente> buscarClientePorFiltro(Cliente filtro) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example example = Example.of(filtro, matcher);
        return clienteRepository.findAll(example);
    }

}