package org.example;

import java.util.ArrayList;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

public class Alunos {
    private String nome;
    private Integer idade;
    private String turma;
    private String aprovacao;
    private List<Materia>  materias;

    public Alunos(String nome, Integer idade, String turma, List<Materia> materias){
        this.nome = nome;
        this.idade = idade;
        this.turma = turma;
        this.materias = materias;
        //this.aprovacao = calculaAprovacao();
       // for (Materia valor : materias) {
       //     this.materias.add(valor);
       // }

    }

    public Integer getIdade() {
        return idade;
    }
    public String getNome() {
        return nome;
    }
    public String getTurma(){
        return turma;
    }
    public String getAprovacao(){
        return aprovacao;
    }

    public List<Materia> getMateria() {
        return materias;
    }

    public void setMaterias(List<Materia> materias) {
        this.materias = materias;
    }

private String calculaAprovacao(){
            if(materias.getClass(i) < 50){
                return "Reprovado";
            } else if (nota > 50 || nota < 70) {
                return "Em recuperção";
            }else {
                return "Aprovado";
            }
    }

   /* public void imprimelistamaterias (){
        for (Materia valor: materias)
              {
            System.out.println(valor);
        }*/
   // }
    public static void main(String[] args) {

        ArrayList<Materia> materias =  new ArrayList<Materia>();
        materias.add(new Materia("Ciências", 75));
        materias.add( new Materia("Matemática", 80));
        materias.add(new Materia("História", 49));
        materias.add(new Materia("Artes",23));

        ArrayList<Alunos> alunos = new ArrayList<>();

        alunos.add(new Alunos("João", 18, "A", materias));

       alunos.add(new Alunos("Maria", 17, "B", materias));
        alunos.add(new Alunos("Pedro", 16, "A", materias));
        alunos.add(new Alunos("Ana", 18, "B", materias));
        alunos.add(new Alunos("Mario", 14, "C", materias));
        alunos.add(new Alunos("Joana", 23, "C", materias));

/*
        alunos.add (new Alunos("Thiago", 37, "A", 93));
        alunos.add(new Alunos("Andre", 43, "B", 68));
        alunos.add(new Alunos("Ana", 22, "B", 43));
        alunos.add(new Alunos("Jairo", 32, "C", 87));
        alunos.add(new Alunos("João", 18, "A", 75));
        alunos.add(new Alunos("Maria", 17, "B", 60));
        alunos.add(new Alunos("Pedro", 16, "A", 40));
        alunos.add(new Alunos("Ana", 18, "B", 85));
        alunos.add(new Alunos("Mario", 14, "C", 63));
        alunos.add(new Alunos("Joana", 23, "C", 23));
        alunos.add (new Alunos("Thiago", 37, "A", 93));
        alunos.add(new Alunos("Andre", 43, "B", 68));
        alunos.add(new Alunos("Ana", 22, "B", 43));
        alunos.add(new Alunos("Jairo", 32, "C", 87));*/


       // Alunos alunos1= alunos.get(0).getMateria().get(0).getMateria();

        for (Alunos aluno : alunos) {
            for (Materia materia: materias){

            }
            for(int i=0;i< materias.size();i++) {

                private String calculaAprovacao(){
                    if(aluno.getMateria().get(i).getNota()< 50){
                        return "Reprovado";
                    } else if (aluno.getMateria().get(i).getNota() > 50 || aluno.getMateria().get(i).getNota() < 70) {
                        return "Em recuperção";
                    }else {
                        return "Aprovado";
                    }
                }
                System.out.println(aluno.getNome() + " - " + aluno.getMateria().get(i).getMateria() +
                        "-" + aluno.getMateria().get(i).getNota());
            }
        }


}
}
