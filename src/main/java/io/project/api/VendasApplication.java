package io.project.api;

import io.project.api.model.Cliente;
import io.project.api.model.Pedido;
import io.project.api.repository.ClienteRepository;
import io.project.api.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@SpringBootApplication
@RestController
public class VendasApplication {

    @Bean
    public CommandLineRunner init(@Autowired ClienteRepository clienteRepository,
                                  @Autowired PedidoRepository pedidoRepository) {
        return args -> {

            System.out.println("Salvando clienteRepository");
            Cliente fulana = new Cliente("Gabriella");
            clienteRepository.save(fulana);

            Pedido p = new Pedido();
            p.setCliente(fulana);
            p.setDataPedido(LocalDate.now());
            p.setTotal(54.8f);

            pedidoRepository.save(p);

            Cliente clienteFetchPedidos = clienteRepository.findClienteFetchPedidos(fulana.getId());
            System.out.println(clienteFetchPedidos);
            System.out.println("Pedidos do cliente: " + clienteFetchPedidos.getPedidos());

            pedidoRepository.findByCliente(fulana).forEach(System.out::println);

//            boolean exists = clienteRepository.existsByNome("Gabriella");
//            System.out.println(exists);

            /*List<Cliente> todosClientes = clienteRepository.findAll();
            todosClientes.forEach(System.out::println);

            System.out.println("Atualizando clienteRepository");
            todosClientes.forEach(c -> {
                c.setNome(c.getNome() + " atualizado.");
                clienteRepository.save(c);
            });

            todosClientes = clienteRepository.findAll();
            todosClientes.forEach(System.out::println);

            System.out.println("Buscando clienteRepository");
            clienteRepository.findByNomeLike("Cli").forEach(System.out::println);

            System.out.println("Deletando clienteRepository");
            clienteRepository.findAll().forEach(c -> {
                clienteRepository.delete(c);
            });

            System.out.println("Buscando todos");
            todosClientes = clienteRepository.findAll();
            if(todosClientes.isEmpty()){
                System.out.println("Nenhum cliente encontrado.");
            }else{
                todosClientes.forEach(System.out::println);
            }*/
        };
    }

    public static void main(String[] args) {
        SpringApplication.run(VendasApplication.class, args);
    }
}
