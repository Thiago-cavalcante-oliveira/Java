package org.example;

public class Canario implements Animal{
    @Override
    public String comer(String periodo) {
        if (periodo == "manha"){
            return "semente";
        } else if (periodo == "tarde") {
            return "insetos";
        }else return "frutas";
    }

    @Override
    public String som() {
        return "Piu piu";
    }

    @Override
    public String especie() {
        return "Psitacídeo";
    }
}
