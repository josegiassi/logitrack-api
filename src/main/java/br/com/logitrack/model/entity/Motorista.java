package br.com.logitrack.model.entity;

import br.com.logitrack.model.enums.CategoriaCnh;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "motorista")
public class Motorista {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(nullable = false, unique = true, length = 11)
    private String cpf;

    @Column(nullable = false, unique = true, length = 11)
    private String cnh;

    @Enumerated(EnumType.STRING)
    @Column(name = "categoria_cnh", nullable = false, length = 1)
    private CategoriaCnh categoriaCnh;

    @Column(nullable = false, length = 11)
    private String telefone;

    @Column(nullable = false, unique = true, length = 120)
    private String email;

    @Column(nullable = false)
    private boolean ativo = true;

    @Column(name = "criado_em", nullable = false, updatable = false)
    private LocalDateTime criadoEm;

    @Column(name = "atualizado_em", nullable = false)
    private LocalDateTime atualizadoEm;

    /** Lado inverso do relacionamento 1:N com Entrega. */
    @OneToMany(mappedBy = "motorista")
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

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }

    public String getCnh() { return cnh; }
    public void setCnh(String cnh) { this.cnh = cnh; }

    public CategoriaCnh getCategoriaCnh() { return categoriaCnh; }
    public void setCategoriaCnh(CategoriaCnh categoriaCnh) { this.categoriaCnh = categoriaCnh; }

    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public boolean isAtivo() { return ativo; }
    public void setAtivo(boolean ativo) { this.ativo = ativo; }

    public LocalDateTime getCriadoEm() { return criadoEm; }
    public LocalDateTime getAtualizadoEm() { return atualizadoEm; }

    public List<Entrega> getEntregas() { return entregas; }
}
