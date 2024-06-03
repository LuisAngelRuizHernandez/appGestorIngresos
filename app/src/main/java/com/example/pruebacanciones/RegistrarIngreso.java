package com.example.pruebacanciones;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.pruebacanciones.OpenHelper.SQLlite_OpenHelper;

public class RegistrarIngreso extends AppCompatActivity {

    SQLlite_OpenHelper helper; // referencia de la bd
    int userId; // ID del usuario, se asume que se pasa a esta actividad

    Button cancelarBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registrar_ingreso);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Obtener el ID del usuario del Intent
        userId = getIntent().getIntExtra("USER_ID", -1);

        helper = new SQLlite_OpenHelper(this, "BD1", null, 2); // instancia de la bd

        // referencia de los botones
        EditText nombreIngreso = findViewById(R.id.nombreIngresoTxtRegIngre);
        EditText valorIngreso = findViewById(R.id.valorIngresoTxtRegIngre);
        EditText descripcionIngreso = findViewById(R.id.descripcionIngresoTxtRegIngre);
        EditText fuenteIngreso = findViewById(R.id.fuenteIngresoTxtRegIngre);
        Button registrarIngresoBtn = findViewById(R.id.registrarIngresoBtnDesdeRegIngre);

        // presionar button registrar ingreso
         registrarIngresoBtn.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 // convertirlo a txt
                 String nombre = nombreIngreso.getText().toString();
                 String valorStr = valorIngreso.getText().toString();
                 String descripcion = descripcionIngreso.getText().toString();
                 String fuente = fuenteIngreso.getText().toString();

                 // completar todos los campos
                 if (nombre.isEmpty() || valorStr.isEmpty() || descripcion.isEmpty() || fuente.isEmpty()) {
                     Toast.makeText(RegistrarIngreso.this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
                 } else {

                     int valor = Integer.parseInt(valorStr); // parsear valor
                     helper.insertarIngreso(userId, nombre, valor, fuente, descripcion); // ingresarlos en la bd por medio de la instancia
                     Toast.makeText(RegistrarIngreso.this, "Ingreso registrado con éxito", Toast.LENGTH_SHORT).show();

                     // Limpiar los campos después de registrar el ingreso
                     nombreIngreso.setText("");
                     valorIngreso.setText("");
                     descripcionIngreso.setText("");
                     fuenteIngreso.setText("");
                 }
             }
         });

         cancelarBtn = (Button) findViewById(R.id.cancelarBtnDesdeRegIngre);
         // presionar el button de cancelar ingreso
         cancelarBtn.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 new AlertDialog.Builder(RegistrarIngreso.this)
                             .setMessage("¿Desea cancelar?") // mensaje si desea cancelar o continuar
                         .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                             @Override
                             public void onClick(DialogInterface dialog, int which) { // regresar a la activity gestionar ingreso
                                 // Ir a la actividad GestionarIngreso
                                 Intent intent = new Intent(RegistrarIngreso.this, GestionarIngreso.class);
                                 startActivity(intent);
                                 finish(); // opcional: cerrar la actividad actual
                             }
                         })
                         .setNegativeButton("No", null)
                         .show();
             }
         });
    }
}