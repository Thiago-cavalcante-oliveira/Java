package org.example;
import java.util.ArrayList;


public class Materia {
    private String materia;
    private double nota;



    public Materia(String materia, double nota) {

        this.materia = materia;
        this.nota = nota;

    }

    public String getMateria() {
        return materia;
    }

    public double getNota() {
        return nota;
    }

    public void setNota(double nota) {
        this.nota = nota;
    }

    public void setMateria(String materia) {
        this.materia = materia;
    }

    @Override
    public String toString() {
        return "Materia{" +
                "materia='" + materia + '\'' +
                ", nota=" + nota +
                '}';
    }
}