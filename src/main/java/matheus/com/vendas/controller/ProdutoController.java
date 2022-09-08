package matheus.com.vendas.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import matheus.com.vendas.entity.Produto;
import matheus.com.vendas.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequestMapping("/produtos")
@Api("Api de Produtos")
public class ProdutoController {

    private ProdutoRepository produtoRepository;

    @Autowired
    public ProdutoController(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }


    @GetMapping("/buscar-por-id/{id}")
    @ApiOperation("Api responsável por buscar produto pelo id.")
    public Produto buscarProdutoPorId(@ApiParam("id do produto") @PathVariable("id") Integer id) {
        return produtoRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado!"));
    }

    @PostMapping("/cadastrar")
    @ResponseStatus(CREATED)
    @ApiOperation("Api responsável por cadastrar produto.")
    public Produto cadastrar(@Valid @RequestBody Produto produto) {
        return produtoRepository.save(produto);
    }

    @DeleteMapping("/deletar/{id}")
    @ResponseStatus(NO_CONTENT)
    @ApiOperation("Api responsável por deletar produto.")
    public void deletar(@ApiParam("id do produto") @PathVariable("id") Integer id) {
        produtoRepository.findById(id)
                .map(produto -> {
                    produtoRepository.delete(produto);
                    return Void.TYPE;
                }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado!"));
    }

    @PutMapping("/atualizar/{id}")
    @ResponseStatus(NO_CONTENT)
    @ApiOperation("Api responsável por atualizar produto.")
    public void atualizar(@ApiParam("id do produto") @Valid @PathVariable("id") Integer id, @RequestBody Produto produto) {
        produtoRepository
                .findById(id)
                .map(p -> {
                    produto.setId(p.getId());
                    produtoRepository.save(produto);
                    return produto;
                }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado!"));
    }

    @GetMapping("/buscar-por-filtro")
    @ApiOperation("Api responsável por buscar produto por filtro.")
    public List<Produto> buscarPorFiltro(Produto filtro) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example example = Example.of(filtro, matcher);
        return produtoRepository.findAll(example);
    }

}
