import java.time.LocalDateTime;
import java.time.LocalTime;

public class Condutor extends Entitty{
    private String nome;
    private String cpf;
    private String telefone;
    private LocalTime tempoPago;
    private LocalTime tempoDesconto;

    public Condutor (Long id, LocalDateTime criacao, LocalDateTime edicao,
                     boolean ativo, String nome, String cpf, String telefone, LocalTime tempoPago, LocalTime tempoDesconto){
        super(id, criacao, edicao, ativo);
        this.nome = nome;
        this.cpf = cpf;
        this.telefone = telefone;
        this.tempoPago = tempoPago;
        this.tempoDesconto = tempoDesconto;
    }
    public void setNome(String nome){
        this.nome = nome;
    }
    public void setCpf(String cpf){
        this.cpf = cpf;
    }
    public void setTelefone(String telefone){
        this.telefone = telefone;
    }
    public void setTempoPago(LocalTime tempoPago){
        this.tempoPago = tempoPago;
    }
    public void setTempoDesconto(LocalTime tempoDesconto){
        this.tempoDesconto = tempoDesconto;
    }

    public String getNome() {
        return nome;
    }

    public String getCpf() {
        return cpf;
    }

    public String getTelefone() {
        return telefone;
    }

    public LocalTime getTempoPago() {
        return tempoPago;
    }

    public LocalTime getTempoDesconto() {
        return tempoDesconto;
    }
}
