package com.example.pruebacanciones;

import android.content.Intent;
import android.database.Cursor;
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

public class MainActivity extends AppCompatActivity {

    Button register, ingresar;

    SQLlite_OpenHelper helper = new SQLlite_OpenHelper(this,"BD1",null,2); // si cambio alguna estructura se sube la version

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        
        register = (Button) findViewById(R.id.registerBtnDesdeLogin); // boton register desde login
        ingresar = (Button) findViewById(R.id.loginBtnDesdeLogin); // boton login desde login

        // presionar button register
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Registro.class)); // mandar al otro activity
            }
        });

        // presinar button login
        ingresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText txtUsu = (EditText) findViewById(R.id.usuarioTxtLogin);
                EditText txtPass = (EditText) findViewById(R.id.passwordTxtLogin);

                Cursor cursor =helper.consultarUsuPass(txtUsu.getText().toString(),txtPass.getText().toString());

                if(cursor.getCount()>0){ // si existe un registro pasa a la actividad principal
                    Intent i = new Intent(getApplicationContext(),Principal.class);
                    startActivity(i);
                }else{
                    Toast.makeText(getApplicationContext(),"Usuario y/o pass incorrectos",Toast.LENGTH_LONG).show();
                }
                // limpiar casillas de texto
                txtUsu.setText("");
                txtPass.setText("");
                txtUsu.findFocus();
            }
        });
    }
}