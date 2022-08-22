package matheus.com.vendas.exception;

public class PedidoNaoEncontradoException extends RuntimeException{

    public PedidoNaoEncontradoException(String message){
        super(message);
    }
}
