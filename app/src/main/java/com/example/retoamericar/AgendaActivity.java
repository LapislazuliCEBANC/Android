package com.example.retoamericar;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
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
        cargar();
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

        lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                dialogo(identificadores.get(i));
            }
        });

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
        String[] args = new String[]{id};

        Cursor cursor = db.rawQuery("Select Partners.nombre as _id , Agenda.idPartner, Agenda.descripcion, fecha, hora, idAgenda " +
                                        "From Agenda, Partners " +
                                        "Where Agenda.idComercial=? and Agenda.idPartner = Partners.idPartner",args);
        if (cursor.moveToFirst()){
            SimpleCursorAdapter sca = new SimpleCursorAdapter(this, R.layout.agenda, cursor,
                    new String[]{"_id", "descripcion", "fecha","hora", "idPartner"},
                    new int[]{R.id.txvAgendaNombrePartner, R.id.txvAgendaDescripcion, R.id.txvAgendaFecha, R.id.txvAgendaHora},
                    CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
            lst.setAdapter(sca);
            do {
                identificadores.add(cursor.getInt(5));
            }while (cursor.moveToNext());
        }
        db.close();
    }

    private void dialogo(int id) {
        String[] opc = {"Eliminar","Cancelar"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Selecciona una opcion");
        builder.setItems(opc, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (i==0){
                    eliminar(id);
                }
            }
        });
        builder.show();
    }

    private void eliminar(int id){
        retoSQLiteHelper rsdb = new retoSQLiteHelper(this, "reto", null, 1);
        SQLiteDatabase db = rsdb.getWritableDatabase();

        String[] args = {String.valueOf(id)};
        db.delete("Agenda","idAgenda=?",args);
        cargar();
    }
}