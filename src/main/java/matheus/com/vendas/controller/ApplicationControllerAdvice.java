package matheus.com.vendas.controller;

import matheus.com.vendas.exception.ApiErrors;
import matheus.com.vendas.exception.PedidoNaoEncontradoException;
import matheus.com.vendas.exception.RegraNegocioException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ApplicationControllerAdvice {

    @ExceptionHandler(RegraNegocioException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrors handleRegraNegocioException(RegraNegocioException exception) {
        String mensagemErro = exception.getMessage();
        return new ApiErrors(mensagemErro);
    }

    @ExceptionHandler(PedidoNaoEncontradoException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiErrors handlePedidoNaoEncontradoException(PedidoNaoEncontradoException exception) {
        String mensagemErro = exception.getMessage();
        return new ApiErrors(mensagemErro);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrors handleMethodNotValidException(MethodArgumentNotValidException exception) {
        List<String> erros = exception.getBindingResult().getAllErrors()
                .stream().map(erro -> erro.getDefaultMessage())
                .collect(Collectors.toList());
        return new ApiErrors(erros);
    }
}
