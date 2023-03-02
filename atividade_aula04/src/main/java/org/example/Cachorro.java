package org.example;

public class Cachorro implements Animal {

    @Override
    public String comer(String periodo) {

        if(periodo == "manha"){
            return "Ração";
        } else if (periodo == "tarde") {
        }else {
            return "osso";
        }

        return null;
    }

    @Override
    public String som() {
        return "au au";
    }

    @Override
    public String especie() {
        return "Canideo";
    }



}
