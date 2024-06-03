package com.example.pruebacanciones;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class GestionarGasto extends AppCompatActivity {

    Button registrarGasto, mostrarGastoBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_gestionar_gasto);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        mostrarGastoBtn = (Button) findViewById(R.id.mostrarGastosDesdeGestionarGasto);
        mostrarGastoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GestionarGasto.this, mostrarGasto.class)); // mover a mostrar gasto
            }
        });

        registrarGasto = (Button) findViewById(R.id.registrarGastoDesdeGestionGasto);
        registrarGasto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GestionarGasto.this, RegistrarGasto.class)); // mover a registrar gasto
            }
        });
    }
}