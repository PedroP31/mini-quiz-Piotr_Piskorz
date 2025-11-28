package com.example.miniquiz;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {

    TextView tekstPytania, tekstWynik;
    Button przyciskStart, przyciskReset, przyciskA, przyciskB, przyciskC;

    ArrayList<Pytanie> listaPytan;
    int aktualnyIndeks = 0;
    int wynik = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tekstPytania = findViewById(R.id.tekstPytania);
        tekstWynik = findViewById(R.id.tekstWynik);

        przyciskStart = findViewById(R.id.przyciskStart);
        przyciskReset = findViewById(R.id.przyciskReset);
        przyciskA = findViewById(R.id.przyciskA);
        przyciskB = findViewById(R.id.przyciskB);
        przyciskC = findViewById(R.id.przyciskC);

        ukryjQuiz();

        przyciskStart.setOnClickListener(v -> {
            ustawQuiz();
            przyciskStart.setVisibility(View.GONE);
            pokazQuiz();
            pokazPytanie();
        });

        View.OnClickListener wyborOdpowiedzi = v -> {
            Button b = (Button)v;
            Pytanie pytanie = listaPytan.get(aktualnyIndeks);
            String wybrana = "";
            if(b==przyciskA) wybrana = pytanie.odpA;
            if(b==przyciskB) wybrana = pytanie.odpB;
            if(b==przyciskC) wybrana = pytanie.odpC;

            if(wybrana.equals(pytanie.poprawna)) wynik++;
            tekstWynik.setText("Wynik: " + wynik);

            aktualnyIndeks++;
            if(aktualnyIndeks >= 5) zakonczQuiz();
            else pokazPytanie();
        };

        przyciskA.setOnClickListener(wyborOdpowiedzi);
        przyciskB.setOnClickListener(wyborOdpowiedzi);
        przyciskC.setOnClickListener(wyborOdpowiedzi);

        przyciskReset.setOnClickListener(v -> {
            wynik = 0;
            aktualnyIndeks = 0;
            tekstWynik.setText("Wynik: 0");
            ukryjQuiz();
            przyciskStart.setVisibility(View.VISIBLE);
        });
    }

    void ustawQuiz() {
        listaPytan = new ArrayList<>();
        listaPytan.add(new Pytanie("Kto wygrał Złotą Piłkę w 2024 roku?", "Messi", "Haaland", "Mbappé", "Messi"));
        listaPytan.add(new Pytanie("Jakim wynikiem zakończył się mecz Polska – Holandia na Stadionie Narodowym?", "1:1", "2:0", "0:3", "1:1"));
        listaPytan.add(new Pytanie("Co jest stolicą Hiszpanii?", "Barcelona", "Madryt", "Paryż", "Madryt"));
        listaPytan.add(new Pytanie("2 + 2 * 2 = ?", "6", "8", "4", "6"));
        listaPytan.add(new Pytanie("Najwyższa góra świata?", "Everest", "K2", "Makalu", "Everest"));

        Collections.shuffle(listaPytan);
        listaPytan = new ArrayList<>(listaPytan.subList(0,5));
        aktualnyIndeks = 0;
        wynik = 0;
    }

    void pokazPytanie() {
        Pytanie pytanie = listaPytan.get(aktualnyIndeks);
        tekstPytania.setText(pytanie.text);
        przyciskA.setText(pytanie.odpA);
        przyciskB.setText(pytanie.odpB);
        przyciskC.setText(pytanie.odpC);
    }

    void zakonczQuiz() {
        new AlertDialog.Builder(this)
                .setTitle("Koniec quizu!")
                .setMessage("Twój wynik: " + wynik + " / 5")
                .setPositiveButton("OK", null)
                .show();
        ukryjQuiz();
        przyciskStart.setVisibility(View.VISIBLE);
    }

    void ukryjQuiz() {
        tekstPytania.setVisibility(View.GONE);
        przyciskA.setVisibility(View.GONE);
        przyciskB.setVisibility(View.GONE);
        przyciskC.setVisibility(View.GONE);
        tekstWynik.setVisibility(View.GONE);
        przyciskReset.setVisibility(View.GONE);
    }

    void pokazQuiz() {
        tekstPytania.setVisibility(View.VISIBLE);
        przyciskA.setVisibility(View.VISIBLE);
        przyciskB.setVisibility(View.VISIBLE);
        przyciskC.setVisibility(View.VISIBLE);
        tekstWynik.setVisibility(View.VISIBLE);
        przyciskReset.setVisibility(View.VISIBLE);
    }

    class Pytanie {
        String text, odpA, odpB, odpC, poprawna;
        Pytanie(String t, String a, String b, String c, String poprawna) {
            this.text = t; this.odpA = a; this.odpB = b; this.odpC = c; this.poprawna = poprawna;
        }
    }
}
