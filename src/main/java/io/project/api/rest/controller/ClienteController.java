package io.project.api.rest.controller;

import io.project.api.domain.model.Cliente;
import io.project.api.domain.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    @Autowired
    ClienteRepository clienteRepository;

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getClienteById(@PathVariable Integer id) {
        Optional<Cliente> clienteById = clienteRepository.findById(id);

        if (clienteById.isPresent()) {
            return ResponseEntity.ok(clienteById.get());
        }
        return ResponseEntity.status(NOT_FOUND).body("Cliente não encontrado");
    }

    @PostMapping
    public ResponseEntity<?> saveCliente(@RequestBody @Valid Cliente cliente) {

        Cliente clienteById = clienteRepository.save(cliente);
        return ResponseEntity.ok().body(clienteById);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteClienteById(@PathVariable Integer id) {
        Optional<Cliente> clienteById = clienteRepository.findById(id);

        if (clienteById.isPresent()) {
            clienteRepository.delete(clienteById.get());
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(NOT_FOUND).body("Cliente não encontrado");
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<?> updateCliente(@PathVariable Integer id, @RequestBody @Valid Cliente cliente) {

        return clienteRepository
                .findById(id)
                .map(clienteExistente -> {
                    cliente.setId(clienteExistente.getId());
                    clienteRepository.save(cliente);
                    return ResponseEntity.noContent().build();
                })
                .orElseGet(() -> ResponseEntity.status(NOT_FOUND).body("Cliente não encontrado"));
    }

    @GetMapping
    public ResponseEntity<?> find(Cliente filtro) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(
                        ExampleMatcher.StringMatcher.CONTAINING);

        Example<Cliente> example = Example.of(filtro, matcher);

        List<Cliente> lista = clienteRepository.findAll(example);
        return ResponseEntity.ok(lista);
    }
}
