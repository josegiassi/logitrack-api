package br.com.logitrack.repository;

import br.com.logitrack.model.entity.Entrega;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EntregaRepository extends JpaRepository<Entrega, Long>, JpaSpecificationExecutor<Entrega> {

    boolean existsByCodigoRastreio(String codigoRastreio);

    boolean existsByMotoristaId(Long motoristaId);

    boolean existsByVeiculoId(Long veiculoId);

    /** Carrega motorista e veículo junto (evita N+1 ao montar o DTO de saída). */
    @Override
    @EntityGraph(attributePaths = {"motorista", "veiculo"})
    Optional<Entrega> findById(Long id);

    @Override
    @EntityGraph(attributePaths = {"motorista", "veiculo"})
    Page<Entrega> findAll(Specification<Entrega> spec, Pageable pageable);

    @EntityGraph(attributePaths = {"motorista", "veiculo"})
    Optional<Entrega> findByCodigoRastreio(String codigoRastreio);
}
