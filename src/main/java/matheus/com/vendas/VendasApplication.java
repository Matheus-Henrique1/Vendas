package matheus.com.vendas;

import matheus.com.vendas.domain.entity.Cliente;
import matheus.com.vendas.domain.repositorio.ClienteRepository;
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
    public CommandLineRunner init(@Autowired ClienteRepository clienteRepository) {
        return args -> {
            Cliente cliente = new Cliente("Matheus");
            clienteRepository.save(cliente);

            Cliente cliente2 = new Cliente("Rina");
            clienteRepository.save(cliente2);

            boolean existe = clienteRepository.existsByNome("Matheus");
            System.out.println("Existe um cliente com o nome Matheus? " + existe);

            System.out.println(clienteRepository.encontrarPorNome("Rina"));

//            System.out.println("Buscando cliente por nome");
//            clienteRepository.findByNomeLike("Rina").forEach(System.out::println);
//
//            System.out.println("Deletando clientes");
//            clienteRepository.findAll().forEach(c -> {
//                clienteRepository.delete(c);
//            });
//
//            List<Cliente> todosClientes = clienteRepository.findAll();
//            if (todosClientes.isEmpty()) {
//                System.out.println("Lista Vazia");
//            } else {
//                todosClientes.forEach(System.out::println);
//            }
        };
    }

}
