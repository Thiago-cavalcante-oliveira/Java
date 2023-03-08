public class Modelo extends Marca{
    private String nome;
    private Marca marca;

    public Modelo(String nome, String nome1, Marca marca) {
        super(nome);
        this.nome = nome1;
        this.marca = marca;
    }

    public void setNome(String nome){
        this.nome = nome;
    }

    public void setMarca(Marca marca){
        this.marca = marca;
    }
    public String getNome(){
        return nome;
    }

    public Marca getMarca(){
        return marca;
    }
}
