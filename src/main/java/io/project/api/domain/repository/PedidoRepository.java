package io.project.api.domain.repository;

import io.project.api.domain.model.Cliente;
import io.project.api.domain.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface PedidoRepository extends JpaRepository<Pedido, Integer> {

    Set<Pedido> findByCliente (Cliente cliente);
}
