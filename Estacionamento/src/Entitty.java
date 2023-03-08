import java.time.LocalDateTime;

public abstract class Entitty {
    protected Long id;
    public LocalDateTime cadastro;
    public LocalDateTime edicao;
    public boolean ativo;

    public Entitty(){
        this.id = id;
        this.cadastro = cadastro;
        this.edicao = edicao;
        this.ativo = ativo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getCadastro() {
        return cadastro;
    }

    public void setCadastro(LocalDateTime cadastro) {
        this.cadastro = cadastro;
    }

    public LocalDateTime getEdicao() {
        return edicao;
    }

    public void setEdicao(LocalDateTime edicao) {
        this.edicao = edicao;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }
}
