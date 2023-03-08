import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Movimentacao extends Entitty {

    private Veiculo veiculo;
    private Condutor condutor;
    private LocalDateTime entrada;
    private LocalDateTime saida;
    private LocalTime tempo;
    private LocalTime tempoDesconto;
    private LocalTime tempoMulta;
    private BigDecimal valorDesconto;
    private BigDecimal valorMulta;
    private BigDecimal valorTotal;
    private BigDecimal valorHora;
    private BigDecimal valorHoraMulta;

    public Movimentacao(Long id, LocalDateTime cadastro, LocalDateTime edicao, boolean ativo, Veiculo veiculo, Condutor condutor, LocalDateTime entrada, LocalDateTime saida, LocalTime tempo, LocalTime tempoDesconto, LocalTime tempoMulta, BigDecimal valorDesconto, BigDecimal valorMulta, BigDecimal valorTotal, BigDecimal valorHora, BigDecimal valorHoraMulta) {
        super(id, cadastro, edicao, ativo);
        this.veiculo = veiculo;
        this.condutor = condutor;
        this.entrada = entrada;
        this.saida = saida;
        this.tempo = tempo;
        this.tempoDesconto = tempoDesconto;
        this.tempoMulta = tempoMulta;
        this.valorDesconto = valorDesconto;
        this.valorMulta = valorMulta;
        this.valorTotal = valorTotal;
        this.valorHora = valorHora;
        this.valorHoraMulta = valorHoraMulta;
    }

    public Veiculo getVeiculo() {
        return veiculo;
    }

    public void setVeiculo(Veiculo veiculo) {
        this.veiculo = veiculo;
    }

    public Condutor getCondutor() {
        return condutor;
    }

    public void setCondutor(Condutor condutor) {
        this.condutor = condutor;
    }

    public LocalDateTime getEntrada() {
        return entrada;
    }

    public void setEntrada(LocalDateTime entrada) {
        this.entrada = entrada;
    }

    public LocalDateTime getSaida() {
        return saida;
    }

    public void setSaida(LocalDateTime saida) {
        this.saida = saida;
    }

    public LocalTime getTempo() {
        return tempo;
    }

    public void setTempo(LocalTime tempo) {
        this.tempo = tempo;
    }

    public LocalTime getTempoDesconto() {
        return tempoDesconto;
    }

    public void setTempoDesconto(LocalTime tempoDesconto) {
        this.tempoDesconto = tempoDesconto;
    }

    public LocalTime getTempoMulta() {
        return tempoMulta;
    }

    public void setTempoMulta(LocalTime tempoMulta) {
        this.tempoMulta = tempoMulta;
    }

    public BigDecimal getValorDesconto() {
        return valorDesconto;
    }

    public void setValorDesconto(BigDecimal valorDesconto) {
        this.valorDesconto = valorDesconto;
    }

    public BigDecimal getValorMulta() {
        return valorMulta;
    }

    public void setValorMulta(BigDecimal valorMulta) {
        this.valorMulta = valorMulta;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    public BigDecimal getValorHora() {
        return valorHora;
    }

    public void setValorHora(BigDecimal valorHora) {
        this.valorHora = valorHora;
    }

    public BigDecimal getValorHoraMulta() {
        return valorHoraMulta;
    }

    public void setValorHoraMulta(BigDecimal valorHoraMulta) {
        this.valorHoraMulta = valorHoraMulta;
    }
}
