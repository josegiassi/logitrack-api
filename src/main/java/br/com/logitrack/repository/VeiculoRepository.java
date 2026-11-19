package br.com.logitrack.repository;

import br.com.logitrack.model.entity.Veiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface VeiculoRepository extends JpaRepository<Veiculo, Long>, JpaSpecificationExecutor<Veiculo> {

    boolean existsByPlaca(String placa);
    boolean existsByPlacaAndIdNot(String placa, Long id);
}
