package org.example;

public class Gato implements Animal {
    @Override
    public String comer(String periodo) {

        if (periodo == "manha"){
            return "peixe";
        } else if (periodo == "tarde") {
            return "ração";
        }else return "brócolis";


    }

    @Override
    public String som() {
        return "miau";
    }

    @Override
    public String especie() {
        return "felina";
    }
}
