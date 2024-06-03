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
import com.example.pruebacanciones.adaptadores.listaIngresoAdapter;
import com.example.pruebacanciones.entidades.Ingreso;

import java.util.ArrayList;

public class mostrarIngreso extends AppCompatActivity {
    RecyclerView listaIngreso;
    private SQLlite_OpenHelper dbHelper;
    private int userId;
    ArrayList<Ingreso> listaIngresos;
    listaIngresoAdapter adapter; // Adaptador para la lista de ingresos

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_mostrar_ingreso);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Inicializar el RecyclerView
        listaIngreso = findViewById(R.id.listaIngresosView);
        listaIngreso.setLayoutManager(new LinearLayoutManager(this));

        // Inicializar el DBHelper
        dbHelper = new SQLlite_OpenHelper(this, "BD1", null, 2);

        // Obtener el userId de los extras del Intent
        userId = getIntent().getIntExtra("USER_ID", -1);

        // Obtener la lista de ingresos del DBHelper
        listaIngresos = new ArrayList<>();
        listaIngresos.addAll(dbHelper.mostrarIngresos(userId));

        // Inicializar el adaptador con el contexto y dbHelper
        adapter = new listaIngresoAdapter(listaIngresos, this, dbHelper);

        // Asignar el adaptador al RecyclerView
        listaIngreso.setAdapter(adapter);
    }
}
