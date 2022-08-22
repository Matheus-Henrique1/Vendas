package matheus.com.vendas.repository;

import matheus.com.vendas.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {

    // select c from Cliente c where c.nome like :nome
    List<Cliente> findByNomeLike(String nome);

    List<Cliente> findByNomeOrIdOrderById(String nome, Integer id);

    Cliente findOneByNome(String nome);

    boolean existsByNome(String nome);

    // Consulta em JPQL
    @Query(value = "select c from Cliente c where c.nome like :nome")
    List<Cliente> encontrarPorNome(@Param("nome") String nome);

    // Consulta Nativa com SQL
    @Query(value = "select * from Cliente c where c.nome like '%:nome' ", nativeQuery = true)
    List<Cliente> encontrarPorNome2(@Param("nome") String nome);

    void deleteByNome(String nome);

    @Query("delete from Cliente c where c.nome = :nome")
    @Modifying
    void deleteByNome2(@Param("nome") String nome);

    @Query("select c from Cliente c left join fetch c.pedidos p where c.id = :id")
    Cliente buscarClienteEProdutos(@Param("id") Integer id);

}
