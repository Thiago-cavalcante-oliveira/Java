package org.example;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        String nome = "Teste";

        Integer total = 10;
        int totalprimitivo = 30;
        LocalDate data = LocalDate.now();
        LocalTime horaentrada = LocalTime.now();
        LocalTime horasaida = LocalTime.now();

        System.out.println("Hello world!");
        System.out.println(nome);
        System.out.println(total);
        System.out.println(totalprimitivo);
        System.out.println(totalprimitivo+total);
        System.out.println(data);
        System.out.println(horasaida);


        int idade = 30;

        if(idade < 10) {
            System.out.println("criança");
        } else if (idade >10 && idade <= 20) {
            System.out.println("Adolescente");
        } else if (idade >20 && idade<50) {
        System.out.println("Adulto");

        }




    }
    }
