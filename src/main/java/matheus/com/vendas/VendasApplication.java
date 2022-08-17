package matheus.com.vendas;

import matheus.com.vendas.domain.entity.Cliente;
import matheus.com.vendas.domain.repositorio.Clientes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class VendasApplication {

	public static void main(String[] args) {
		SpringApplication.run(VendasApplication.class, args);
	}

	@Bean
	public CommandLineRunner init(@Autowired Clientes clientes){
		return args -> {
			Cliente cliente = new Cliente("Matheus");
			clientes.salvar(cliente);

			Cliente cliente2 = new Cliente("Rina");
			clientes.salvar(cliente2);

			List<Cliente> todosCliente = clientes.obterTodos();
			todosCliente.forEach(System.out::println);
		};
	}

}
