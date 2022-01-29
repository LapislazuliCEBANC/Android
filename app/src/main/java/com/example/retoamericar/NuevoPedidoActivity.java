package com.example.retoamericar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class NuevoPedidoActivity extends AppCompatActivity {
    TextView idAlbaran;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_pedido);
        Intent data = getIntent();
        idAlbaran = findViewById(R.id.txvNuevoPedidoIdAlbaran);
        idAlbaran.setText(data.getStringExtra("id"));
    }
}