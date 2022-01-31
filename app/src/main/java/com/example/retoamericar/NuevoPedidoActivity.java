package com.example.retoamericar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class NuevoPedidoActivity extends AppCompatActivity {
    TextView idAlbaran;
    ListView lista;
    Button crear;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_pedido);
        Intent data = getIntent();
        idAlbaran = findViewById(R.id.txvNuevoPedidoIdAlbaran);
        idAlbaran.setText(data.getStringExtra("id"));


    }

    private void cargarLista(){

    }
}