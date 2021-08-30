package io.project.api.api.controller;

import io.project.api.domain.model.Produto;
import io.project.api.domain.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("/api/produtos/")
public class ProdutoController {

    @Autowired
    ProdutoRepository produtoRepository;

    @PostMapping
    public ResponseEntity<?> saveProduto(@RequestBody Produto produto) {

        Produto produtoById = produtoRepository.save(produto);
        return ResponseEntity.ok().body(produtoById);
    }

    @PutMapping(value = "{id}")
    public ResponseEntity<?> updateProduto(@PathVariable Integer id, @RequestBody Produto produto) {

        return produtoRepository
                .findById(id)
                .map(p -> {
                    produto.setId(p.getId());
                    produtoRepository.save(produto);
                    return ResponseEntity.noContent().build();
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity<?> deleteProdutoById(@PathVariable Integer id) {
        Optional<Produto> clienteById = produtoRepository.findById(id);

        if (clienteById.isPresent()) {
            produtoRepository.delete(clienteById.get());
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping(value = "{id}")
    public ResponseEntity<?> getProdutoById(@PathVariable Integer id) {
        Optional<Produto> clienteById = produtoRepository.findById(id);

        if (clienteById.isPresent()) {
            return ResponseEntity.ok(clienteById.get());
        }
        return ResponseEntity.status(NOT_FOUND).body("Produto não encontrado");
    }

    @GetMapping
    public ResponseEntity<?> findProduto(Produto filtro) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(
                        ExampleMatcher.StringMatcher.CONTAINING);

        Example<Produto> example = Example.of(filtro, matcher);

        List<Produto> lista = produtoRepository.findAll(example);
        return ResponseEntity.ok(lista);
    }
}
