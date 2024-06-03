package com.example.pruebacanciones;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
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

import java.util.Date;

public class RegistrarGasto extends AppCompatActivity {

    SQLlite_OpenHelper helper; // referencia de la bd
    int userId; // ID del usuario, se asume que se pasa a esta actividad

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registrar_gasto);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Obtener el ID del usuario del Intent
        userId = getIntent().getIntExtra("USER_ID", -1);

        helper = new SQLlite_OpenHelper(this, "BD1", null, 5); // instancia de la bd

        // referencia de los botones
        EditText nombreGasto = findViewById(R.id.nombreGastoTxt);
        EditText valorGasto = findViewById(R.id.valorGastoTxt);
        EditText descripcionGasto = findViewById(R.id.descripcionGastoTxt);
        EditText fechaGasto = findViewById(R.id.fechaGastoTxt);
       Button registrarGastoBtn = (Button) findViewById(R.id.registrarGastoBtnDesdeRegistroGasto);
        Button cancelarGastoBtn = (Button) findViewById(R.id.cancelarGastoBtnDesdeRegistroGasto);

        registrarGastoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Convertirlo a txt
                String nombre = nombreGasto.getText().toString();
                String valorStr = valorGasto.getText().toString();
                String descripcion = descripcionGasto.getText().toString();
                String fechaStr = fechaGasto.getText().toString();

                // Completar todos los campos
                if (nombre.isEmpty() || valorStr.isEmpty() || descripcion.isEmpty() || fechaStr.isEmpty()) {
                    Toast.makeText(RegistrarGasto.this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        int valor = Integer.parseInt(valorStr); // Parsear valor
                        // No se necesita convertir fechaStr a Date, ya que se almacenará como String

                        SQLiteDatabase db = helper.getWritableDatabase();
                        if (db != null) {
                            helper.insertarGasto(nombre, fechaStr, valor, descripcion,userId); // Ingresarlos en la BD por medio de la instancia
                            Toast.makeText(RegistrarGasto.this, "Gasto registrado con éxito", Toast.LENGTH_SHORT).show();
                            // Limpiar los campos después de registrar el gasto
                            nombreGasto.setText("");
                            valorGasto.setText("");
                            descripcionGasto.setText("");
                            fechaGasto.setText("");
                        }else{
                            Toast.makeText(RegistrarGasto.this,"la bd es nula", Toast.LENGTH_SHORT).show();
                        }
                    } catch (NumberFormatException e) {
                        Toast.makeText(RegistrarGasto.this, "El valor del gasto debe ser un número", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        // presionar el button de cancelar gasto
        cancelarGastoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(RegistrarGasto.this)
                        .setMessage("¿Desea cancelar?") // mensaje si desea cancelar o continuar
                        .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) { // regresar a la activity gestionar gasto
                                // Ir a la actividad GestionarGasto (suponiendo que exista esta actividad)
                                Intent intent = new Intent(RegistrarGasto.this, GestionarGasto.class);
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

