package io.project.api.repository;

import io.project.api.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

//@Repository //não é necessário devido ao JPArepository
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {

    //@Query(value = "select c from Cliente c where c.nome like :nome") //consulta hql
    @Query(value = "select * from cliente c where c.nome like '%:nome%' ", nativeQuery = true) //consulta sql
    List<Cliente> encontrarPorNome(@Param("nome") String nome);

    @Query(value = "delete from Cliente c where c.nome = :nome")
    @Modifying //update ou delete, operações transacionais
    void deleteByNome(String nome);

    boolean existsByNome(String nome);
}
