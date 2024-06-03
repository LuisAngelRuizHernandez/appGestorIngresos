package com.example.pruebacanciones;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pruebacanciones.OpenHelper.SQLlite_OpenHelper;
import com.example.pruebacanciones.adaptadores.listaGastoAdapter;
import com.example.pruebacanciones.entidades.Gasto;

import java.util.ArrayList;

public class mostrarGasto extends AppCompatActivity {
    RecyclerView listaGasto;
    private SQLlite_OpenHelper dbHelper;
    private int userId;
    ArrayList<Gasto> listaGastos;
   listaGastoAdapter adapter; // Adaptador para la lista de gastos

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_mostrar_gasto);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Inicializar el RecyclerView
        listaGasto = findViewById(R.id.listaGastoView);
        listaGasto.setLayoutManager(new LinearLayoutManager(this));

        // Inicializar el DBHelper
        dbHelper = new SQLlite_OpenHelper(this, "BD1", null, 5);

        // Obtener el userId de los extras del Intent
        userId = getIntent().getIntExtra("USER_ID", -1);

        // Obtener la lista de gastos del DBHelper
        listaGastos = new ArrayList<>();
        listaGastos.addAll(dbHelper.mostrarGastos(userId));

        // Inicializar el adaptador con el contexto y dbHelper
        adapter = new listaGastoAdapter(listaGastos, this, dbHelper);

        // Asignar el adaptador al RecyclerView
        listaGasto.setAdapter(adapter);
    }
}
