package com.example.pruebacanciones;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.pruebacanciones.OpenHelper.SQLlite_OpenHelper;

public class verGasto extends AppCompatActivity {

    private EditText editNombre, editDescripcion;
    private Button btnGuardarGasto;
    private int ingresoId;
    private SQLlite_OpenHelper dbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_ver_gasto);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        editNombre = findViewById(R.id.editNombreGasto);
        editDescripcion = findViewById(R.id.editDescripcionGasto);
        btnGuardarGasto = findViewById(R.id.btnGuardarCambiosGasto); // guardar cambios


        ingresoId = getIntent().getIntExtra("INGRESO_ID", -1);
        dbHelper = new SQLlite_OpenHelper(this, "BD1", null, 5);

        // Rellenar los campos con los datos actuales
        editNombre.setText(getIntent().getStringExtra("nombreGasto"));
        editDescripcion.setText(getIntent().getStringExtra("descripcionGasto"));

        // puntero para guardar cambios
        btnGuardarGasto.setOnClickListener(v -> guardarCambiosGastos());
    }
        // guardar cambios al modificar gasto
    private void guardarCambiosGastos() {
        String nuevoNombre = editNombre.getText().toString();
        String nuevaDescripcion = editDescripcion.getText().toString();

        boolean actualizado = dbHelper.actualizarGasto(ingresoId, nuevoNombre, nuevaDescripcion);
        if (actualizado) {
            Toast.makeText(this, "Ingreso actualizado", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Error al actualizar el ingreso", Toast.LENGTH_SHORT).show();
        }
    }
}