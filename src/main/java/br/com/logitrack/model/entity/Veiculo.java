package br.com.logitrack.model.entity;

import br.com.logitrack.model.enums.TipoVeiculo;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "veiculo")
public class Veiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 7)
    private String placa;

    @Column(nullable = false, length = 60)
    private String modelo;

    @Column(nullable = false, length = 60)
    private String marca;

    @Column(nullable = false)
    private Integer ano;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private TipoVeiculo tipo;

    @Column(name = "capacidade_kg", nullable = false, precision = 10, scale = 2)
    private BigDecimal capacidadeKg;

    @Column(nullable = false)
    private boolean ativo = true;

    @Column(name = "criado_em", nullable = false, updatable = false)
    private LocalDateTime criadoEm;

    @Column(name = "atualizado_em", nullable = false)
    private LocalDateTime atualizadoEm;

    /** Lado inverso do relacionamento 1:N com Entrega. */
    @OneToMany(mappedBy = "veiculo")
    private List<Entrega> entregas = new ArrayList<>();

    @PrePersist
    void prePersist() {
        criadoEm = LocalDateTime.now();
        atualizadoEm = criadoEm;
    }

    @PreUpdate
    void preUpdate() {
        atualizadoEm = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getPlaca() { return placa; }
    public void setPlaca(String placa) { this.placa = placa; }

    public String getModelo() { return modelo; }
    public void setModelo(String modelo) { this.modelo = modelo; }

    public String getMarca() { return marca; }
    public void setMarca(String marca) { this.marca = marca; }

    public Integer getAno() { return ano; }
    public void setAno(Integer ano) { this.ano = ano; }

    public TipoVeiculo getTipo() { return tipo; }
    public void setTipo(TipoVeiculo tipo) { this.tipo = tipo; }

    public BigDecimal getCapacidadeKg() { return capacidadeKg; }
    public void setCapacidadeKg(BigDecimal capacidadeKg) { this.capacidadeKg = capacidadeKg; }

    public boolean isAtivo() { return ativo; }
    public void setAtivo(boolean ativo) { this.ativo = ativo; }

    public LocalDateTime getCriadoEm() { return criadoEm; }
    public LocalDateTime getAtualizadoEm() { return atualizadoEm; }

    public List<Entrega> getEntregas() { return entregas; }
}
