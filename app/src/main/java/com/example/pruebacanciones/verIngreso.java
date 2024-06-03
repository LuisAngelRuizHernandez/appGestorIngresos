package com.example.pruebacanciones;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.pruebacanciones.OpenHelper.SQLlite_OpenHelper;

public class verIngreso extends AppCompatActivity {

    private EditText editNombre, editValor, editFuente;
    private Button btnGuardar;
    private int ingresoId;
    private SQLlite_OpenHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_ver_ingreso);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        editNombre = findViewById(R.id.editNombreIngreso);
        editValor = findViewById(R.id.editValorIngreso);
        editFuente = findViewById(R.id.editFuenteIngreso);
        btnGuardar = findViewById(R.id.btnGuardarCambios);

        ingresoId = getIntent().getIntExtra("INGRESO_ID", -1);
        dbHelper = new SQLlite_OpenHelper(this, "BD1", null, 5);

        // Rellenar los campos con los datos actuales
        editNombre.setText(getIntent().getStringExtra("NOMBRE"));
        editValor.setText(getIntent().getStringExtra("VALOR"));
        editFuente.setText(getIntent().getStringExtra("FUENTE"));


        btnGuardar.setOnClickListener(v -> guardarCambios());

    }
    // guardar cambios al modificar inrgreso
    private void guardarCambios() {
        String nuevoNombre = editNombre.getText().toString();
        double nuevoValor = Double.parseDouble(editValor.getText().toString());
        String nuevaFuente = editFuente.getText().toString();

        boolean actualizado = dbHelper.actualizarIngreso(ingresoId, nuevoNombre, nuevoValor, nuevaFuente);
        if (actualizado) {
            Toast.makeText(this, "Ingreso actualizado", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Error al actualizar el ingreso", Toast.LENGTH_SHORT).show();
        }
    }
}
