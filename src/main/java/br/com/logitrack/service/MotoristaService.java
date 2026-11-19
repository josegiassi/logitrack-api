package br.com.logitrack.service;

import br.com.logitrack.dto.common.PageResponseDTO;
import br.com.logitrack.dto.motorista.MotoristaRequestDTO;
import br.com.logitrack.dto.motorista.MotoristaResponseDTO;
import br.com.logitrack.exception.ConflictException;
import br.com.logitrack.exception.ResourceNotFoundException;
import br.com.logitrack.mapper.MotoristaMapper;
import br.com.logitrack.model.entity.Motorista;
import br.com.logitrack.model.enums.CategoriaCnh;
import br.com.logitrack.repository.EntregaRepository;
import br.com.logitrack.repository.MotoristaRepository;
import br.com.logitrack.repository.specification.MotoristaSpecs;
import br.com.logitrack.repository.specification.Specs;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
public class MotoristaService {

    static final Set<String> CAMPOS_ORDENACAO =
            Set.of("id", "nome", "categoriaCnh", "email", "ativo", "criadoEm");

    private final MotoristaRepository motoristaRepository;
    private final EntregaRepository entregaRepository;

    public MotoristaService(MotoristaRepository motoristaRepository, EntregaRepository entregaRepository) {
        this.motoristaRepository = motoristaRepository;
        this.entregaRepository = entregaRepository;
    }

    @Transactional(readOnly = true)
    public PageResponseDTO<MotoristaResponseDTO> listar(String nome, CategoriaCnh categoria, Boolean ativo,
                                                        Pageable pageable) {
        SortValidator.validar(pageable, CAMPOS_ORDENACAO);
        Specification<Motorista> spec = Specs.allOf(
                MotoristaSpecs.nomeContem(nome),
                MotoristaSpecs.categoriaIgual(categoria),
                MotoristaSpecs.ativoIgual(ativo)
        );
        return PageResponseDTO.from(motoristaRepository.findAll(spec, pageable), MotoristaMapper::toResponse);
    }

    @Transactional(readOnly = true)
    public MotoristaResponseDTO buscarPorId(Long id) {
        return MotoristaMapper.toResponse(buscarEntidade(id));
    }

    @Transactional
    public MotoristaResponseDTO criar(MotoristaRequestDTO dto) {
        Motorista motorista = new Motorista();
        MotoristaMapper.copyToEntity(dto, motorista);
        validarUnicidade(motorista, null);
        return MotoristaMapper.toResponse(motoristaRepository.save(motorista));
    }

    @Transactional
    public MotoristaResponseDTO atualizar(Long id, MotoristaRequestDTO dto) {
        Motorista motorista = buscarEntidade(id);
        MotoristaMapper.copyToEntity(dto, motorista);
        validarUnicidade(motorista, id);
        return MotoristaMapper.toResponse(motoristaRepository.saveAndFlush(motorista));
    }

    @Transactional
    public void excluir(Long id) {
        Motorista motorista = buscarEntidade(id);
        if (entregaRepository.existsByMotoristaId(id)) {
            throw new ConflictException("Motorista possui entregas vinculadas e não pode ser excluído. "
                    + "Inative-o (ativo=false) em vez de excluir.");
        }
        motoristaRepository.delete(motorista);
    }

    /** Usado também pelo EntregaService. */
    @Transactional(readOnly = true)
    public Motorista buscarEntidade(Long id) {
        return motoristaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Motorista", id));
    }

    private void validarUnicidade(Motorista m, Long idAtual) {
        boolean cpfDuplicado = idAtual == null
                ? motoristaRepository.existsByCpf(m.getCpf())
                : motoristaRepository.existsByCpfAndIdNot(m.getCpf(), idAtual);
        if (cpfDuplicado) {
            throw new ConflictException("Já existe um motorista cadastrado com este CPF");
        }

        boolean cnhDuplicada = idAtual == null
                ? motoristaRepository.existsByCnh(m.getCnh())
                : motoristaRepository.existsByCnhAndIdNot(m.getCnh(), idAtual);
        if (cnhDuplicada) {
            throw new ConflictException("Já existe um motorista cadastrado com esta CNH");
        }

        boolean emailDuplicado = idAtual == null
                ? motoristaRepository.existsByEmailIgnoreCase(m.getEmail())
                : motoristaRepository.existsByEmailIgnoreCaseAndIdNot(m.getEmail(), idAtual);
        if (emailDuplicado) {
            throw new ConflictException("Já existe um motorista cadastrado com este e-mail");
        }
    }
}
