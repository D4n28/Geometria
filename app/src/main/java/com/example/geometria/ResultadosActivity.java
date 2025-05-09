package com.example.geometria;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ResultadosActivity extends AppCompatActivity {

    TextView tvDetalles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultados);

        tvDetalles = findViewById(R.id.tvDetalles);

        // Obtén el resultado desde el Intent
        String resultado = getIntent().getStringExtra("resultado");
        if (resultado != null) {
            tvDetalles.setText(resultado);
        }
    }
}