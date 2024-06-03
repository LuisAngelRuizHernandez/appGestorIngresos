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
import com.example.pruebacanciones.entidades.Gasto;
import com.example.pruebacanciones.verGasto;
import com.example.pruebacanciones.verIngreso;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class listaGastoAdapter extends RecyclerView.Adapter<listaGastoAdapter.ViewHolder> {
    private List<Gasto> gastos;
    private Context context;
    private SQLlite_OpenHelper dbHelper;

    public listaGastoAdapter(List<Gasto> gastos, Context context, SQLlite_OpenHelper dbHelper) {
        this.gastos = gastos;
        this.context = context;
        this.dbHelper = dbHelper;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.lista_item_gasto, null, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Gasto gasto = gastos.get(position);
        holder.nombreGasto.setText(gasto.getNombreGasto());
        holder.fechaGasto.setText(gasto.getFechaGasto());
        holder.descripcionGasto.setText(gasto.getDescripcionGasto());


        // Presionar botón editar en la vista
        holder.btnEditar.setOnClickListener(v -> {
            Intent intent = new Intent(context, verGasto.class);
            intent.putExtra("INGRESO_ID", gasto.getId());
            intent.putExtra("nombreGasto", gasto.getNombreGasto());
            intent.putExtra("descripcionGasto", gasto.getDescripcionGasto());
            context.startActivity(intent);
        });

        // Presionar botón eliminar en la vista
        holder.btnEliminar.setOnClickListener(v -> eliminarGasto(gasto.getId(), position));
    }

    // metodo para eliminar un ingreso desde el adaptador
    private void eliminarGasto(int ingresoId, int position) {
        boolean isDeleted = dbHelper.eliminarGasto(ingresoId);
        if (isDeleted) {
            gastos.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, gastos.size());
            Toast.makeText(context, "gasto eliminado", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Error al eliminar el ingreso", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public int getItemCount() {
        return gastos.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView nombreGasto, fechaGasto, descripcionGasto;
        Button btnEditar, btnEliminar;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nombreGasto = itemView.findViewById(R.id.viewNombreGasto);
            fechaGasto = itemView.findViewById(R.id.viewFechaGasto);
            descripcionGasto = itemView.findViewById(R.id.viewDescripcionGasto);
            btnEditar = itemView.findViewById(R.id.btnEditarGasto); // Aquí se inicializa el botón editar
            btnEliminar = itemView.findViewById(R.id.btnEliminarGasto); // aqui se inicializa el boton de eliminar
        }
    }
}
