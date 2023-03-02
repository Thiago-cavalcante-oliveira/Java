package org.example;

public class Galo implements Animal{
    @Override
    public String comer(String periodo) {
        if (periodo == "manha"){
            return "milho";
        } else if (periodo == "tarde") {
            return "ração";
        }else return "inseto";


    }

    @Override
    public String som() {
        return "cocorico";
    }

    @Override
    public String especie() {
        return "Galinaceo";
    }
}
