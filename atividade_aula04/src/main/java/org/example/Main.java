package org.example;

public class Main {
    public static void main(String[] args) {

    Cachorro cachorro = new Cachorro();
    Gato gato = new Gato();
    Cavalo cavalo = new Cavalo();
    Canario canario = new Canario();
    Galo galo = new Galo();

    System.out.println("O cachorro come" + cachorro.comer("manha"));
    System.out.println(cachorro.som());
    System.out.println(cachorro.especie());
    System.out.println("\n");

    System.out.println(gato.comer("manha"));
    System.out.println(gato.som());
    System.out.println(gato.especie());
    System.out.println("\n");

    System.out.println(cavalo.comer("manha"));
    System.out.println(cavalo.som());
    System.out.println(cavalo.especie());
    System.out.println("\n");

    System.out.println(canario.comer("TÃrdé"));
    System.out.println(canario.som());
    System.out.println(canario.especie());
    System.out.println("\n");

    System.out.println(galo.comer("manha"));
    System.out.println(galo.som());
    System.out.println(galo.especie());
    System.out.println("\n");

    }
}