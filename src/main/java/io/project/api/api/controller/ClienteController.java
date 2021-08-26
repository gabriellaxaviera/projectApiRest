package io.project.api.api.controller;

import io.project.api.domain.model.Cliente;
import io.project.api.domain.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class ClienteController {

    @Autowired
    ClienteRepository clienteRepository;

    @GetMapping(value = "/clientes/{id}")
    @ResponseBody
    public ResponseEntity getClienteById(@PathVariable Integer id) {
        Optional<Cliente> clienteById = clienteRepository.findById(id);

        if (clienteById.isPresent()) {
            return ResponseEntity.ok(clienteById.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping(value = "/clientes")
    @ResponseBody
    public ResponseEntity saveCliente(@RequestBody Cliente cliente) {

        Cliente clienteById = clienteRepository.save(cliente);
        return ResponseEntity.ok().body(clienteById);
    }

    @DeleteMapping(value = "/clientes/{id}")
    @ResponseBody
    public ResponseEntity deleteClienteById(@PathVariable Integer id) {
        Optional<Cliente> clienteById = clienteRepository.findById(id);

        if (clienteById.isPresent()) {
            clienteRepository.delete(clienteById.get());
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping(value = "/clientes/{id}")
    @ResponseBody
    public ResponseEntity updateCliente(@PathVariable Integer id, @RequestBody Cliente cliente) {

        return clienteRepository
                .findById(id)
                .map(clienteExistente -> {
                    cliente.setId(clienteExistente.getId());
                    clienteRepository.save(cliente);
                    return ResponseEntity.noContent().build();
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/clientes")
    public ResponseEntity find( Cliente filtro ){
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(
                        ExampleMatcher.StringMatcher.CONTAINING );

        Example<Cliente> example = Example.of(filtro, matcher);

        List<Cliente> lista = clienteRepository.findAll(example);
        return ResponseEntity.ok(lista);
    }
}
