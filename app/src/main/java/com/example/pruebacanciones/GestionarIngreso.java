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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pruebacanciones.OpenHelper.SQLlite_OpenHelper;
import com.example.pruebacanciones.entidades.Ingreso;

import java.util.List;

public class GestionarIngreso extends AppCompatActivity {

    Button registroIngresobtn, mostrarIngresobtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_gestionar_ingreso);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        registroIngresobtn = (Button) findViewById(R.id.registrarIngresoBtnDesdeIngreso);
        // mandar a registrar ingreso
        registroIngresobtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GestionarIngreso.this, RegistrarIngreso.class)); // mandar al otro activity de gestionar ingreso
            }
        });

        mostrarIngresobtn = (Button) findViewById(R.id.mostrarIngresoBtnDesdeIngreso);
        mostrarIngresobtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GestionarIngreso.this, mostrarIngreso.class)); // mandar al otro activity de gestionar ingreso
            }
        });

    }
}