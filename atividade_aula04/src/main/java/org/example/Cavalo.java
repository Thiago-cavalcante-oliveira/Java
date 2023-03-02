package org.example;

public class Cavalo implements Animal{
    @Override
    public String comer(String periodo) {
        if(periodo == "manha"){
            return "Feno";
        } else if (periodo == "tarde") {
            return "capim";
        }else
            return "Ração";

    }

    @Override
    public String som() {
        return "Relincho";
    }

    @Override
    public String especie() {
        return "Equino";
    }
}
