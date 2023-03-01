package org.example;

public abstract class Veiculo {
    private int qtdePorta;
    private String tipoMotor;
    private int qtdePassageiros;

    public int getQtdePorta() {
        return qtdePorta;
    }

    public void setQtdePorta(int qtdePorta) {
        this.qtdePorta = qtdePorta;
    }

    public String getTipoMotor() {
        return tipoMotor;
    }

    public void setTipoMotor(String tipoMotor) {
        this.tipoMotor = tipoMotor;
    }

    public int getQtdePassageiros() {
        return qtdePassageiros;
    }

    public void setQtdePassageiros(int qtdePassageiros) {
        this.qtdePassageiros = qtdePassageiros;
    }

    public String velocidade(){
        return "Veloidade do Veículo é:" + 125 "km/h";
    }
    public String buzina(){
        return "Bip Bip veículo";
    }



}
