package io.project.api.api.controller;

import io.project.api.domain.model.Cliente;
import io.project.api.domain.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}
