package io.project.api.repository;

import io.project.api.model.Cliente;
import io.project.api.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface PedidoRepository extends JpaRepository<Pedido, Integer> {

    Set<Pedido> findByCliente (Cliente cliente);
}
