package com.example.pruebacanciones;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.pruebacanciones.OpenHelper.SQLlite_OpenHelper;

public class InformeFinanciero extends AppCompatActivity {

    private SQLlite_OpenHelper dbHelper;
    private TextView totalGastosTextView, totalIngresosTextView, saldoActualTextView;
    private int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_informe_financiero);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Obtener referencias a los TextViews
        totalGastosTextView = findViewById(R.id.totalGastosTextView);
        totalIngresosTextView = findViewById(R.id.totalIngresosTextView);
        saldoActualTextView = findViewById(R.id.saldoActualTextView);

        // Obtener el ID del usuario (puedes obtenerlo del intent o de las preferencias compartidas)
        userId = getIntent().getIntExtra("USER_ID", -1);

        // Inicializar el helper de la base de datos
        dbHelper = new SQLlite_OpenHelper(this, "BD1", null, 5);

        // Obtener los totales y mostrar en los TextViews
        mostrarInformeFinanciero();
    }
    private void mostrarInformeFinanciero() {
        double totalGastos = dbHelper.obtenerTotalGastos(userId);
        double totalIngresos = dbHelper.obtenerTotalIngresos(userId);
        double saldoActual = totalIngresos - totalGastos; // restar el total de ingresos con el total de gastos

        totalGastosTextView.setText("Total Gastos: " + totalGastos);
        totalIngresosTextView.setText("Total Ingresos: " + totalIngresos);
        saldoActualTextView.setText("Saldo Actual: " + saldoActual);
    }
    }
