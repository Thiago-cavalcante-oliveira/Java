import java.math.BigDecimal;
import java.time.LocalTime;

public class Configuracao {
    private BigDecimal valorHora;
    private BigDecimal valorMinutoHora;
    private LocalTime inicioExpediente;
    private LocalTime fimExpediente;
    private LocalTime tempoParaDesconto;
    private LocalTime tempoDeDesconto;
    private boolean gerarDesconto;
    private int vagasMoto;
    private int vagasCarro;
    private int vagasVan;

    public BigDecimal getValorHora() {
        return valorHora;
    }

    public void setValorHora(BigDecimal valorHora) {
        this.valorHora = valorHora;
    }

    public BigDecimal getValorMinutoHora() {
        return valorMinutoHora;
    }

    public void setValorMinutoHora(BigDecimal valorMinutoHora) {
        this.valorMinutoHora = valorMinutoHora;
    }

    public LocalTime getInicioExpediente() {
        return inicioExpediente;
    }

    public void setInicioExpediente(LocalTime inicioExpediente) {
        this.inicioExpediente = inicioExpediente;
    }

    public LocalTime getFimExpediente() {
        return fimExpediente;
    }

    public void setFimExpediente(LocalTime fimExpediente) {
        this.fimExpediente = fimExpediente;
    }

    public LocalTime getTempoParaDesconto() {
        return tempoParaDesconto;
    }

    public void setTempoParaDesconto(LocalTime tempoParaDesconto) {
        this.tempoParaDesconto = tempoParaDesconto;
    }

    public LocalTime getTempoDeDesconto() {
        return tempoDeDesconto;
    }

    public void setTempoDeDesconto(LocalTime tempoDeDesconto) {
        this.tempoDeDesconto = tempoDeDesconto;
    }

    public boolean isGerarDesconto() {
        return gerarDesconto;
    }

    public void setGerarDesconto(boolean gerarDesconto) {
        this.gerarDesconto = gerarDesconto;
    }

    public int getVagasMoto() {
        return vagasMoto;
    }

    public void setVagasMoto(int vagasMoto) {
        this.vagasMoto = vagasMoto;
    }

    public int getVagasCarro() {
        return vagasCarro;
    }

    public void setVagasCarro(int vagasCarro) {
        this.vagasCarro = vagasCarro;
    }

    public int getVagasVan() {
        return vagasVan;
    }

    public void setVagasVan(int vagasVan) {
        this.vagasVan = vagasVan;
    }

    public Configuracao(BigDecimal valorHora, BigDecimal valorMinutoHora, LocalTime inicioExpediente, LocalTime fimExpediente,
                        LocalTime tempoParaDesconto, LocalTime tempoDeDesconto, boolean gerarDesconto, int vagasMoto, int vagasCarro, int vagasVan){
        this.valorHora = valorHora;
        this.valorMinutoHora = valorMinutoHora;
        this.inicioExpediente = inicioExpediente;
        this.fimExpediente = fimExpediente;
        this.tempoParaDesconto = tempoParaDesconto;
        this.tempoDeDesconto = tempoDeDesconto;
        this.gerarDesconto = gerarDesconto;
        this.vagasMoto = vagasMoto;
        this.vagasCarro = vagasCarro;
        this.vagasVan = vagasVan;




    }

}
