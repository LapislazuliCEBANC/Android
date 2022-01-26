package com.example.retoamericar;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.CursorAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;

public class PedidosActivity extends AppCompatActivity {
    Spinner spin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedidos);

        cargar();

    }

    private void cargar(){
        retoSQLiteHelper rsdb = new retoSQLiteHelper(this, "reto", null, 1);
        SQLiteDatabase db = rsdb.getReadableDatabase();

        String id = String.valueOf((((GlobalData) this.getApplication()).getIdComercial()));

        String[] campos = new String[]{"idPartner as _id","nombre","direccion","poblacion","cif","telefono","email"};
        String[] args = new String[]{id};

        Cursor c = db.query("Partners", campos, "idComercial=?", args, null, null, null);
        if (c.moveToFirst()){
            SimpleCursorAdapter sca = new SimpleCursorAdapter(this,R.layout.partner,c,new String[]{"_id","nombre","direccion","poblacion","cif","telefono","email"},new int[]{
                    R.id.txvPartnerID,
                    R.id.txvPartnerNombre,
                    R.id.txvPartnerDireccion,
                    R.id.txvPartnerPoblacion,
                    R.id.txvPartnerCIF,
                    R.id.txvPartnerTelefono,
                    R.id.txvPartnerEmail
            }, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
            spin = findViewById(R.id.spnPedidosSpinner);
            sca.setDropDownViewResource(R.layout.partner_pedidos);
            spin.setAdapter(sca);
        }
        db.close();
    }
}