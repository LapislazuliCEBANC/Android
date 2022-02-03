package com.example.retoamericar;

import android.content.Intent;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerViewAdapterPedido extends RecyclerView.Adapter<RecyclerViewAdapterPedido.MiViewHolder> {

    private Cursor c;
    public RecyclerViewAdapterPedido(Cursor c) {
        this.c = c;
    }

    public class MiViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView idArticulo;
        TextView descripcion;
        TextView cantidad;
        TextView precio;
        public MiViewHolder(@NonNull View itemView) {
            super(itemView);
            idArticulo = itemView.findViewById(R.id.txvLineaIdArticulo);
            descripcion = itemView.findViewById(R.id.txvLineaDescripcion);
            cantidad = itemView.findViewById(R.id.txvAlbaranFechaEnvio);
            precio = itemView.findViewById(R.id.txvAlbaranFechaPago);
            itemView.setClickable(true);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            TextView idEliminar = itemView.findViewById(R.id.txvNuevoPedidoIdEliminar);
            idEliminar.setText(idArticulo.getText().toString());
        }
    }

    @NonNull
    @Override
    public MiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.albaran, null, false);
        return new MiViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MiViewHolder holder, int position) {
        c.moveToPosition(position);
        holder.idArticulo.setText(c.getString(0));
        holder.descripcion.setText(c.getString(1));
        holder.cantidad.setText(c.getString(2));
        holder.precio.setText(c.getString(3));
    }

    @Override
    public int getItemCount() {
        return c.getCount();
    }


}
