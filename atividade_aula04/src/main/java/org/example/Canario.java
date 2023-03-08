package org.example;
import java.text.*;
import java.util.Locale;

public class Canario implements Animal{
    @Override
    public String comer(String periodo) {
        Collator collator = Collator.getInstance(new Locale("pt", "BR"));
        collator.setStrength(Collator.PRIMARY);
        if (collator.compare(periodo, "MANHÃ")==0){
            return "semente";
        } else if ((collator.compare(periodo, "TARDE")==0)) {
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
