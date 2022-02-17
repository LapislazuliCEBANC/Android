package com.example.retoamericar;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import java.util.ArrayList;

public class AgendaActivity extends AppCompatActivity {
    DatePicker calendario;
    Button crear;
    ListView lst;
    ArrayList<Integer> identificadores = new ArrayList<>();
    String fecha;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agenda);

        lst = findViewById(R.id.lsvTareas);
        calendario = findViewById(R.id.calendario);
        crear = findViewById(R.id.btnTarea);

        int dayOfMonth = calendario.getDayOfMonth();
        int monthOfYear = calendario.getMonth();
        int year = calendario.getYear();

        fecha = dayOfMonth +"/"+monthOfYear+ "/"+year;
        calendario.setOnDateChangedListener(new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                fecha = dayOfMonth +"/"+monthOfYear+ "/"+year;
            }
        });

        crear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AgendaActivity.this, NuevaTareaActivity.class);
                i.putExtra("fecha", fecha);
                startActivity(i);
            }
        });

//        lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                dialogo(identificadores.get(i));
//            }
//        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        cargar();
    }

    private void cargar(){
        retoSQLiteHelper rsdb = new retoSQLiteHelper(this, "reto", null, 1);
        SQLiteDatabase db = rsdb.getReadableDatabase();

        String id = String.valueOf(((GlobalData) this.getApplication()).getIdComercial());
        //Select campos, "as _id" obligatorio para el SimpleCursorAdapter
        String[] campos = new String[]{"idAgenda as _id","idPartner", "descripcion", "fecha","hora"};
        //Where campos
        String[] args = new String[]{id};

        Cursor c = db.query("Agenda", campos, "idComercial=?", args, null, null, null);
        if (c.moveToFirst()){
            SimpleCursorAdapter sca = new SimpleCursorAdapter(this, R.layout.agenda, c,
                    new String[]{"_id", "descripcion", "fecha","hora", "idPartner"},
                    new int[]{R.id.idAgenda, R.id.descripcion, R.id.fecha, R.id.hora, R.id.partnerId},
                    CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
            lst.setAdapter(sca);
            do {
                identificadores.add(c.getInt(0));
            }while (c.moveToNext());
        }
        db.close();
    }
//
//    private void dialogo(int id) {
//        String[] opc = {"Modificar","Eliminar","Cancelar"};
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setTitle("Selecciona una opcion");
//        builder.setItems(opc, new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                esperador(id, i);
//            }
//        });
//        builder.show();
//    }
//
//    private void esperador(int id, int respuesta){
//        if (respuesta==0){
//            modificar(id);
//        }else if (respuesta==1){
//            eliminar(id);
//        }
//    }
//
//    private void eliminar(int id){
//        retoSQLiteHelper rsdb = new retoSQLiteHelper(this, "reto", null, 1);
//        SQLiteDatabase db = rsdb.getWritableDatabase();
//
//        String[] args = {String.valueOf(id)};
//        db.delete("Agenda","idAgenda=?",args);
//        cargar();
//    }
//
//    private void modificar(int id){
//        Intent i = new Intent(AgendaActivity.this, ModificarTarea.class);
//        i.putExtra("id", String.valueOf(id));
//        startActivity(i);
//    }
}