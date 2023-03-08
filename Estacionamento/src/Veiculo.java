import java.time.LocalDateTime;

public class Veiculo extends Entitty{
    private String placa;
    private Modelo modelo;
    private Cor cor;
    private Tipo tipo;
    private int ano;

    public Veiculo(Long id, LocalDateTime cadastro, LocalDateTime edicao,
                   boolean ativo, String placa, Modelo modelo, Cor cor, Tipo tipo, int ano) {
        super(id, cadastro, edicao, ativo);
        this.placa = placa;
        this.modelo = modelo;
        this.cor = cor;
        this.tipo = tipo;
        this.ano = ano;
    }
}


