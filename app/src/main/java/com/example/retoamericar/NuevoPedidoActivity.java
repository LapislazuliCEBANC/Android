package com.example.retoamericar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class NuevoPedidoActivity extends AppCompatActivity {
    TextView idAlbaran;
    ListView lista;
    Button crear;
    String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_pedido);
        Intent data = getIntent();
        id = data.getStringExtra("id");
        idAlbaran = findViewById(R.id.txvNuevoPedidoIdAlbaran);
        idAlbaran.setText(id);
        lista=findViewById(R.id.lsvNuevoPedidoLista);
        cargarLista();
        crear=findViewById(R.id.btnNuevoPedidoCrear);
        crear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(NuevoPedidoActivity.this, NuevaLineaActivity.class);
                i.putExtra("id", id);
                startActivity(i);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        cargarLista();
    }

    private void cargarLista(){
        retoSQLiteHelper rsdb = new retoSQLiteHelper(this, "reto", null, 1);
        SQLiteDatabase db = rsdb.getReadableDatabase();

        String[] args = new String[]{id};

        Cursor c = db.rawQuery(
                "SELECT a.idArticulo as _id, descripcion, cantidad, precio " +
                    "FROM Articulos a, Lineas l " +
                    "WHERE l.idAlbaran = ?",args);
        if (c.moveToFirst()){
            SimpleCursorAdapter sca = new SimpleCursorAdapter(this,R.layout.partner,c,
                    new String[]{"_id","descripcion","cantidad","precio"},
                    new int[]{R.id.txvPartnerID, R.id.txvPartnerNombre, R.id.txvPartnerDireccion, R.id.txvPartnerPoblacion, R.id.txvPartnerCIF, R.id.txvPartnerTelefono, R.id.txvPartnerEmail
                    }, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
            lista.setAdapter(sca);
        }
        db.close();
    }
}