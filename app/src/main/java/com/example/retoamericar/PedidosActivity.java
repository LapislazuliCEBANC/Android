package com.example.retoamericar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;

public class PedidosActivity extends AppCompatActivity {
    Spinner spin;
    RecyclerView lista;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedidos);
        lista = findViewById(R.id.rcvPedidosLista);
        cargarSpinner();


        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                cargarRecyclerView(i);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        cargarSpinner();
    }

    private void cargarSpinner(){
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

    private void cargarRecyclerView(int pos){
        retoSQLiteHelper rsdb = new retoSQLiteHelper(this, "reto", null, 1);
        SQLiteDatabase db = rsdb.getReadableDatabase();
        String idc = String.valueOf((((GlobalData) this.getApplication()).getIdComercial()));

        String[] camposPartner = new String[]{"idPartner"};
        String[] argsPartner = new String[]{idc};

        Cursor partners = db.query("Partners", camposPartner, "idComercial=?", argsPartner, null, null, null);
        if (partners.moveToPosition(pos)){

            String[] campos = new String[]{"idAlbaran as _id","fechaAlbaran","fechaEnvio","FechaPago"};
            String[] args = new String[]{String.valueOf(partners.getInt(0))};

            Cursor c = db.query("Albaranes", campos, "idPartner=?", args, null, null, null);
            if (c.moveToFirst()){

                lista.setLayoutManager(new LinearLayoutManager(this));

                RecyclerViewAdapter rva = new RecyclerViewAdapter(c);
                rva.notifyDataSetChanged();
                lista.setAdapter(rva);
                //https://stackoverflow.com/questions/50424829/implementation-of-recyclerview-with-cursor-adapter
            }
        }
        db.close();
    }
}