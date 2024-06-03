package com.example.pruebacanciones.OpenHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

import com.example.pruebacanciones.entidades.Gasto;
import com.example.pruebacanciones.entidades.Ingreso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

// estructura de la base de datos y metodos
public class SQLlite_OpenHelper extends SQLiteOpenHelper {

    public SQLlite_OpenHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, 5);  // incrementa la versión
    }

    // esctructura de las tablas (relaciones)
    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "create table usuarios(_ID integer primary key autoincrement, nombre text, apellido text, correo text, password text)";
        db.execSQL(query);

        // Crear la tabla ingresos
        String queryIngresos = "create table ingresos(ID integer primary key autoincrement, _IDusuario integer, nombre text, valor integer, fuente text, descripcion text, " +
                "foreign key(_IDusuario) references usuarios(_ID) on delete cascade)";
        db.execSQL(queryIngresos);

        // Crear la tabla gastos
        String createTableGastos = "CREATE TABLE gastos (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nombreGasto TEXT, " +
                "fechaGasto TEXT, " +  // Fecha como String
                "valorGasto INTEGER, " +  // Valor como INTEGER
                "descripcionGasto TEXT, " +
                "_IDusuario INTEGER, " +  // Corrección del nombre de columna
                "FOREIGN KEY(_IDusuario) REFERENCES usuarios(_ID) ON DELETE CASCADE)";  // Corrección del nombre de columna
        db.execSQL(createTableGastos);
    }

    // cambiar de version y borra las tablas si no existen
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS usuarios");
        db.execSQL("DROP TABLE IF EXISTS ingresos");
        db.execSQL("DROP TABLE IF EXISTS gastos");
        onCreate(db);
    }

    // metodo para abrir la bd
    public void abrir(){
        this.getWritableDatabase();
    }

    // metodo para cerrar la bd
    public void cerrar(){
        this.close();
    }

    // metodo para insertar datos en la tabla usuarios
    public void insertarReg(String nom, String apell, String corr, String pass){
        ContentValues valores = new ContentValues();
        valores.put("nombre",nom);
        valores.put("apellido",apell);
        valores.put("correo",corr);
        valores.put("password",pass);
        this.getWritableDatabase().insert("usuarios",null,valores);
        // tabla , null , valores a insertar
    }


    // Metodo para insertar datos en la tabla ingresos
    public void insertarIngreso(int idUsuario, String nombre, int valor, String fuente, String descripcion) {
        ContentValues valores = new ContentValues();
        valores.put("_IDusuario", idUsuario); // llave fk
        valores.put("nombre", nombre);
        valores.put("valor", valor);
        valores.put("fuente", fuente);
        valores.put("descripcion", descripcion);
        this.getWritableDatabase().insert("ingresos", null, valores);
    }

    // metodo para insertar en la tabla gastos
    public void insertarGasto(String nombreGasto, String fechaGasto, int valorGasto, String descripcionGasto, int idUsuario) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("nombreGasto", nombreGasto);
        contentValues.put("fechaGasto", fechaGasto);  // Fecha como String
        contentValues.put("valorGasto", valorGasto);  // Valor como int
        contentValues.put("descripcionGasto", descripcionGasto);
        contentValues.put("_IDusuario", idUsuario); // llave fk
        this.getWritableDatabase().insert("gastos", null, contentValues);
    }

    // Método para obtener el total de gastos en un rango de fechas
    public double totalGastos(Date fechaInicio, Date fechaFin) {
        double total = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT SUM(valorGasto) FROM gastos WHERE fechaGasto BETWEEN ? AND ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(fechaInicio.getTime()), String.valueOf(fechaFin.getTime())});
        if (cursor.moveToFirst()) {
            total = cursor.getDouble(0);
        }
        cursor.close();
        return total;
    }

    // metodo para mostrar todos los ingresos
    public List<Ingreso> mostrarIngresos(int _IDusuario) {
        List<Ingreso> ingresos = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;

        try {
            cursor = db.rawQuery("SELECT * FROM ingresos WHERE _IDusuario = ?", new String[]{String.valueOf(_IDusuario)});

            if (cursor != null && cursor.moveToFirst()) {
                int idIndex = cursor.getColumnIndex("ID");
                int nombreIndex = cursor.getColumnIndex("nombre");
                int valorIndex = cursor.getColumnIndex("valor");
                int descripcionIndex = cursor.getColumnIndex("descripcion");
                int fuenteIndex = cursor.getColumnIndex("fuente");

                do {
                    int ID = cursor.getInt(idIndex);
                    String nombre = cursor.getString(nombreIndex);
                    int valor = cursor.getInt(valorIndex);
                    String descripcion = cursor.getString(descripcionIndex);
                    String fuente = cursor.getString(fuenteIndex);
                    ingresos.add(new Ingreso(ID, _IDusuario, nombre, valor, descripcion, fuente));
                } while (cursor.moveToNext());
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return ingresos;
    }

    // metodo para mostrar todos los gastos

    public List<Gasto> mostrarGastos(int _IDusuario) {
        List<Gasto> gastos = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;

        try {
            cursor = db.rawQuery("SELECT * FROM gastos WHERE _IDUsuario = ?", new String[]{String.valueOf(_IDusuario)});

            if (cursor != null && cursor.moveToFirst()) {
                int idIndex = cursor.getColumnIndex("id");
                int nombreGastoIndex = cursor.getColumnIndex("nombreGasto");
                int fechaGastoIndex = cursor.getColumnIndex("fechaGasto");
                int valorGastoIndex = cursor.getColumnIndex("valorGasto");
                int descripcionGastoIndex = cursor.getColumnIndex("descripcionGasto");
                do {
                    int ID = cursor.getInt(idIndex);
                    String nombreGasto = cursor.getString(nombreGastoIndex);
                    String fechaGasto = cursor.getString(fechaGastoIndex); // fecha en String
                    int valorGasto = cursor.getInt(valorGastoIndex);
                    String descripcionGasto = cursor.getString(descripcionGastoIndex);
                    gastos.add(new Gasto(ID, nombreGasto, fechaGasto, valorGasto, descripcionGasto, _IDusuario));
                } while (cursor.moveToNext());
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return gastos;
    }

    // metodo para modificar un ingreso
    public boolean actualizarIngreso(int ingresoId, String nombre, double valor, String fuente) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put("nombre", nombre);
        valores.put("valor", valor);
        valores.put("fuente", fuente);

        int filasAfectadas = db.update("ingresos", valores, "ID = ?", new String[]{String.valueOf(ingresoId)});
        return filasAfectadas > 0;
    }

    // metodo para modificar gasto

    // método para modificar un gasto
    public boolean actualizarGasto(int id, String nombreGasto,String descripcionGasto) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put("nombreGasto", nombreGasto);
        valores.put("descripcionGasto", descripcionGasto);

        int filasAfectadas = db.update("gastos", valores, "id = ?", new String[]{String.valueOf(id)});
        return filasAfectadas > 0;
    }

    // Método para obtener el total de gastos
    public double obtenerTotalGastos(int idUsuario) {
        double totalGastos = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT SUM(valorGasto) FROM gastos WHERE _IDUsuario = ?", new String[]{String.valueOf(idUsuario)});
        if (cursor.moveToFirst()) {
            totalGastos = cursor.getDouble(0);
        }
        cursor.close(); // cerrar
        return totalGastos;
    }

    // Método para obtener el total de ingresos
    public double obtenerTotalIngresos(int idUsuario) {
        double totalIngresos = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT SUM(valor) FROM ingresos WHERE _IDUsuario = ?", new String[]{String.valueOf(idUsuario)});
        if (cursor.moveToFirst()) {
            totalIngresos = cursor.getDouble(0);
        }
        cursor.close();
        return totalIngresos;
    }

    // Método para eliminar un ingreso por ID
    public boolean eliminarIngreso(int ingresoId) {
        SQLiteDatabase db = this.getWritableDatabase();
        int filasAfectadas = db.delete("ingresos", "ID = ?", new String[]{String.valueOf(ingresoId)});
        return filasAfectadas > 0;
    }

    // Método para eliminar un ingreso por ID
    public boolean eliminarGasto(int ingresoId) {
        SQLiteDatabase db = this.getWritableDatabase();
        int filasAfectadas = db.delete("gastos", "ID = ?", new String[]{String.valueOf(ingresoId)});
        return filasAfectadas > 0;
    }


    // metodo que permite validar si el usuario existe
    public Cursor consultarUsuPass(String usu, String pass) throws SQLException {
        Cursor mcursor = null;
        mcursor = this.getWritableDatabase().query("usuarios",new String[]{"_ID","nombre","apellido","correo","password"},
                "correo like '" + usu + "' and password like '"+ pass +"'",null,null,null,null);
        return mcursor;
    }

}
