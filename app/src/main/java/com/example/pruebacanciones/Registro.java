package com.example.pruebacanciones;

import android.content.Intent;
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

public class Registro extends AppCompatActivity {
    Button btnRegister;
    EditText nombreTxtReg, apellidoTxtReg, correoTxtReg, passwordTxtReg;

    // contexto, nombreBd,null,version
    SQLlite_OpenHelper helper = new SQLlite_OpenHelper(this,"BD1",null,1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        btnRegister = (Button) findViewById(R.id.RegisterBtn);
        nombreTxtReg = findViewById(R.id.nombreTxtRegister);
        apellidoTxtReg = findViewById(R.id.apellidoTxtRegister);
        correoTxtReg = findViewById(R.id.correoTxtRegister);
        passwordTxtReg = findViewById(R.id.passwordTextRegister);

        // metodo para presionar
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                helper.abrir(); // usar la instancia de la bd para abrirla con su metodo
                helper.insertarReg(String.valueOf(nombreTxtReg.getText()), String.valueOf(apellidoTxtReg.getText()),String.valueOf(correoTxtReg.getText()),
                        String.valueOf(passwordTxtReg.getText())); // insertar registros
                helper.cerrar(); // cerrar bd

                Toast.makeText(getApplicationContext(), "Registro almacenado con exito", Toast.LENGTH_LONG).show(); // mensaje de texto

                Intent i = new Intent(getApplicationContext(), MainActivity.class); // mover al main (mandar al logeo una vez registrado)
                startActivity(i); // lanzar actividad
            }
        });
    }
}