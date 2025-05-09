package com.example.geometria;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    EditText etX1, etY1, etX2, etY2;
    TextView tvResultado;
    Button btnPendiente, btnPuntoMedio, btnEcuacion, btnCuadrantes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etX1 = findViewById(R.id.etX1);
        etY1 = findViewById(R.id.etY1);
        etX2 = findViewById(R.id.etX2);
        etY2 = findViewById(R.id.etY2);
        tvResultado = findViewById(R.id.tvResultado);

        btnPendiente = findViewById(R.id.btnPendiente);
        btnPuntoMedio = findViewById(R.id.btnPuntoMedio);
        btnEcuacion = findViewById(R.id.btnEcuacion);
        btnCuadrantes = findViewById(R.id.btnCuadrantes);

        btnPendiente.setOnClickListener(v -> calcularPendiente());
        btnPuntoMedio.setOnClickListener(v -> calcularPuntoMedio());
        btnEcuacion.setOnClickListener(v -> calcularEcuacion());
        btnCuadrantes.setOnClickListener(v -> calcularCuadrantes());

        registerForContextMenu(etX1);
        registerForContextMenu(etY1);
        registerForContextMenu(etX2);
        registerForContextMenu(etY2);
    }

    private boolean validarCampos() {
        return !etX1.getText().toString().trim().isEmpty() &&
                !etY1.getText().toString().trim().isEmpty() &&
                !etX2.getText().toString().trim().isEmpty() &&
                !etY2.getText().toString().trim().isEmpty();
    }

    private int getInt(EditText et) {
        return Integer.parseInt(et.getText().toString());
    }

    private void mostrarResultados(String mensaje) {
        Intent intent = new Intent(this, ResultadosActivity.class);

        intent.putExtra("resultado", mensaje);
        startActivity(intent);
    }

    private void calcularPendiente() {
        if (!validarCampos()) {
            tvResultado.setText("Por favor, completa todos los campos.");
            return;
        }
        try {
            int x1 = getInt(etX1), y1 = getInt(etY1), x2 = getInt(etX2), y2 = getInt(etY2);
            if (x2 == x1) {
                mostrarResultados("Pendiente indefinida (división por cero)");
            } else {
                double m = (double)(y2 - y1) / (x2 - x1);
                mostrarResultados("Pendiente: " + m);
            }
        } catch (NumberFormatException e) {
            tvResultado.setText("Datos inválidos. Usa solo números.");
        }
    }


    private void calcularPuntoMedio() {
        if (!validarCampos()) return;
        try {
            int x1 = getInt(etX1), y1 = getInt(etY1), x2 = getInt(etX2), y2 = getInt(etY2);
            double xm = (x1 + x2) / 2.0;
            double ym = (y1 + y2) / 2.0;
            mostrarResultados("Punto medio: (" + xm + ", " + ym + ")");
        } catch (NumberFormatException e) {
            tvResultado.setText("Datos inválidos. Usa solo números.");
        }
    }

    private void calcularEcuacion() {
        if (!validarCampos()) return;
        try {
            int x1 = getInt(etX1), y1 = getInt(etY1), x2 = getInt(etX2), y2 = getInt(etY2);
            if (x2 == x1) {
                mostrarResultados("Ecuación: x = " + x1);
            } else {
                double m = (double)(y2 - y1) / (x2 - x1);
                double b = y1 - m * x1;
                mostrarResultados(String.format("Ecuación: y = %.2fx + %.2f", m, b));
            }
        } catch (NumberFormatException e) {
            tvResultado.setText("Datos inválidos. Usa solo números.");
        }
    }

    private void calcularCuadrantes() {
        if (!validarCampos()) return;
        try {
            int x1 = getInt(etX1), y1 = getInt(etY1), x2 = getInt(etX2), y2 = getInt(etY2);
            String q1 = cuadrante(x1, y1);
            String q2 = cuadrante(x2, y2);
            mostrarResultados("Punto 1: " + q1 + "\nPunto 2: " + q2);
        } catch (NumberFormatException e) {
            tvResultado.setText("Datos inválidos. Usa solo números.");
        }
    }

    private String cuadrante(int x, int y) {
        if (x > 0 && y > 0) return "Primer cuadrante";
        else if (x < 0 && y > 0) return "Segundo cuadrante";
        else if (x < 0 && y < 0) return "Tercer cuadrante";
        else if (x > 0 && y < 0) return "Cuarto cuadrante";
        else return "Sobre eje";
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("Opciones");
        menu.add(0, v.getId(), 0, "Generar aleatorio");
        menu.add(0, v.getId(), 1, "Cambiar signo");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        View view = findViewById(item.getItemId());

        if (view instanceof EditText) {
            EditText editText = (EditText) view;
            String valorStr = editText.getText().toString();

            switch (item.getOrder()) {
                case 0:
                    int numAleatorio = new Random().nextInt(41) - 20;
                    editText.setText(String.valueOf(numAleatorio));
                    break;
                case 1:
                    if (!valorStr.isEmpty()) {
                        int valor = Integer.parseInt(valorStr);
                        editText.setText(String.valueOf(-valor));
                    }
                    break;
            }
        }

        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.layout_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int x1 = 0, y1 = 0, x2 = 0, y2 = 0;

        int id = item.getItemId();

        if (id == R.id.menu_q1) {
            // Primer cuadrante: x > 0, y > 0
            x1 = randomInRange(1, 20);
            y1 = randomInRange(1, 20);
            x2 = randomInRange(1, 20);
            y2 = randomInRange(1, 20);
        } else if (id == R.id.menu_q2) {
            // Segundo cuadrante: x < 0, y > 0
            x1 = randomInRange(-20, -1);
            y1 = randomInRange(1, 20);
            x2 = randomInRange(-20, -1);
            y2 = randomInRange(1, 20);
        } else if (id == R.id.menu_q3) {
            // Tercer cuadrante: x < 0, y < 0
            x1 = randomInRange(-20, -1);
            y1 = randomInRange(-20, -1);
            x2 = randomInRange(-20, -1);
            y2 = randomInRange(-20, -1);
        } else if (id == R.id.menu_q4) {
            // Cuarto cuadrante: x > 0, y < 0
            x1 = randomInRange(1, 20);
            y1 = randomInRange(-20, -1);
            x2 = randomInRange(1, 20);
            y2 = randomInRange(-20, -1);
        }

        etX1.setText(String.valueOf(x1));
        etY1.setText(String.valueOf(y1));
        etX2.setText(String.valueOf(x2));
        etY2.setText(String.valueOf(y2));

        return super.onOptionsItemSelected(item);
    }
    private int randomInRange(int min, int max) {
        return new Random().nextInt(max - min + 1) + min;
    }
}
