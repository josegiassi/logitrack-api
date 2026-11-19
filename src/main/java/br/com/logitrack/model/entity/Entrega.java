package br.com.logitrack.model.entity;

import br.com.logitrack.model.enums.StatusEntrega;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "entrega")
public class Entrega {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "codigo_rastreio", nullable = false, unique = true, length = 12)
    private String codigoRastreio;

    @Column(nullable = false, length = 255)
    private String descricao;

    @Column(name = "destinatario_nome", nullable = false, length = 100)
    private String destinatarioNome;

    @Column(name = "endereco_destino", nullable = false, length = 200)
    private String enderecoDestino;

    @Column(name = "cidade_destino", nullable = false, length = 80)
    private String cidadeDestino;

    @Column(name = "uf_destino", nullable = false, length = 2)
    private String ufDestino;

    @Column(name = "peso_kg", nullable = false, precision = 10, scale = 2)
    private BigDecimal pesoKg;

    @Column(name = "valor_frete", nullable = false, precision = 10, scale = 2)
    private BigDecimal valorFrete;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private StatusEntrega status = StatusEntrega.PENDENTE;

    @Column(name = "data_prevista", nullable = false)
    private LocalDate dataPrevista;

    @Column(name = "data_entrega")
    private LocalDateTime dataEntrega;

    /** Lado dono do relacionamento N:1 com Motorista (FK motorista_id). */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "motorista_id", nullable = false)
    private Motorista motorista;

    /** Lado dono do relacionamento N:1 com Veiculo (FK veiculo_id). */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "veiculo_id", nullable = false)
    private Veiculo veiculo;

    @Column(name = "criado_em", nullable = false, updatable = false)
    private LocalDateTime criadoEm;

    @Column(name = "atualizado_em", nullable = false)
    private LocalDateTime atualizadoEm;

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

    public String getCodigoRastreio() { return codigoRastreio; }
    public void setCodigoRastreio(String codigoRastreio) { this.codigoRastreio = codigoRastreio; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public String getDestinatarioNome() { return destinatarioNome; }
    public void setDestinatarioNome(String destinatarioNome) { this.destinatarioNome = destinatarioNome; }

    public String getEnderecoDestino() { return enderecoDestino; }
    public void setEnderecoDestino(String enderecoDestino) { this.enderecoDestino = enderecoDestino; }

    public String getCidadeDestino() { return cidadeDestino; }
    public void setCidadeDestino(String cidadeDestino) { this.cidadeDestino = cidadeDestino; }

    public String getUfDestino() { return ufDestino; }
    public void setUfDestino(String ufDestino) { this.ufDestino = ufDestino; }

    public BigDecimal getPesoKg() { return pesoKg; }
    public void setPesoKg(BigDecimal pesoKg) { this.pesoKg = pesoKg; }

    public BigDecimal getValorFrete() { return valorFrete; }
    public void setValorFrete(BigDecimal valorFrete) { this.valorFrete = valorFrete; }

    public StatusEntrega getStatus() { return status; }
    public void setStatus(StatusEntrega status) { this.status = status; }

    public LocalDate getDataPrevista() { return dataPrevista; }
    public void setDataPrevista(LocalDate dataPrevista) { this.dataPrevista = dataPrevista; }

    public LocalDateTime getDataEntrega() { return dataEntrega; }
    public void setDataEntrega(LocalDateTime dataEntrega) { this.dataEntrega = dataEntrega; }

    public Motorista getMotorista() { return motorista; }
    public void setMotorista(Motorista motorista) { this.motorista = motorista; }

    public Veiculo getVeiculo() { return veiculo; }
    public void setVeiculo(Veiculo veiculo) { this.veiculo = veiculo; }

    public LocalDateTime getCriadoEm() { return criadoEm; }
    public LocalDateTime getAtualizadoEm() { return atualizadoEm; }
}
