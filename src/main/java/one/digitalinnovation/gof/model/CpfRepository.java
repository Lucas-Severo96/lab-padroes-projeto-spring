package one.digitalinnovation.gof.model;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Interface exclusiva para buscar o CPF do cliente
 */
@Repository
public interface CpfRepository extends CrudRepository<Cliente, String> {
    Optional<Cliente> findByCpf(String cpf);
}
