package com.example.pruebacanciones;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Principal extends AppCompatActivity {
    Button gestionarIngresoBtn, gestionarGastoBtn, mostrarInformeFinancieroBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_principal);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        gestionarIngresoBtn = (Button) findViewById(R.id.GestionarIngresosDesPrinci);
        // mandar a la activity gestionar ingreso
        gestionarIngresoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Principal", "Botón Gestionar Ingreso presionado");
                startActivity(new Intent(Principal.this, GestionarIngreso.class));// mover a clase Gestionar ingreso
            }
        });

        gestionarGastoBtn = (Button) findViewById(R.id.gestionarGastoDesPrinci);
        gestionarGastoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Principal.this, GestionarGasto.class)); // mover a clase Gestionar gasto
            }
        });

        mostrarInformeFinancieroBtn = (Button) findViewById(R.id.mostrarInformeFinancieroBtnDesdePrincipal);
        mostrarInformeFinancieroBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Principal.this, InformeFinanciero.class)); // mandar para el informe financiero
            }
        });
    }
}