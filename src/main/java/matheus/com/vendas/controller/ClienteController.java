package matheus.com.vendas.controller;

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
public class ClienteController {

    private ClienteRepository clienteRepository;

    @Autowired
    public ClienteController(ClienteRepository clienteRepository){
        this.clienteRepository = clienteRepository;
    }

    @GetMapping("/{id}")
    public Cliente buscarClientePorId(@PathVariable("id") Integer id) {
        return clienteRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado!"));
    }

    @PostMapping("/cadastrar")
    @ResponseStatus(HttpStatus.CREATED)
    public Cliente cadastrar(@Valid @RequestBody Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    @DeleteMapping("/deletar/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable("id") Integer id) {
        clienteRepository.findById(id)
                .map(cliente -> {
                    clienteRepository.delete(cliente);
                    return Void.TYPE;
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado!"));
    }

    @PutMapping("/atualizar/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void atualizar(@Valid @PathVariable("id") Integer id, @RequestBody Cliente cliente) {
        clienteRepository
                .findById(id)
                .map(clienteExistente -> {
                    cliente.setId(clienteExistente.getId());
                    clienteRepository.save(cliente);
                    return clienteExistente;
                }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado!"));
    }

    @GetMapping("/buscar-por-filtro")
    public List<Cliente> buscarClientePorFiltro(Cliente filtro) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example example = Example.of(filtro, matcher);
        return clienteRepository.findAll(example);
    }

}