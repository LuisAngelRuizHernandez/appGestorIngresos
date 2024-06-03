package com.example.pruebacanciones.adaptadores;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pruebacanciones.OpenHelper.SQLlite_OpenHelper;
import com.example.pruebacanciones.R;
import com.example.pruebacanciones.entidades.Ingreso;
import com.example.pruebacanciones.verIngreso;

import java.util.ArrayList;

public class listaIngresoAdapter extends RecyclerView.Adapter<listaIngresoAdapter.IngresoViewHolder> {

    ArrayList<Ingreso> listaIngresos;
    Context context;
    private SQLlite_OpenHelper dbHelper;

    // Constructor de la clase listaIngresoAdapter
    public listaIngresoAdapter(ArrayList<Ingreso> listaIngresos,Context context, SQLlite_OpenHelper dbHelper) {
        this.listaIngresos = listaIngresos;
        this.context = context;
        this.dbHelper = dbHelper;
    }

    @NonNull
    @Override
    public IngresoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_intem_ingreso, null, false);
        return new IngresoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IngresoViewHolder holder, int position) {
        Ingreso ingreso = listaIngresos.get(position);
        holder.viewNombre.setText(ingreso.getNombre());
        holder.viewValor.setText(String.valueOf(ingreso.getValor()));
        holder.viewFuente.setText(ingreso.getFuente());

        // Presionar botón editar en la vista
        holder.btnEditar.setOnClickListener(v -> {
            Intent intent = new Intent(context, verIngreso.class);
            intent.putExtra("INGRESO_ID", ingreso.getID());
            intent.putExtra("NOMBRE", ingreso.getNombre());
            intent.putExtra("VALOR", String.valueOf(ingreso.getValor()));
            intent.putExtra("FUENTE", ingreso.getFuente());
            context.startActivity(intent);
        });

        // Presionar botón eliminar en la vista
        holder.btnEliminar.setOnClickListener(v -> eliminarIngreso(ingreso.getID(), position));
    }

    @Override
    public int getItemCount() {
      return listaIngresos.size(); // tamaño de la lista
    }

    // metodo para eliminar un ingreso desde el adaptador
    private void eliminarIngreso(int ingresoId, int position) {
        boolean isDeleted = dbHelper.eliminarIngreso(ingresoId);
        if (isDeleted) {
            listaIngresos.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, listaIngresos.size());
            Toast.makeText(context, "Ingreso eliminado", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Error al eliminar el ingreso", Toast.LENGTH_SHORT).show();
        }
    }

    // metodo para llenar la vista
    public class IngresoViewHolder extends RecyclerView.ViewHolder {

        TextView viewNombre,viewValor,viewFuente;
        Button btnEditar, btnEliminar;

        public IngresoViewHolder(@NonNull View itemView) {
            super(itemView);
            viewNombre = itemView.findViewById(R.id.viewNombreIngreso);
            viewValor = itemView.findViewById(R.id.viewValorIngreso);
            viewFuente = itemView.findViewById(R.id.viewFuenteIngreso);
            btnEditar = itemView.findViewById(R.id.btnEditarIngreso); // Aquí se inicializa el botón editar
            btnEliminar = itemView.findViewById(R.id.btnEliminarIngreso); // aqui se inicializa el boton de eliminar
        }
    }
}
