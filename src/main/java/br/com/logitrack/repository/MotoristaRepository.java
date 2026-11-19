package br.com.logitrack.repository;

import br.com.logitrack.model.entity.Motorista;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface MotoristaRepository extends JpaRepository<Motorista, Long>, JpaSpecificationExecutor<Motorista> {

    boolean existsByCpf(String cpf);
    boolean existsByCpfAndIdNot(String cpf, Long id);

    boolean existsByCnh(String cnh);
    boolean existsByCnhAndIdNot(String cnh, Long id);

    boolean existsByEmailIgnoreCase(String email);
    boolean existsByEmailIgnoreCaseAndIdNot(String email, Long id);
}
