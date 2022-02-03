package com.example.retoamericar;

import android.content.Intent;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerViewAdapterAlbaran extends RecyclerView.Adapter<RecyclerViewAdapterAlbaran.MiViewHolder> {

    private Cursor c;
    public RecyclerViewAdapterAlbaran(Cursor c) {
        this.c = c;
    }

    public class MiViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView idAlbaran;
        TextView fechaAlbaran;
        TextView fechaEnvio;
        TextView fechapago;
        public MiViewHolder(@NonNull View itemView) {
            super(itemView);
            idAlbaran = itemView.findViewById(R.id.txvAlbaranId);
            fechaAlbaran = itemView.findViewById(R.id.txvAlbaranFechaAlbaran);
            fechaEnvio = itemView.findViewById(R.id.txvAlbaranFechaEnvio);
            fechapago = itemView.findViewById(R.id.txvAlbaranFechaPago);
            itemView.setClickable(true);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent (view.getContext(), NuevoPedidoActivity.class);
            intent.putExtra("id", idAlbaran.getText());
            view.getContext().startActivity(intent);
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
        holder.idAlbaran.setText(c.getString(0));
        holder.fechaAlbaran.setText(c.getString(1));
        holder.fechaEnvio.setText(c.getString(2));
        holder.fechapago.setText(c.getString(3));
    }

    @Override
    public int getItemCount() {
        return c.getCount();
    }


}
